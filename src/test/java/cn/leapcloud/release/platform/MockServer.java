package cn.leapcloud.release.platform;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by stream.
 */
public class MockServer {

  private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

  @Ignore
  @Test
  public void mockMain() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("部署release-platform成功");
      } else {
        logger.error("部署失败", event.cause());
      }
    });
    while (true) {
      Thread.sleep(1000L);
    }
  }


















}
