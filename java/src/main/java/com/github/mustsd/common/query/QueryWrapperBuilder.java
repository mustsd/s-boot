package com.github.mustsd.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.github.mustsd.common.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Query wrapper builder.
 *
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
public class QueryWrapperBuilder {

  private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>();

  private static SimpleDateFormat getSimpleDateFormat() {
    SimpleDateFormat format = local.get();
    if (format == null) {
      format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      local.set(format);
    }
    return format;
  }

  /**
   * build wrapper
   *
   * @param searchObj
   * @param request
   * @param <T>
   * @return
   */
  public static <T> QueryWrapper<T> build(T searchObj, HttpServletRequest request) {
    return build(searchObj, request.getParameterMap());
  }

  /**
   * build wrapper
   *
   * @param searchObj
   * @param parameterMap
   * @param <T>
   * @return
   */
  public static <T> QueryWrapper<T> build(T searchObj, Map<String, String[]> parameterMap) {
    QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
    installWithCondition(queryWrapper, searchObj, parameterMap);
    return queryWrapper;
  }

  /**
   * @param queryWrapper
   * @param searchObj
   * @param parameterMap
   */
  private static void installWithCondition(
      QueryWrapper<?> queryWrapper, Object searchObj, Map<String, String[]> parameterMap) {
    PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(searchObj);
    for (PropertyDescriptor descriptor : descriptors) {
      String name = descriptor.getName();
      String type = descriptor.getPropertyType().getTypeName();
      String beginValue, endValue;
      if (parameterMap != null && parameterMap.containsKey(name + ConditionConstant.BEGIN)) {
        beginValue = parameterMap.get(name + ConditionConstant.BEGIN)[0].trim();
        addQueryByRule(queryWrapper, name, type, beginValue, QueryRuleEnum.GE);
      }
      if (parameterMap != null && parameterMap.containsKey(name + ConditionConstant.END)) {
        endValue = parameterMap.get(name + ConditionConstant.END)[0].trim();
        addQueryByRule(queryWrapper, name, type, endValue, QueryRuleEnum.LE);
      }
      // multi values.
      if (parameterMap != null && parameterMap.containsKey(name + ConditionConstant.MULTI)) {
        endValue = parameterMap.get(name + ConditionConstant.MULTI)[0].trim();
        addQueryByRule(
            queryWrapper,
            name.replace(ConditionConstant.MULTI, ""),
            type,
            endValue,
            QueryRuleEnum.IN);
      }
      try {
        Object value = PropertyUtils.getSimpleProperty(searchObj, name);
        QueryRuleEnum rule = convert2Rule(value);
        addEasyQuery(queryWrapper, name, replaceKeyWord(rule, value), rule);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // order
    sortByMultiFields(queryWrapper, parameterMap);
  }

  private static void sortByMultiFields(
      QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap) {
    String column = null, order = null;
    if (parameterMap != null && parameterMap.containsKey(ConditionConstant.ORDER_COLUMN)) {
      column = parameterMap.get(ConditionConstant.ORDER_COLUMN)[0];
    }
    if (parameterMap != null && parameterMap.containsKey(ConditionConstant.ORDER_TYPE)) {
      order = parameterMap.get(ConditionConstant.ORDER_TYPE)[0];
    }
    if (StringUtils.hasText(column) && StringUtils.hasText(order)) {
      if (SqlInjectionUtils.check(column)) {
        throw new RuntimeException("Illegal condition.");
      }
      boolean isAsc = order.toUpperCase().equals(ConditionConstant.ORDER_TYPE_ASC);
      queryWrapper.orderBy(true, isAsc, CommonUtils.camelToUnderline(column));
    }
  }

  private static void addQueryByRule(
      QueryWrapper<?> queryWrapper, String name, String type, String value, QueryRuleEnum rule) {
    if (StringUtils.isEmpty(value)) {
      return;
    }
    Object temp;
    if (value.indexOf(ConditionConstant.COMMA) != -1) {
      temp = value;
      addEasyQuery(queryWrapper, name, temp, rule);
      return;
    }
    switch (type) {
      case "java.lang.Integer":
        temp = Integer.parseInt(value);
        break;
      case "java.math.BigDecimal":
        temp = new BigDecimal(value);
        break;
      case "java.lang.Short":
        temp = Short.parseShort(value);
        break;
      case "java.lang.Long":
        temp = Long.parseLong(value);
        break;
      case "java.lang.Float":
        temp = Float.parseFloat(value);
        break;
      case "java.lang.Double":
        temp = Double.parseDouble(value);
        break;
      case "java.util.Date":
        temp = getDateQueryByRule(value, rule);
        break;
      default:
        temp = value;
        break;
    }
    addEasyQuery(queryWrapper, name, temp, rule);
  }

  private static void addEasyQuery(
      QueryWrapper<?> queryWrapper, String name, Object value, QueryRuleEnum rule) {
    if (rule == null || StringUtils.isEmpty(value)) {
      return;
    }
    name = CommonUtils.camelToUnderline(name);
    switch (rule) {
      case GT:
        queryWrapper.gt(name, value);
        break;
      case GE:
        queryWrapper.ge(name, value);
        break;
      case LT:
        queryWrapper.lt(name, value);
        break;
      case LE:
        queryWrapper.le(name, value);
        break;
      case EQ:
        queryWrapper.eq(name, value);
        break;
      case NE:
        queryWrapper.ne(name, value);
        break;
      case IN:
        if (value instanceof String) {
          queryWrapper.in(name, (Object[]) value.toString().split(","));
        } else if (value instanceof String[]) {
          queryWrapper.in(name, (Object[]) value);
        } else {
          queryWrapper.in(name, value);
        }
        break;
      case LIKE:
        queryWrapper.like(name, value);
        break;
      case LEFT_LIKE:
        queryWrapper.likeLeft(name, value);
        break;
      case RIGHT_LIKE:
        queryWrapper.likeRight(name, value);
        break;
      default:
        log.info("--Query rule not matched.---");
        break;
    }
  }

  private static Date getDateQueryByRule(String value, QueryRuleEnum rule) {
    Date date = null;
    try {
      if (value.length() == 10) {
        if (rule == QueryRuleEnum.GE) {
          // 比较大于
          date = getSimpleDateFormat().parse(value + " 00:00:00");
        } else if (rule == QueryRuleEnum.LE) {
          // 比较小于
          date = getSimpleDateFormat().parse(value + " 23:59:59");
        }
      }
      if (date == null) {
        date = getSimpleDateFormat().parse(value);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  private static QueryRuleEnum convert2Rule(Object value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    String val = (value + "").trim();
    if (val.length() == 0) {
      return null;
    }
    QueryRuleEnum rule = null;

    // 优先判断 >= =<
    if (rule == null && val.length() >= 3) {
      if (ConditionConstant.QUERY_SEPARATE_KEYWORD.equals(val.substring(2, 3))) {
        rule = QueryRuleEnum.getByKey(val.substring(0, 2));
      }
    }
    // 判断 > <
    if (rule == null && val.length() >= 2) {
      if (ConditionConstant.QUERY_SEPARATE_KEYWORD.equals(val.substring(1, 2))) {
        rule = QueryRuleEnum.getByKey(val.substring(0, 1));
      }
    }
    // 判断 like
    if (rule == null && val.contains(ConditionConstant.STAR)) {
      if (val.startsWith(ConditionConstant.STAR) && val.endsWith(ConditionConstant.STAR)) {
        rule = QueryRuleEnum.LIKE;
      } else if (val.startsWith(ConditionConstant.STAR)) {
        rule = QueryRuleEnum.LEFT_LIKE;
      } else if (val.endsWith(ConditionConstant.STAR)) {
        rule = QueryRuleEnum.RIGHT_LIKE;
      }
    }
    // 判断 in
    if (rule == null && val.contains(ConditionConstant.COMMA)) {
      rule = QueryRuleEnum.IN;
    }
    // 判断 !=
    if (rule == null && val.startsWith(ConditionConstant.NOT_EQUAL)) {
      rule = QueryRuleEnum.NE;
    }
    return rule != null ? rule : QueryRuleEnum.EQ;
  }

  private static Object replaceKeyWord(QueryRuleEnum rule, Object value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    if (!(value instanceof String)) {
      return value;
    }
    String val = (value + "").trim();
    if (rule == QueryRuleEnum.LIKE) {
      value = val.substring(1, val.length() - 1);
    } else if (rule == QueryRuleEnum.LEFT_LIKE || rule == QueryRuleEnum.NE) {
      value = val.substring(1);
    } else if (rule == QueryRuleEnum.RIGHT_LIKE) {
      value = val.substring(0, val.length() - 1);
    } else if (rule == QueryRuleEnum.IN) {
      value = val.split(",");
    }
    return value;
  }
}
