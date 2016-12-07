package cn.leapcloud.release.platform;

import io.vertx.core.json.JsonObject;
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
 * Created by songqian on 16/12/6.
 */

@RunWith(VertxUnitRunner.class)
public class ReleaseTaskControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

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
  public void insert(TestContext context) {
    Async async = context.async();
    JsonObject body = new JsonObject().put("releaseType", 1).put("proposal", "www").put("title", "www")
      .put("projectURL", "www").put("projectDescription", "www");


    rule.vertx().createHttpClient().post(8888, "localhost", "/task", httpClientResponse -> {
      httpClientResponse.exceptionHandler(context::fail);
      context.assertEquals(200, httpClientResponse.statusCode());
      async.complete();
    }).putHeader("Content-Type", "application/json")
      .end(body.encode());
  }

  @Test
  public void update(TestContext context) {
    Async async = context.async();
    JsonObject body = new JsonObject().put("id", 2).put("releaseType", 1).put("proposal", "stream").put("title", "www")
      .put("projectURL", "www").put("projectDescription", "www");

    rule.vertx().createHttpClient().put(8888, "localhost", "/changetask", httpClientResponse -> {
      httpClientResponse.exceptionHandler(context::fail);
      context.assertEquals(200, httpClientResponse.statusCode());
      async.complete();
    }).putHeader("Content-Type", "application/json")
      .end(body.encode());
  }
}

