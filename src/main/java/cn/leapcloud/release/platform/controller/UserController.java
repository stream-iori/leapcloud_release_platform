package cn.leapcloud.release.platform.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by stream on 29/11/2016.
 */
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(Router router) {
    router.post("/login").consumes("application/json").handler(routingContext -> {
      routingContext.request().bodyHandler(bodyBuffer -> {
        JsonObject loginData = bodyBuffer.toJsonObject();
        logger.info(loginData);
        routingContext.response().end("OK");
      });
    });
  }

}
