package cn.leapcloud.release.platform.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cn.leapcloud.release.platform.controller.ConditionParser.SQLOperation.EQ;

/**
 * 用来解析SQL的where语句
 * 具体API参考 http://www.maxwon.cn/website/docs/doc_api/jianjie/jianjie.html#查询参数支持
 * Created by stream on 11/01/2017.
 */
public class ConditionParser {

  public ConditionParser(){

  }

  public SQLCondition getSQLCondition(JsonObject op) {
    String alias = op.fieldNames().iterator().next();
    Object value = op.getValue(alias);
    SQLCondition sqlCondition = new SQLCondition();

    if (!(value instanceof JsonObject) && !(value instanceof JsonArray)) {
      sqlCondition.field = alias;
      sqlCondition.op = EQ;
      sqlCondition.value = value;
      return sqlCondition;
    }

    if (alias.toUpperCase().equals("$AND") || alias.toUpperCase().equals("$OR")) {
      sqlCondition.op = SQLOperation.valueOf(alias.toUpperCase().substring(1));
      List<SQLCondition> sqlConditions = new ArrayList<>();
      for (Object jsonValue : ((JsonArray) value).getList()) {
        sqlConditions.add(getSQLCondition((JsonObject) jsonValue));
      }
      sqlCondition.value = sqlConditions;
      return sqlCondition;
    }

    JsonObject opObject = (JsonObject) value;
    final String conditionOpField = opObject.fieldNames().iterator().next();
    if (!conditionOpField.startsWith("$")) {
      throw new IllegalArgumentException("condition token illegal, operator token should start with $");
    }

    sqlCondition.field = alias;
    sqlCondition.op = Optional.of(SQLOperation.valueOf(conditionOpField.substring(1).toUpperCase()))
      .orElseThrow(() -> new IllegalArgumentException("no such sql operation token."));

    switch (sqlCondition.op) {
      case IN:
      case NIN:
        sqlCondition.value = ((JsonArray) opObject.getValue(conditionOpField)).getList();
        break;
      default:
        sqlCondition.value = opObject.getValue(conditionOpField);
        break;
    }
    return sqlCondition;
  }

  public class SQLCondition {
    private String field;
    private SQLOperation op;
    private Object value;

    public String getField() {
      return field;
    }

    public SQLOperation getOp() {
      return op;
    }

    public Object getValue() {
      return value;
    }
  }

  public enum SQLOperation {
    LT, LTE, GT, GTE, EQ, NE, IN, NIN, EXISTS, LIKE, REGULAR, AND, OR
  }

}
