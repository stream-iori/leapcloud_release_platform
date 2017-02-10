package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.controller.ConditionParser;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.*;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static cn.leapcloud.release.platform.Base.vertx;

/**
 * Created by songqian on 17/1/12.
 */
@RunWith(VertxUnitRunner.class)
public class JsonTest {

  @Test
  public void mail(TestContext context) {
    Async async = context.async();
    MailConfig mailConfig = new MailConfig();
    mailConfig.setHostname("smtp.partner.outlook.cn");
    mailConfig.setPort(587);
    mailConfig.setStarttls(StartTLSOptions.OPTIONAL);
    mailConfig.setTrustAll(true);
    mailConfig.setLogin(LoginOption.REQUIRED);
    mailConfig.setUsername("alert@maxleap.com");
    mailConfig.setPassword("Yaqu8132");


    MailClient mailClient = MailClient.createNonShared(vertx, mailConfig);
    MailMessage mailMessage = new MailMessage();
    mailMessage.setFrom("alert@maxleap.com");
    mailMessage.setTo("qsong@maxleap.com");
    mailMessage.setSubject("待发布项目");
    mailMessage.setHtml("<a href='http://10.10.10.196'>点我</a>");


    mailClient.sendMail(mailMessage, mailResultAsyncResult -> {
      if (mailResultAsyncResult.succeeded()) {
        System.out.println(mailResultAsyncResult.result());
        async.complete();
      } else {
        mailResultAsyncResult.cause().printStackTrace();
      }
    });
  }

  @Test
  public void testEq() {
    ConditionParser conditionParser = new ConditionParser();

    JsonObject jsonObject1 = new JsonObject();
    jsonObject1.put("$eq", 20);
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.put("status", jsonObject1);


//    ConditionParser.SQLCondition sqlCondition = conditionParser.getSQLCondition(jsonObject1);
//    Assert.assertEquals("age", sqlCondition.getField());
//    Assert.assertEquals(sqlCondition.getOp(), ConditionParser.SQLOperation.EQ);
//    Assert.assertEquals(sqlCondition.getValue(), 20);
    System.out.println(jsonObject2);
  }

  @Test
  public void testIn() {
    ConditionParser conditionParser = new ConditionParser();

    ArrayList<String> arrayList = new ArrayList<String>();
    arrayList.add("Anhui");
    arrayList.add("Shanghai");
    arrayList.add("Beijing");
    System.out.println(arrayList.toString());
    JsonArray jsonArray = new JsonArray(arrayList);
    System.out.println(jsonArray);
    JsonObject jsonObject1 = new JsonObject();
    jsonObject1.put("$in", jsonArray);
    System.out.println(jsonObject1);
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.put("city", jsonObject1);
    System.out.println(jsonObject2);

    ConditionParser.SQLCondition sqlCondition = conditionParser.getSQLCondition(jsonObject2);
    Assert.assertEquals(sqlCondition.getField(), "city");
    Assert.assertEquals(sqlCondition.getOp(), ConditionParser.SQLOperation.IN);
    System.out.println(sqlCondition.getValue());

  }

  @Test
  public void testAnd() {
    ConditionParser conditionParser = new ConditionParser();
    JsonObject jsonObject1 = new JsonObject();
    jsonObject1.put("$ne", 20);
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.put("$lt", 1000);
    JsonObject jsonObject3 = new JsonObject();
    JsonObject jsonObject4 = new JsonObject();
    jsonObject3.put("price", jsonObject1);
    jsonObject4.put("price", jsonObject2);
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(jsonObject3);
    jsonArray.add(jsonObject4);
    JsonObject jsonObject5 = new JsonObject();
    jsonObject5.put("$and", jsonArray);
    System.out.println(jsonObject5);

    ConditionParser.SQLCondition sqlCondition = conditionParser.getSQLCondition(jsonObject5);

    List<ConditionParser.SQLCondition> sqlConditions = (ArrayList) sqlCondition.getValue();

    Assert.assertEquals("price", sqlConditions.get(0).getField());

    Assert.assertEquals(ConditionParser.SQLOperation.NE, sqlConditions.get(0).getOp());

    Assert.assertEquals(20, sqlConditions.get(0).getValue());


  }


//  @Test
//  public void testDing(TestContext context) {
//    Async async = context.async();
//    DingPerform dingPerform = new DingPerform(vertx);
//    dingPerform.sendDing("hello",2);
//
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//
//    async.complete();
//  }
}

