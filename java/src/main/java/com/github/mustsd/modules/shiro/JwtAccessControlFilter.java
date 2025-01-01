package com.github.mustsd.modules.shiro;

import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.modules.shiro.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
public class JwtAccessControlFilter extends BasicHttpAuthenticationFilter {

  /**
   * Login authorization.
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return
   */
  @Override
  protected boolean isAccessAllowed(
      ServletRequest request, ServletResponse response, Object mappedValue) {
    try {
      return executeLogin(request, response);
    } catch (Exception e) {
      throw new AuthenticationException("Token invalid, please login again.", e);
    }
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response)
      throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader(CommonConstant.ACCESS_TOKEN);
    if (StringUtils.isEmpty(token)) {
      token = httpServletRequest.getParameter(CommonConstant.ACCESS_TOKEN);
    }
    if (StringUtils.isEmpty(token)) {
      throw new AuthenticationException("Empty token is not allowed. Please login first.");
    }
    JwtToken jwtToken = new JwtToken(token);
    // login realm. Exception if error.
    getSubject(request, response).login(jwtToken);
    return true;
  }

  /** 对跨域提供支持 */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader(
        "Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Headers",
        httpServletRequest.getHeader("Access-Control-Request-Headers"));
    // option
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    try {
      return super.preHandle(request, response);
    } catch (AuthenticationException e) {
      ((HttpServletResponse) response).sendError(401, "Unauthorized");
    }
    return false;
  }

  @Override
  public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception)
      throws Exception {
    // option
    if (!((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
      getSubject(request, response).logout();
    }
    super.afterCompletion(request, response, exception);
  }
}
