package com.github.mustsd.common.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangz
 * @date 2022-02-23 15:13
 */
public class CommonUtils {

  private static Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

  public static String underlineToCamel(String str) {
    // 正则匹配下划线及后一个字符&#xff0c;删除下划线并将匹配的字符转成大写
    Matcher matcher = UNDERLINE_PATTERN.matcher(str);
    StringBuffer sb = new StringBuffer(str);
    if (matcher.find()) {
      sb = new StringBuffer();
      // 将当前匹配的子串替换成指定字符串&#xff0c;并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
      // 正则之前的字符和被替换的字符
      matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
      // 把之后的字符串也添加到StringBuffer对象中
      matcher.appendTail(sb);
    } else {
      // 去除除字母之外的前面带的下划线
      return sb.toString().replaceAll("_", "");
    }
    return underlineToCamel(sb.toString());
  }

  /**
   * @param request IP
   * @return IP Address
   */
  public static String getIpAddressByRequest(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public static String camelToUnderline(String str) {
    if (str.length() < 3) {
      return str.toLowerCase();
    }
    StringBuilder sb = new StringBuilder(str);
    int temp = 0;
    // 从第三个字符开始 避免命名不规范
    for (int i = 2; i < str.length(); i++) {
      if (Character.isUpperCase(str.charAt(i))) {
        sb.insert(i + temp, "_");
        temp += 1;
      }
    }
    return sb.toString().toLowerCase();
  }

  /**
   * 获取类的所有属性，包括父类
   *
   * @param object
   * @return
   */
  public static Field[] getAllFields(Object object) {
    Class<?> clazz = object.getClass();
    List<Field> fieldList = new ArrayList<>();
    while (clazz != null) {
      fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
      clazz = clazz.getSuperclass();
    }
    Field[] fields = new Field[fieldList.size()];
    fieldList.toArray(fields);
    return fields;
  }

  /**
   * 字典切面专用空判断
   *
   * @param object
   * @return
   */
  public static boolean isEmpty(Object object) {
    if (object == null) {
      return (true);
    }
    if ("".equals(object)) {
      return (true);
    }
    if ("null".equals(object)) {
      return (true);
    }
    return (false);
  }
}
