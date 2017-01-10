package cn.leapcloud.release.platform;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by songqian on 16/12/6.
 */

@RunWith(VertxUnitRunner.class)
public class ReleaseTaskControllerTest extends Base {

  @BeforeClass
  public static void before() throws InterruptedException {
    startServer();
  }

  @AfterClass
  public static void after() {
    stopServer();
  }

  @Test
  public void insert(TestContext context) {
    Async async = context.async();
    JsonObject body = new JsonObject().put("releaseType", 3).put("proposal", "www").put("title", "www")
      .put("projectURL", "www").put("projectDescription", "www");


    vertx.createHttpClient().post(8888, "localhost", "/api/task", httpClientResponse -> {
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

    vertx.createHttpClient().put(8888, "localhost", "/api/task", httpClientResponse -> {
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

    vertx.createHttpClient().post(8888, "localhost", "/api/login", loginResponse -> {
      loginResponse.exceptionHandler(context::fail);
      context.assertEquals(200, loginResponse.statusCode());
      //获取header里的cookie,后面要带着发给服务端
      String cookie = loginResponse.getHeader("Set-Cookie");
      JsonObject taskBody = new JsonObject().put("id", 85).put("status", 1).put("releaseRemark", "hello");
      vertx.createHttpClient()
        .put(8888, "localhost", "/api/disposaltask", disposalResponse -> {
          disposalResponse.exceptionHandler(context::fail);
          context.assertEquals(200, disposalResponse.statusCode());
          async.complete();
        })
        .putHeader("Content-Type", "application/json").putHeader("Cookie", cookie)
        .end(taskBody.encode());
    }).putHeader("Content-Type", "application/json").end(loginBody.encode());

  }

  @Test
  public void getWant(TestContext context) {
    Async async = context.async();
    vertx.createHttpClient().get(8888, "localhost", "/api/tasks?pageSize=2&currentPage=2", httpClientResponse -> {
      httpClientResponse.exceptionHandler(context::fail);
      context.assertEquals(200, httpClientResponse.statusCode());
      async.complete();
    }).end();

  }


}

