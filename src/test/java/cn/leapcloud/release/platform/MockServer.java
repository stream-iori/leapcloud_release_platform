package cn.leapcloud.release.platform;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by stream.
 */
@RunWith(VertxUnitRunner.class)
public class MockServer {

  private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

  @Test(timeout = 999999L)
  public void restfulServer(TestContext context) {
    Async async = context.async();
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("部署release-platform成功");
      } else {
        logger.error("部署失败", event.cause());
      }
    });
  }

}
