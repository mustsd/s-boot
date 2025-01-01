package com.github.mustsd.common.query;

/**
 * Condition constants.
 *
 * @author mustsd
 */
public interface ConditionConstant {

  String BEGIN = "_begin";

  String END = "_end";

  /** */
  String MULTI = "_MultiString";

  String STAR = "*";

  String COMMA = ",";

  String NOT_EQUAL = "!";
  /** Query keyword separator */
  String QUERY_SEPARATE_KEYWORD = " ";
  /** Order column */
  String ORDER_COLUMN = "column";
  /** Order type */
  String ORDER_TYPE = "order";

  String ORDER_TYPE_ASC = "ASC";
}
