package cn.leapcloud.release.platform;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by songqian on 16/12/9.
 */
@RunWith(VertxUnitRunner.class)
public class ReleaseTypeControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(ReleaseTypeControllerTest.class);

  @ClassRule
  public static RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void get(TestContext context) {
    Async async = context.async();

    rule.vertx().deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("deploy release-platform success");

        rule.vertx().createHttpClient().get(8888, "localhost", "/alltype", httpClientResponse -> {
          httpClientResponse.exceptionHandler(context::fail);
          context.assertEquals(200, httpClientResponse.statusCode());

          async.complete();
        }).end();

      } else {
        logger.error("deploy release-platform failed.", event.cause());
      }
    });


  }
}
