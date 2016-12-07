package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.junit.Test;

import java.util.Date;

/**
 * Created by stream.
 */
public class MockServer {

  private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

  @Test
  public void mockMain() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("部署release-platform成功");
      } else {
        logger.error("部署失败", event.cause());
      }
    });
    while (true) {
      Thread.sleep(1000L);
    }
  }


  @Test
  public void getJson() {
    ReleaseTask releaseTask = new ReleaseTask.Builder()
      .proposal("proposal")
      .proposalTime(new Date(System.currentTimeMillis()))
      .updateTime(new Date())
      .build();
    System.out.println(releaseTask.toJson().encode());
  }

}
