package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.service.impl.UserServiceImpl;
import com.google.inject.Inject;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;

import java.sql.Timestamp;


/**
 * Created by songqian on 17/2/7.
 */
public class DingPerform {

  private Vertx vertx;
  private UserServiceImpl userService;

  @Inject
  public DingPerform(Vertx vertx, UserServiceImpl userService) {
    this.vertx = vertx;
    this.userService = userService;
  }

  public void sendDing(String proposal, int releaseType) {

    String releaseName = null;
    switch (releaseType) {
      case 1:
        releaseName = "移动端";
        break;
      case 2:
        releaseName = "前端";
        break;
      case 3:
        releaseName = "后端";
        break;
    }
    final String releaseTypeName = releaseName;


    Future<String> getTokenFuture = Future.future();

    vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true))
      .get(443, "oapi.dingtalk.com", "/gettoken?corpid=dingfbbc837063e04341&corpsecret" +
        "=SAPxdpipPec7B3AIAoUgkobbVm8YrNNRl2io0ITF7NyHBnrsMLYkQOscrrFBUGsi", httpClientResponse -> {


        if (httpClientResponse.statusCode() != 200) {
          getTokenFuture.fail(httpClientResponse.statusMessage());
        } else {
          httpClientResponse.bodyHandler(buffer -> {
            JsonObject jsonObject = buffer.toJsonObject();
            System.out.println(jsonObject.toString());
            String token = jsonObject.getString("access_token");

            getTokenFuture.complete(token);
          });
        }
      }).end();

    getTokenFuture.compose(token -> {
      Future<Boolean> future = Future.future();
      String str1 = "/message/send?access_token=" + token;
      String ddId = userService.find().getDdId();

      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      String dingMessage = timestamp.toString().substring(0, timestamp.toString().length() - 4) + proposal + "在" + releaseTypeName + "有任务需要发布";
      JsonObject jsonObject1 = new JsonObject().put("content", dingMessage);
      JsonObject jsonObject2 = new JsonObject().put("touser", ddId).put("agentid", "75409909")
        .put("msgtype", "text").put("text", jsonObject1);
      vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true))
        .post(443, "oapi.dingtalk.com", str1, httpClientResponse -> {
          httpClientResponse.bodyHandler(buffer -> {
            System.out.println(buffer.toString());
            if (buffer.toJsonObject().getString("errmsg").equals("ok")) {
//              System.out.println("发送信息成功");
              future.complete(true);
            } else {
              future.complete(false);
            }
          });
        }).putHeader("Content-Type", "application/json").end(jsonObject2.encode());
      return future;
    }).setHandler(event -> {
      if (event.succeeded()) {
        event.result();
      } else {
        event.cause().printStackTrace();
      }
    });
  }
}


