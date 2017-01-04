package cn.leapcloud.release.platform;

import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.BeforeClass;
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


  @BeforeClass
  public static void before() {
    rule.vertx().deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("deploy release-platform success");
      } else {
        logger.error("deploy release-platform failed.", event.cause());
      }
    });
  }

  @Test
  public void get(TestContext context) {
    Async async = context.async();

    rule.vertx().setPeriodic(2000, v -> {
      rule.vertx().createHttpClient().get(8888, "0.0.0.0", "/types", httpClientResponse -> {
        httpClientResponse.exceptionHandler(context::fail);
        context.assertEquals(200, httpClientResponse.statusCode());
        httpClientResponse.bodyHandler(bodyBuffer -> {
          JsonArray body = bodyBuffer.toJsonArray();
          logger.info(body);
        });
        async.complete();
      }).end();
    });
  }
}
