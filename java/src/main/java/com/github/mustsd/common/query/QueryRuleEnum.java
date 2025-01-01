package com.github.mustsd.common.query;

import org.springframework.util.StringUtils;

/**
 * Query rule.
 *
 * @author yangz
 * @date 2022-02-23 14:18
 */
public enum QueryRuleEnum {
  GT(">", "gt", "大于"),
  GE(">=", "ge", "大于等于"),
  LT("<", "lt", "小于"),
  LE("<=", "le", "小于等于"),
  EQ("=", "eq", "等于"),
  NE("!=", "ne", "不等于"),
  IN("IN", "in", "包含"),
  LIKE("LIKE", "like", "全模糊"),
  LEFT_LIKE("LEFT_LIKE", "left_like", "左模糊"),
  RIGHT_LIKE("RIGHT_LIKE", "right_like", "右模糊"),
  SQL_RULES("USE_SQL_RULES", "ext", "自定义SQL片段");

  private String key;

  private String value;

  private String desc;

  QueryRuleEnum(String key, String value, String desc) {
    this.key = key;
    this.value = value;
    this.desc = desc;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public String getDesc() {
    return desc;
  }

  public static QueryRuleEnum getByKey(String key) {
    if (StringUtils.isEmpty(key)) {
      return null;
    }
    for (QueryRuleEnum ruleEnum : values()) {
      if (ruleEnum.getKey().equals(key) || ruleEnum.getValue().equals(key)) {
        return ruleEnum;
      }
    }
    return null;
  }
}
