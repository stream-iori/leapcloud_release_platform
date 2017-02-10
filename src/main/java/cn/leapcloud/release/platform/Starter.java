package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.Module.GuiceModule;
import cn.leapcloud.release.platform.controller.RestfulServer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by stream.
 */
public class Starter extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(Starter.class);
  private RestfulServer restfulServer;
  private Injector injector;

  public Injector getInjector() {
    return injector;
  }

  @Override
  public void start() throws Exception {
    JsonObject config = vertx.fileSystem().readFileBlocking("./config.json").toJsonObject();
    injector = Guice.createInjector(new GuiceModule(vertx, config));
    restfulServer = injector.getInstance(RestfulServer.class);
    restfulServer.start();
  }



  public void stop() throws Exception {
    restfulServer.stop();
  }
}
