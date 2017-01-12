package cn.leapcloud.release.platform.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Optional;
import java.util.stream.Collectors;

import static cn.leapcloud.release.platform.controller.ConditionParser.SQLOperation.EQ;

/**
 * 用来解析SQL的where语句
 * 具体API参考 http://www.maxwon.cn/website/docs/doc_api/jianjie/jianjie.html#查询参数支持
 * Created by stream on 11/01/2017.
 */
public class ConditionParser {

  public SQLCondition getSQLCondition(JsonObject op) {
    String alias = op.fieldNames().iterator().next();
    Object value = op.getValue(alias);
    SQLCondition SQLCondition = new SQLCondition();

    if (value instanceof Number) {
      SQLCondition.field = alias;
      SQLCondition.op = EQ;
      SQLCondition.value = value;
      return SQLCondition;
    }

    if (alias.equals("$and") || alias.equals("$or")) {
      SQLCondition.isMultiCondition = true;
      SQLCondition.op = SQLOperation.valueOf(alias);
      SQLCondition.value = ((JsonArray) value).stream()
        .map(jsonValue -> getSQLCondition((JsonObject) jsonValue))
        .collect(Collectors.toList());
      return SQLCondition;
    }

    JsonObject opObject = (JsonObject) value;
    final String conditionOpField = opObject.fieldNames().iterator().next();
    if (!conditionOpField.startsWith("$"))
      throw new IllegalArgumentException("condition token illegal, operator token should start with $");

    SQLCondition.field = alias;
    SQLCondition.op = Optional.of(SQLOperation.valueOf(conditionOpField.substring(1)))
      .orElseThrow(() -> new IllegalArgumentException("no such sql operation token."));

    switch (SQLCondition.op) {
      case IN:
      case NIN:
        SQLCondition.value = ((JsonArray) opObject.getValue(conditionOpField)).getList();
        break;
      default:
        SQLCondition.value = opObject.getValue(conditionOpField);
        break;
    }
    return SQLCondition;
  }

  public class SQLCondition {
    private boolean isMultiCondition;
    private String field;
    private SQLOperation op;
    private Object value;

    public boolean isMultiCondition() {
      return isMultiCondition;
    }

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
