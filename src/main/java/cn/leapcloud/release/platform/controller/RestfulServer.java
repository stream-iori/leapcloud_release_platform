package cn.leapcloud.release.platform.controller;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by stream.
 */
public class RestfulServer {

  private Vertx vertx;
  private HttpServer httpServer;
  private Router router;

  private static final Logger logger = LoggerFactory.getLogger(RestfulServer.class);

  public RestfulServer(Vertx vertx) {
    this.vertx = vertx;
    this.httpServer = vertx.createHttpServer();
    this.router = Router.router(vertx);

    new UserController(router, null);
  }

  public void start() {
    httpServer.requestHandler(router::accept).listen(8888, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        logger.info("rest service start success on port 8888");
      } else {
        logger.error("rest service start failed.", httpServerAsyncResult.cause());
      }
    });
  }

  public void stop() {
    httpServer.close();
  }




}
