package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.UserService;
import com.google.common.base.Strings;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by stream on.
 */
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  public UserController(Router router, UserService userService) {
    router.post("/login").consumes("application/json").handler(routingContext -> {
      routingContext.request().bodyHandler(bodyBuffer -> {
        JsonObject loginData = bodyBuffer.toJsonObject();
        HttpServerResponse response = routingContext.response();
        logger.info(loginData);
        String username = loginData.getString("username");
        String password = loginData.getString("password");
        //1.判断字段是否为空
        if (Strings.isNullOrEmpty(username)) {
          response.setStatusCode(400).setStatusMessage("username can not be null.").end();
          return;
        }
        if (Strings.isNullOrEmpty(password)) {
          response.setStatusCode(400).setStatusMessage("password can not be null.").end();
          return;
        }
        //2. 执行登录
        boolean result = userService.login(username, password);
        if (result) {
          response.setStatusCode(200).setStatusMessage("登录成功");
        } else {
          response.setStatusCode(400).setStatusMessage("密码错误");
        }
      }).exceptionHandler(ex -> {
        logger.error("登录错误", ex);
        routingContext.response().setStatusCode(500).setStatusMessage(ex.getMessage());
      });
    });
  }

}
