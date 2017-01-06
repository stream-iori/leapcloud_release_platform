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

  private static final Logger logger = LoggerFactory.getLogger(ReleaseTaskControllerTest.class);

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
    JsonObject body = new JsonObject().put("releaseType", 3).put("proposal", "www").put("title", "www")
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

    JsonObject body = new JsonObject().put("id", 74).put("releaseType", 2).put("proposal", "stream").put("title", "www")
      .put("projectURL", "www").put("projectDescription", "www");

    rule.vertx().createHttpClient().put(8888, "localhost", "/task", httpClientResponse -> {
      httpClientResponse.exceptionHandler(context::fail);
      context.assertEquals(200, httpClientResponse.statusCode());
      async.complete();
    }).putHeader("Content-Type", "application/json")
      .end(body.encode());
  }


  @Test
  public void disposal(TestContext context) {
    Async async = context.async();
    JsonObject loginBody = new JsonObject().put("username", "stream").put("password", "123");

    rule.vertx().createHttpClient().post(8888, "localhost", "/login", loginResponse -> {
      loginResponse.exceptionHandler(context::fail);
      context.assertEquals(200, loginResponse.statusCode());
      //获取header里的cookie,后面要带着发给服务端
      String cookie = loginResponse.getHeader("Set-Cookie");
      JsonObject taskBody = new JsonObject().put("id", 75).put("status", 1).put("releaseRemark", "hello");
      rule.vertx().createHttpClient()
        .put(8888, "localhost", "/disposaltask", disposalResponse -> {
          disposalResponse.exceptionHandler(context::fail);
          context.assertEquals(200, disposalResponse.statusCode());
          async.complete();
        })
        .putHeader("Content-Type", "application/json").putHeader("Cookie", cookie)
        .end(taskBody.encode());
    }).putHeader("Content-Type", "application/json").end(loginBody.encode());

  }

}

