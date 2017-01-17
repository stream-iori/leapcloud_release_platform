package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.UserService;
import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import com.google.common.base.Strings;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by stream on.
 */
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;
  private Router router;
  private ReleaseTaskService releaseTaskService;


  @Inject
  public UserController(Router router, UserService userService, ReleaseTaskService releaseTaskService) {
    this.router = router;
    this.userService = userService;
    this.releaseTaskService = releaseTaskService;
    initRouter();
  }

  private void initRouter() {
    router.get("/api/isLogin").handler(routingContext -> {
      JsonObject userInfo = routingContext.session().get("userInfo");
      if (userInfo == null || userInfo.getString("name") == null) {
        routingContext.response().setStatusCode(402).setStatusMessage("user doesn't login.").end();
      } else {
        routingContext.response().setStatusCode(200).end();
      }
    });

    router.put("/api/logout").handler(routingContext -> {
      routingContext.session().destroy();
      routingContext.response().setStatusCode(200).end();
    });


    router.post("/api/login").consumes("application/json").handler(routingContext ->
      routingContext.request().bodyHandler(bodyBuffer -> {
        JsonObject loginData = bodyBuffer.toJsonObject();
        HttpServerResponse response = routingContext.response();
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

        if (!userService.isUserExist(username)) {
          response.setStatusCode(400).setStatusMessage("username no existence").end();
          return;
        }


        boolean result = userService.login(username, password);


        if (result) {
          //3. 设置session
          Session session = routingContext.session().put("userInfo", new JsonObject().put("name", username));
          int skip = 1;
          int limit = 5;
          int i = 4;
          JsonObject object = new JsonObject();
          object.put("$eq", 0);
          JsonObject object1 = new JsonObject();
          object1.put("status", object);


          JsonObject tasks = new JsonObject();
          JsonArray items = new JsonArray();
          List<ReleaseTask> releaseTasks = null;
          int total = 0;

          releaseTasks = releaseTaskService.queryAll(object1, i, skip, limit).getReleaseTasks();


          total = releaseTaskService.queryAll(object1, i, skip, limit).getTotalCount();


          for (ReleaseTask releaseTask : releaseTasks) {

            JsonObject task = releaseTask.toJson();
            items.add(task);
          }

          tasks.put("total", total).put("items", items);


          response.setStatusCode(200).putHeader("vertx-web.session", session.id()).setStatusMessage("login success.").end(tasks.encode());
        } else {
          response.setStatusCode(400).setStatusMessage("password incorrect").end();
        }

      }).exceptionHandler(ex -> {
        logger.error("登录错误", ex);
        routingContext.response().setStatusCode(500).setStatusMessage(ex.getMessage()).end();
      }));
  }
}
















