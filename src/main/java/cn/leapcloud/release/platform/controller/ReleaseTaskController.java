package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.List;


/**
 * Created by songqian on 16/12/5.
 */
public class ReleaseTaskController {

  private ReleaseTaskService releaseTaskService;
  private Router router;


  @Inject
  public ReleaseTaskController(ReleaseTaskService releaseTaskService, Router router) {
    this.releaseTaskService = releaseTaskService;
    this.router = router;
    insertNewTask();
    freshNewTask();
    searchNewTask();
    disposalTask();
  }

  public void insertNewTask() {
    router.post("/api/task").consumes("application/json").handler(routingContext -> {

      routingContext.request().bodyHandler(buffer -> {
        JsonObject jsonObject = buffer.toJsonObject();
        int releaseType = jsonObject.getInteger("releaseType");

        String proposal = jsonObject.getString("proposal");
        String title = jsonObject.getString("title");
        String projectURL = jsonObject.getString("projectURL");
        String projectDescription = jsonObject.getString("projectDescription");

        if (Strings.isNullOrEmpty(proposal)) {
          routingContext.response().setStatusCode(400).setStatusMessage("username can not be empty").end();
          return;
        }
        if (Strings.isNullOrEmpty(title)) {
          routingContext.response().setStatusCode(400).setStatusMessage("title can not be empty").end();
          return;
        }
        if (Strings.isNullOrEmpty(projectURL)) {
          routingContext.response().setStatusCode(400).setStatusMessage("URL can not be empty").end();
          return;
        }
        if (Strings.isNullOrEmpty(projectDescription)) {
          routingContext.response().setStatusCode(400).setStatusMessage("description can not be empty").end();
          return;
        }
        if (releaseType < 1 || releaseType > 3) {
          routingContext.response().setStatusCode(400).setStatusMessage("style must be chosen").end();
          return;
        }
        try {
          boolean result = releaseTaskService.createNewTask(releaseType, proposal, title, projectURL, projectDescription);
          if (result) {
            routingContext.response().setStatusCode(200).end("insert succeed");
          } else {
            routingContext.response().setStatusCode(400).end("insert failed");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
  }


  public void freshNewTask() {
    router.put("/api/task").consumes("application/json").handler(routingContext -> {
      routingContext.request().bodyHandler(buffer -> {
        JsonObject jsonObject = buffer.toJsonObject();
        int id = jsonObject.getInteger("id");
        int releaseType = jsonObject.getInteger("releaseType");
        String proposal = jsonObject.getString("proposal");
        String title = jsonObject.getString("title");
        String projectURL = jsonObject.getString("projectURL");
        String projectDescription = jsonObject.getString("projectDescription");
        try {
          boolean result = releaseTaskService.updateNewTask(id, releaseType, proposal, title, projectURL, projectDescription);
          if (result) {
            routingContext.response().setStatusCode(200).end("update succeed");
          } else {
            routingContext.response().setStatusCode(400).end("update failed");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
  }

  public void disposalTask() {
    router.put("/api/disposaltask").consumes("application/json").handler(routingContext -> {
      JsonObject userInfo = routingContext.session().get("userInfo");
      if (userInfo == null || userInfo.getString("name") == null) {
        routingContext.response().setStatusCode(401).setStatusMessage("authentication failed,please login").end();
        return;
      }
      routingContext.request().bodyHandler(buffer -> {
        JsonObject jsonObject = buffer.toJsonObject();
        int id = jsonObject.getInteger("id");
        int status1 = jsonObject.getInteger("status");
        byte status = (byte) status1;
        String releaseRemark = jsonObject.getString("releaseRemark");
        try {
          boolean result = releaseTaskService.manageNewTask(id, status, releaseRemark);
          if (result) {
            routingContext.response().setStatusCode(200).setStatusMessage("disposal succeed").end();

          } else {
            routingContext.response().setStatusCode(400).setStatusMessage("disposal failed").end();

          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
  }


  public void searchNewTask() {
    router.get("/api/tasks").handler(routingContext -> {
      try {
        MultiMap queryParams = routingContext.request().params();
        int pagesize = Integer.valueOf(queryParams.get("pageSize"));
        int currentpaged = Integer.valueOf(queryParams.get("currentPage"));


        JsonObject tasks = new JsonObject();

        JsonArray items = new JsonArray();
        List<ReleaseTask> releaseTasks = releaseTaskService.queryAll(pagesize, currentpaged).getReleaseTasks();
        int total = releaseTaskService.queryAll(pagesize, currentpaged).getTotalCount();

        for (ReleaseTask releaseTask : releaseTasks) {
          JsonObject task = releaseTask.toJson();
          items.add(task);
        }

        tasks
          .put("total", total)
          .put("items", items);
        routingContext.response().end(tasks.encode());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
