package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.Base64;
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
    router.put("/api/task/operate").consumes("application/json").handler(routingContext -> {
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
      String jsonBaseStr = routingContext.request().getParam("query");
      String orderInfo = routingContext.request().getParam("order");

      String skipString = routingContext.request().getParam("skip");
      String limitString = routingContext.request().getParam("limit");
      int skip = skipString == null ? 5 : Integer.valueOf(skipString);
      int limit = limitString == null ? 1 : Integer.valueOf(limitString);

      int i = 0;
      switch (orderInfo) {
        case "releaseType":
          i = 1;
          break;
        case "-releaseType":
          i = 2;
          break;
        case "proposalTime":
          i = 3;
          break;
        case "-proposalTime":
          i = 4;
          break;
        case "updateTime":
          i = 5;
          break;
        case "-updateTime":
          i = 6;
          break;
        case "status":
          i = 7;
          break;
        case "-status":
          i = 8;
          break;
      }
      String jsonStr = new String(Base64.getDecoder().decode(jsonBaseStr));
      JsonObject jsonObject = new JsonObject(jsonStr);

      JsonObject tasks = new JsonObject();
      JsonArray items = new JsonArray();
      List<ReleaseTask> releaseTasks = null;
      int total = 0;

      releaseTasks = releaseTaskService.queryAll(jsonObject, i, skip, limit).getReleaseTasks();
      total = releaseTaskService.queryAll(jsonObject, i, skip, limit).getTotalCount();


      for (ReleaseTask releaseTask : releaseTasks) {
        JsonObject task = releaseTask.toJson();
        items.add(task);
      }

      tasks.put("total", total).put("items", items);
      routingContext.response().end(tasks.encode());


//      try {
//        MultiMap queryParams = routingContext.request().params();
//        String pageSizeStr = queryParams.get("pageSize");
//        String currentPageStr = queryParams.get("currentPage");
//        String releaseType = queryParams.get("releaseType");
//        String releaseStatus = queryParams.get("releaseStatus");
//


//
//        int pageSize = pageSizeStr == null ? 5 : Integer.valueOf(pageSizeStr);
//        int currentPage = currentPageStr == null ? 1 : Integer.valueOf(currentPageStr);
//        JsonObject tasks = new JsonObject();
//        JsonArray items = new JsonArray();
//        List<ReleaseTask> releaseTasks = null;
//        int total = 0;
//        if (releaseStatus == null && releaseType == null) {
//          releaseTasks = releaseTaskService.queryAll(pageSize, currentPage).getReleaseTasks();
//          total = releaseTaskService.queryAll(pageSize, currentPage).getTotalCount();
//        } else if (releaseStatus == null && releaseType != null) {
//
//          int releaseTypeNum = Integer.parseInt(releaseType);
//          releaseTasks = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeNum).getReleaseTasks();
//          total = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeNum).getTotalCount();
//
//
//        } else if (releaseStatus != null && releaseType == null) {
//
//          byte releaseTypeStatus = (byte) Integer.parseInt(releaseStatus);
//
//          releaseTasks = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeStatus).getReleaseTasks();
//          total = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeStatus).getTotalCount();
//
//
//        } else if (releaseStatus != null && releaseType != null) {
//          byte releaseTypeStatus = (byte) Integer.parseInt(releaseStatus);
//          int releaseTypeNum = Integer.parseInt(releaseType);
//
//          releaseTasks = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeNum, releaseTypeStatus).getReleaseTasks();
//          total = releaseTaskService.queryAll(pageSize, currentPage, releaseTypeNum, releaseTypeStatus).getTotalCount();
//
//
//        }
//
//
//        for (ReleaseTask releaseTask : releaseTasks) {
//          JsonObject task = releaseTask.toJson();
//          items.add(task);
//        }
//        tasks
//          .put("total", total)
//          .put("items", items);
//        routingContext.response().end(tasks.encode());
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
    });
  }
}
