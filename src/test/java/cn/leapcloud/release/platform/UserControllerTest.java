package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import cn.leapcloud.release.platform.service.domain.Status;
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

import java.util.Date;

/**
 * Created by stream on 05/12/2016.
 */
@RunWith(VertxUnitRunner.class)
public class UserControllerTest {

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
  public void login(TestContext context) {
    Async async = context.async();
    JsonObject body = new JsonObject().put("username", "stream").put("password", "123");
    rule.vertx().setTimer(2000, t -> {
      rule.vertx().createHttpClient().post(8888, "localhost", "/api/login", response -> {
        response.exceptionHandler(context::fail);
        context.assertEquals(200, response.statusCode());
        async.complete();
      }).putHeader("Content-Type", "application/json").end(body.encode());
    });
  }

  @Test
  public void loginOut(TestContext context) {
    Async async = context.async();

    rule.vertx().setTimer(2000, v -> {
      rule.vertx().createHttpClient().put(8888, "localhost", "/api/logout", response -> {
        response.exceptionHandler(context::fail);
        context.assertEquals(200, response.statusCode());
        async.complete();
      }).end();

    });
  }

  @Test
  public void getJson(TestContext context) {
    Async async = context.async();
    rule.vertx().setTimer(2000, v -> {
      ReleaseTask releaseTask = new ReleaseTask.Builder()
        .proposal("proposal")
        .proposalTime(new Date(System.currentTimeMillis()))
        .updateTime(new Date(System.currentTimeMillis()))
        .status(Status.DONE)
        .build();
      System.out.println(releaseTask.toJson().encode());
      System.out.println(releaseTask.toString());
      async.complete();
    });
  }

}


