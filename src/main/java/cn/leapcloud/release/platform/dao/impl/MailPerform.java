package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.service.UserService;
import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.*;

/**
 * Created by songqian on 17/1/24.
 */
public class MailPerform {

  private Vertx vertx;
  private JsonObject mailObject;
  private UserService service;

  @Inject
  public MailPerform(Vertx vertx, JsonObject config, UserService userService) {
    this.vertx = vertx;
    mailObject = config.getJsonObject("mail");
    this.service = userService;

  }

  public void sendMail() {
    MailConfig mailConfig = new MailConfig();
    mailConfig.setHostname(mailObject.getString("hostname"));
    mailConfig.setPort(mailObject.getInteger("port"));
    mailConfig.setStarttls(StartTLSOptions.OPTIONAL);
    mailConfig.setTrustAll(true);
    mailConfig.setLogin(LoginOption.REQUIRED);
    mailConfig.setUsername(mailObject.getString("username"));
    mailConfig.setPassword(mailObject.getString("password"));


    MailClient mailClient = MailClient.createNonShared(vertx, mailConfig);


    MailMessage mailMessage = new MailMessage();
    mailMessage.setFrom(mailObject.getString("username"));
//    mailMessage.setTo(service.find().getClass());
    mailMessage.setSubject("待发布项目");
    mailMessage.setHtml("this is html text <a href='http://10.10.10.196'>点我</a>");

    mailClient.sendMail(mailMessage, result -> {
      if (result.succeeded()) {
        System.out.println("邮件发送成功");
      } else {
        result.cause().printStackTrace();
      }

    });
  }
}



