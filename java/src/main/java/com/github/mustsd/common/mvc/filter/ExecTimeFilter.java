package com.github.mustsd.common.mvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求执行时间过滤器
 *
 * @author yangz
 */
@Slf4j
@Profile("dev")
@Component
public class ExecTimeFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String uri = request.getRequestURI();
    Long a = System.currentTimeMillis();
    filterChain.doFilter(request, response);
    Long b = System.currentTimeMillis();
    if (!request.getMethod().equals(RequestMethod.OPTIONS.name())) {
      log.info("URI={}  Execute time={}ms", uri, b - a);
    }
  }
}
