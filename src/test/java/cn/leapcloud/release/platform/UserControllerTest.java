package cn.leapcloud.release.platform;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpClientResponse;
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
    //开启异步
    Async async = context.async();

    JsonObject body = new JsonObject().put("username", "stream").put("password", "123");

    //处理http response
    Handler<HttpClientResponse> responseHandler = response -> {
      //response注册一个异常handler,如果http请求过程中发生异常，则测试失败
      response.exceptionHandler(ex -> context.fail(ex));

      //判断response code是不是200
      context.assertEquals(200, response.statusCode());

      //使测试完成
      async.complete();
    };

    rule.vertx().createHttpClient()
      .post(8888, "localhost", "/login", responseHandler)
      .putHeader("Content-Type", "application/json")
      .end(body.encode());
  }

}
