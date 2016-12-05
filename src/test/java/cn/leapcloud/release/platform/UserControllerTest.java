package cn.leapcloud.release.platform;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Created by stream on 05/12/2016.
 */
@RunWith(VertxUnitRunner.class)
public class UserControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

  private static CountDownLatch countDownLatch = new CountDownLatch(1);
  private static String deploymentID;

  @ClassRule
  public static RunTestOnContext rule = new RunTestOnContext();

  @BeforeClass
  public static void before() {
    rule.vertx().deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("deploy release-platform success");
        deploymentID = event.result();
      } else {
        logger.error("deploy release-platform failed.", event.cause());
      }
      try {
        countDownLatch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  @AfterClass
  public static void after() {
    countDownLatch.countDown();
    rule.vertx().undeploy(deploymentID, event -> {
      if (event.succeeded()) {
        logger.info("tear down success.");
      } else {
        logger.error("tear down fail.", event.cause());
      }
    });
  }

  @Test
  public void login(TestContext context) {
    //开启异步
    Async async = context.async();

    JsonObject body = new JsonObject().put("username", "stream").put("password", "123");
    rule.vertx().createHttpClient().post(8888, "localhost", "/login", response -> {
      //response注册一个异常handler,如果http请求过程中发生异常，则测试失败
      response.exceptionHandler(context::fail);

      //判断response code是不是200
      context.assertEquals(200, response.statusCode());

      //使测试完成
      async.complete();
    }).putHeader("Content-Type", "application/json").end(body.encode());
  }

}
