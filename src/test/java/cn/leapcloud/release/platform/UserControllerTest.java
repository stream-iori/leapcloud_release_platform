package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import cn.leapcloud.release.platform.service.domain.Status;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created by stream on 05/12/2016.
 */
@RunWith(VertxUnitRunner.class)
public class UserControllerTest extends Base {


  @BeforeClass
  public static void before() throws InterruptedException {
    startServer();
  }

  @AfterClass
  public static void after() {
    stopServer();
  }


  @Test
  public void login(TestContext context) {
    Async async = context.async();
    JsonObject body = new JsonObject().put("username", "stream").put("password", "123");

    vertx.createHttpClient().post(8888, "localhost", "/api/login", response -> {
      response.exceptionHandler(context::fail);
      context.assertEquals(200, response.statusCode());
      async.complete();
    }).putHeader("Content-Type", "application/json").end(body.encode());

  }

  @Test
  public void loginOut(TestContext context) {
    Async async = context.async();


    vertx.createHttpClient().put(8888, "localhost", "/api/logout", response -> {
      response.exceptionHandler(context::fail);
      context.assertEquals(200, response.statusCode());
      async.complete();
    }).end();


  }

  @Test
  public void getJson(TestContext context) {


    ReleaseTask releaseTask = new ReleaseTask.Builder()
      .proposal("proposal")
      .proposalTime(new Date(System.currentTimeMillis()))
      .updateTime(new Date(System.currentTimeMillis()))
      .status(Status.DONE)
      .build();
    System.out.println(releaseTask.toJson().encode());
    System.out.println(releaseTask.toString());
  }

}


