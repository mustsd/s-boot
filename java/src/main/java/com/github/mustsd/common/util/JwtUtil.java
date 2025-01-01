package com.github.mustsd.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author yangz
 * @date 2022-02-24 15:13
 */
@Slf4j
public class JwtUtil {

  public static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 6;

  /**
   * 校验token是否正确
   *
   * @param token 密钥
   * @param secret 用户的密码
   * @return 是否正确
   */
  public static void verify(String token, String key, String val, String secret) {
    // 根据密码生成JWT效验器
    Algorithm algorithm = Algorithm.HMAC256(secret);
    JWTVerifier verifier = JWT.require(algorithm).withClaim(key, val).build();
    // 效验TOKEN
    verifier.verify(token);
  }

  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @return token中包含的用户名
   */
  public static String getValByKey(String token, String key) {
    try {
      return getDecodedJWT(token).getClaim(key).asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  /**
   * 生成签名,5min后过期
   *
   * @param key 参数名称
   * @param val 参数值
   * @param secret 用户的密码
   * @return 加密的token
   */
  public static String sign(String key, String val, String secret, long expireTime) {
    Date date = new Date(System.currentTimeMillis() + expireTime);
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // 附带username信息
    return JWT.create().withClaim(key, val).withExpiresAt(date).sign(algorithm);
  }

  /**
   * 判断是否过期
   *
   * @param token
   * @return
   */
  public static boolean isTokenExpired(String token) {
    log.info("过期时间 {}", getExpireDate(token));
    log.info("当前时间 {}", new Date());
    return getExpireDate(token).before(new Date());
  }

  /**
   * 获取过期时间
   *
   * @param token
   * @return
   */
  public static Date getExpireDate(String token) {
    return getDecodedJWT(token).getExpiresAt();
  }

  /**
   * 解码j-token
   *
   * @param token
   * @return
   */
  public static DecodedJWT getDecodedJWT(String token) {
    return JWT.decode(token);
  }
}
