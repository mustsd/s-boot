package com.github.mustsd.common.xls;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.github.mustsd.common.aop.annotation.Dict;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.util.CommonUtils;
import com.github.mustsd.common.util.SpringContextUtils;
import com.github.mustsd.modules.dict.service.ISysDictService;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * @author yangz
 * @date 2022-06-20 11:56
 */
public class BaseDictConverter {

  private static final TimedCache<String, String> CACHE;
  private static volatile ISysDictService dictService;

  static {
    CACHE = CacheUtil.newTimedCache(1000 * 600);
  }

  public BaseDictConverter() {
    if (dictService == null) {
      dictService = SpringContextUtils.getBean(ISysDictService.class);
    }
  }

  protected String getValue(Object value, Field field) {
    if (StringUtils.isEmpty(value)) {
      return "";
    }
    if (field.isAnnotationPresent(Dict.class)) {
      String textValue = "";
      Dict dictAnnotation = field.getAnnotation(Dict.class);
      boolean enableExportXls = dictAnnotation.enableExportXls();
      if (!enableExportXls) {
        return value.toString();
      }
      String code = dictAnnotation.dictCode();
      String text = dictAnnotation.dictText();
      String table = dictAnnotation.dictTable();
      String key = String.valueOf(value);
      StringBuilder cacheKey = new StringBuilder();
      if (StringUtils.hasText(table)) {
        cacheKey.append(table);
        cacheKey.append(CommonConstant.M_LINE);
        cacheKey.append(text);
        cacheKey.append(CommonConstant.M_LINE);
        cacheKey.append(code);
        cacheKey.append(CommonConstant.M_LINE);
        cacheKey.append(key);
      } else {
        cacheKey.append(code);
        cacheKey.append(CommonConstant.M_LINE);
        cacheKey.append(key);
      }
      if (CACHE.containsKey(cacheKey.toString())) {
        textValue = CACHE.get(cacheKey.toString());
      } else {
        textValue = translateDictValue(code, text, table, key);
        CACHE.put(cacheKey.toString(), textValue);
      }
      return textValue;
    }
    return value.toString();
  }

  private String translateDictValue(String code, String text, String table, String key) {
    if (CommonUtils.isEmpty(key)) {
      return null;
    }
    StringBuffer textValue = new StringBuffer();
    String[] keys = key.split(",");
    for (String k : keys) {
      String tmpValue;
      if (k.trim().length() == 0) {
        continue;
      }
      if (StringUtils.hasText(table)) {
        tmpValue = dictService.queryTableDictTextByKey(table, text, code, k.trim());
      } else {
        tmpValue = dictService.queryDictTextByKey(code, k.trim());
      }

      if (tmpValue != null) {
        if (!"".equals(textValue.toString())) {
          textValue.append(",");
        }
        textValue.append(tmpValue);
      }
    }
    return textValue.toString();
  }
}
