package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.ReleaseTaskService;
import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

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
  }

  public void insertNewTask() {
    router.post("/task").consumes("application/json").handler(routingContext -> {
      routingContext.request().bodyHandler(buffer -> {
        JsonObject jsonObject = buffer.toJsonObject();
        int releaseType = jsonObject.getInteger("releaseType");
        String proposal = jsonObject.getString("proposal");
        String title = jsonObject.getString("title");
        String projectURL = jsonObject.getString("projectURL");
        String projectDescription = jsonObject.getString("projectDescription");


        try {
          boolean result = releaseTaskService.createNewTask(releaseType, proposal, title, projectURL, projectDescription);
          if (result) {
            routingContext.response().end("insert succeed");
          } else {
            routingContext.response().end("insert failed");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
  }

  public void freshNewTask() {
    router.put("/changetask").consumes("application/json").handler(routingContext -> {
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
            routingContext.response().end("update succeed");
          } else {
            routingContext.response().end("update failed");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
  }
}
