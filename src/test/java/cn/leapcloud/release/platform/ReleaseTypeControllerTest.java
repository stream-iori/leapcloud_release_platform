package cn.leapcloud.release.platform;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by songqian on 16/12/9.
 */
@RunWith(VertxUnitRunner.class)
public class ReleaseTypeControllerTest extends Base {

  @BeforeClass
  public static void before() throws InterruptedException {
    startServer();
  }

  @AfterClass
  public static void after() {
    stopServer();
  }


  @Test
  public void get(TestContext context) {
    Async async = context.async();


    vertx.createHttpClient().get(8888, "0.0.0.0", "/api/types", httpClientResponse -> {
      httpClientResponse.exceptionHandler(context::fail);
      context.assertEquals(200, httpClientResponse.statusCode());
      httpClientResponse.bodyHandler(bodyBuffer -> {
        JsonArray body = bodyBuffer.toJsonArray();
        System.out.println(body);
      });
      async.complete();
    }).end();

  }
}
