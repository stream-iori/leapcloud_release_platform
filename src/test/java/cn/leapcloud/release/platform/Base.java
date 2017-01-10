package cn.leapcloud.release.platform;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by stream.
 */
public class Base {

  private static final Logger logger = LoggerFactory.getLogger(Base.class);
  protected static Vertx vertx = Vertx.vertx();
  private static String deploymentID;

  public static void startServer() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    vertx.deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        deploymentID = event.result();
        logger.info("deploy release-platform success");
      } else {
        logger.error("deploy release-platform failed.", event.cause());
      }
      countDownLatch.countDown();
    });
    countDownLatch.await();
  }

  public static void stopServer() {
    vertx.undeploy(deploymentID, event -> {
      if (event.succeeded()) {
        logger.info("undeploy success.");
      } else {
        logger.error("undeploy failed.", event.cause());
      }
    });
  }

}
