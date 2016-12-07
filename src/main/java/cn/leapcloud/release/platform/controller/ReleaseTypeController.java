package cn.leapcloud.release.platform.controller;

import cn.leapcloud.release.platform.service.ReleaseTypeService;
import cn.leapcloud.release.platform.service.domain.ReleaseType;
import com.google.inject.Inject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.List;

/**
 * Created by songqian on 16/12/7.
 */
public class ReleaseTypeController {
  private ReleaseTypeService releaseTypeService;
  private Router router;

  @Inject
  public ReleaseTypeController(ReleaseTypeService releaseTypeService, Router router) {
    this.releaseTypeService = releaseTypeService;
    this.router = router;
    searchType();
  }

  public void searchType() {
    router.get("/alltype").handler(routingContext -> {

      try {
        JsonArray types = new JsonArray();
        List<ReleaseType> releaseTypes = releaseTypeService.queryAll();
        for (ReleaseType releaseType : releaseTypes) {
          JsonObject jsonObject = releaseType.toJson();
          types.add(jsonObject);
        }
        routingContext.response().end(types.encode());
      } catch (Exception e) {
        e.printStackTrace();
      }

    });
  }
}
