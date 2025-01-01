package com.github.mustsd.common.constant;

import java.math.BigDecimal;

/**
 * Common constants
 *
 * @author mustsd
 */
public interface CommonConstant {

  String PRIMARY_KEY = "id";

  String DEFAULT = "default";
  /** comma */
  String E_COMMA = ",";
  /** 中文逗号 */
  String C_COMMA = "，";
  /** minus */
  String M_LINE = "-";

  /** not deleted */
  Integer DEL_FLAG_0 = 0;

  String EMPTY_STR = "";
  /** OK */
  Integer RES_OK = 200;
  /** Error */
  Integer RES_ERROR = 500;
  /** no auth */
  Integer RES_NO_AUTH = 510;
  /** token */
  String ACCESS_TOKEN = "Access-Token";
  /** token */
  String USER_TOKEN_PREFIX = "token:system";

  String DICT_ITEM_VALUE_SUFFIX = "_dictText";
  /** English letters */
  String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
  /** Captcha characters */
  String BASE_CAPTCHA_CHAR = "qwertyupkjhgfdsazxcvbnmQWERTYUPKJHGFDSAZXCVBNM23456789";
  /** minio attachment bucket */
  String ATTACHMENT_BUCKET = "attachment";
  /** minio image bucket */
  String IMAGE_BUCKET = "image";
  /** base64 image prefix */
  String BASE64_IMAGE_PREFIX = "data:image/jpeg;base64,";

  String ACCOUNT = "account";
  /** Logout event */
  String SOCKET_EVENT_LOGOUT = "logout";
  /** Notify event */
  String SOCKET_EVENT_NOTIFY = "notify";
  /** Unread event */
  String SOCKET_EVENT_UNREAD = "unread";
  /** 1MB = 1024 * 1024 byte */
  BigDecimal FILE_SIZE_MB = new BigDecimal("1048576");
}
