package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.Module.GuiceModule;
import cn.leapcloud.release.platform.controller.RestfulServer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by stream.
 */
public class Starter extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(Starter.class);

//  public static void main(String[] args) {
//    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new Starter(), event -> {
//      if (event.succeeded()) {
//        logger.info("部署成功");
//      } else {
//        logger.error("部署失败", event.cause());
//      }
//    });
//  }


  @Override
  public void start() throws Exception {
    Injector injector = Guice.createInjector(new GuiceModule(vertx));
    RestfulServer restfulServer =injector.getInstance(RestfulServer.class);
    restfulServer.start();
  }
}
