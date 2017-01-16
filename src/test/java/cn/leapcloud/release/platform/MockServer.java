package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.Module.GuiceModule;
import cn.leapcloud.release.platform.controller.ConditionParser;
import cn.leapcloud.release.platform.dao.SQLConditionCombine;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static cn.leapcloud.release.platform.dao.entity.Tables.RELEASE_TASK;

/**
 * Created by stream.
 */
public class MockServer {

  private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

  @Ignore
  @Test
  public void mockMain() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Starter(), event -> {
      if (event.succeeded()) {
        logger.info("部署release-platform成功");
      } else {
        logger.error("部署失败", event.cause());
      }
    });
    while (true) {
      Thread.sleep(1000L);
    }
  }

  @Test
  public void combineSql() {
    Vertx vertx = Vertx.vertx();
    JsonObject config = vertx.fileSystem().readFileBlocking("./config.json").toJsonObject();
    Injector injector = Guice.createInjector(new GuiceModule(vertx, config));



    SQLConditionCombine conditionCombine = new SQLConditionCombine();

    DSLContext jooq = injector.getInstance(DSLContext.class);

    ConditionParser conditionParser = new ConditionParser();

    JsonObject jsonObject = new JsonObject();

    jsonObject.put("id", new JsonObject().put("$eq", 16));

    ConditionParser.SQLCondition sqlCondition = conditionParser.getSQLCondition(jsonObject);

    SelectConditionStep<ReleaseTaskRecord> conditionStep = conditionCombine

      .combineCondition(RELEASE_TASK, jooq.selectFrom(RELEASE_TASK), sqlCondition);

    Assert.assertEquals(16L, (long) conditionStep.fetchOne().getId());

    System.out.println(conditionStep.fetch());
  }
}
