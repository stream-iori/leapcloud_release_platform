package cn.leapcloud.release.platform.controller;

import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by stream.
 */
public class RestfulServer {

  private HttpServer httpServer;
  private Router router;

  private static final Logger logger = LoggerFactory.getLogger(RestfulServer.class);

  @Inject
  public RestfulServer(Vertx vertx, Router router, UserController userController, ReleaseTaskController releaseTaskController, ReleaseTypeController releaseTypeController) {
    this.httpServer = vertx.createHttpServer().requestHandler(router::accept);
    this.router = router;
  }

  public void start() {
    httpServer.listen(8888, "0.0.0.0", event -> {
      if (event.succeeded()) {
        logger.info("rest service start success on port 8888");
      } else {
        logger.error("rest service start failed", event.cause());
      }
    });
  }

  public void stop() {
    router.clear();
    httpServer.close();
  }
}
