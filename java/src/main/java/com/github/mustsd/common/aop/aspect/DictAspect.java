package com.github.mustsd.common.aop.aspect;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustsd.common.aop.annotation.Dict;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.util.CommonUtils;
import com.github.mustsd.modules.dict.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dict AOP
 *
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Aspect
@Component
public class DictAspect {
  private static final TimedCache<String, String> CACHE;

  static {
    CACHE = CacheUtil.newTimedCache(1000 * 600);
  }

  @Autowired private ISysDictService dictService;

  @Pointcut("@annotation(com.github.mustsd.common.aop.annotation.DictTranslate)")
  public void section() {}

  @Around("section()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Result result = (Result) pjp.proceed();
    Object r = result.getResult();
    if (r instanceof IPage) {
      IPage page = (IPage) r;
      page.setRecords(this.parseDictText(page.getRecords()));
    } else if (r instanceof Collection) {
      result.setResult(this.parseDictText((Collection) r));
    }
    return result;
  }

  private List<JSONObject> parseDictText(Collection<Object> records) {
    List<JSONObject> result =
        records
            .parallelStream()
            .map(
                record -> {
                  ObjectMapper mapper = new ObjectMapper();
                  String json = "{}";
                  try {
                    json = mapper.writeValueAsString(record);
                  } catch (JsonProcessingException e) {
                    log.error("json解析失败" + e.getMessage(), e);
                  }
                  JSONObject item = JSONObject.parseObject(json);
                  for (Field field : CommonUtils.getAllFields(record)) {
                    if (field.isAnnotationPresent(Dict.class)) {
                      Dict dictAnnotation = field.getAnnotation(Dict.class);
                      String code = dictAnnotation.dictCode();
                      String text = dictAnnotation.dictText();
                      String table = dictAnnotation.dictTable();
                      Object obj = item.get(field.getName());
                      String key = String.valueOf(obj == null ? "" : obj);
                      if (StringUtils.isEmpty(key)) {
                        continue;
                      }
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
                      String textValue;
                      if (CACHE.containsKey(cacheKey.toString())) {
                        textValue = CACHE.get(cacheKey.toString());
                      } else {
                        textValue = translateDictValue(code, text, table, key);
                        CACHE.put(cacheKey.toString(), textValue);
                      }
                      item.put(field.getName() + CommonConstant.DICT_ITEM_VALUE_SUFFIX, textValue);
                    }
                    if (field.getType().getName().equals("java.util.Date")
                        && field.getAnnotation(JsonFormat.class) == null
                        && item.get(field.getName()) != null) {
                      SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                      item.put(
                          field.getName(),
                          aDate.format(new Date((Long) item.get(field.getName()))));
                    }
                  }
                  return item;
                })
            .collect(Collectors.toList());
    return result;
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
