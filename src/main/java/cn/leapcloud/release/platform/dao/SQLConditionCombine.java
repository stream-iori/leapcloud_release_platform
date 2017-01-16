package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.controller.ConditionParser;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stream.
 */
public class SQLConditionCombine {

  private <R extends Record> Condition matchCondition(ConditionParser.SQLCondition sqlCondition, TableImpl<R> table) {


    Field field = table.field(sqlCondition.getField());
    Object value = sqlCondition.getValue();
    Condition condition = null;
    switch (sqlCondition.getOp()) {
      case AND:
        List<ConditionParser.SQLCondition> andSqlConditions = (List<ConditionParser.SQLCondition>) value;
        ConditionParser.SQLCondition firstAndCondition = andSqlConditions.get(0);
        condition = matchCondition(firstAndCondition, table);
        andSqlConditions = andSqlConditions.stream().skip(1).collect(Collectors.toList());

        for (ConditionParser.SQLCondition sqlCond : andSqlConditions)
          condition = condition.and(matchCondition(sqlCond, table));
        break;
      case OR:
        List<ConditionParser.SQLCondition> orSqlConditions = (List<ConditionParser.SQLCondition>) value;
        ConditionParser.SQLCondition firstOrCondition = orSqlConditions.get(0);
        condition = matchCondition(firstOrCondition, table);
        orSqlConditions = orSqlConditions.stream().skip(1).collect(Collectors.toList());

        for (ConditionParser.SQLCondition sqlCond : orSqlConditions)
          condition = condition.or(matchCondition(sqlCond, table));
        break;
      case EQ:
        condition = field.eq(value);
        break;
      case LT:
        condition = field.lt(value);
        break;
      case LTE:
        condition = field.le(value);
        break;
      case GT:
        condition = field.gt(value);
        break;
      case GTE:
        condition = field.ge(value);
        break;
      case NE:
        condition = field.ne(value);
        break;
      case IN:
        condition = field.in(((List) value).toArray());
        break;
      case NIN:
        condition = field.notIn(((List) value).toArray());
        break;
      case LIKE:
        condition = field.like("%" + value);
        break;
      case REGULAR:
        field.likeRegex("" + value);
        break;
      default:
        throw new UnsupportedOperationException("do not supper " + sqlCondition.getOp().name());
    }
    return condition;
  }

  /**
   * 单表
   */
  public <R extends Record> SelectConditionStep<R> combineCondition(TableImpl<R> table,
                                                                    SelectWhereStep<R> whereStep,
                                                                    ConditionParser.SQLCondition sqlCondition) {
    return whereStep.where(matchCondition(sqlCondition, table));
  }

}
