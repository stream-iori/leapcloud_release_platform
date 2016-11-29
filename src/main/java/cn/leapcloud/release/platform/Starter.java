package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.controller.RestfulServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by stream.
 */
public class Starter extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(Starter.class);

  private RestfulServer restfulServer;

  @Override
  public void start() throws Exception {
    restfulServer = new RestfulServer(vertx);
    restfulServer.start();
  }

  @Override
  public void stop() throws Exception {
    restfulServer.stop();
  }
}
