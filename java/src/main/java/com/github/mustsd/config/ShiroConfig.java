package com.github.mustsd.config;

import com.github.mustsd.modules.shiro.JwtAccessControlFilter;
import com.github.mustsd.modules.shiro.ShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangz
 * @date 2022-03-01 11:30
 */
@Slf4j
@Configuration
public class ShiroConfig {

  @Value("${config.shiro.exclude}")
  private String excludeUrls;

  @Bean("shiroFilter")
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    if (StringUtils.hasText(excludeUrls)) {
      String[] permissionUrl = excludeUrls.split(",");
      for (String url : permissionUrl) {
        filterChainDefinitionMap.put(url, "anon");
      }
    }

    filterChainDefinitionMap.put("/html/**", "anon");

    filterChainDefinitionMap.put("/auth/login", "anon");
    filterChainDefinitionMap.put("/auth/captcha/*", "anon");

    filterChainDefinitionMap.put("/generic/**", "anon");
    filterChainDefinitionMap.put("/", "anon");
    filterChainDefinitionMap.put("/doc.html", "anon");
    filterChainDefinitionMap.put("/**/*.js", "anon");
    filterChainDefinitionMap.put("/**/*.css", "anon");
    filterChainDefinitionMap.put("/**/*.html", "anon");
    filterChainDefinitionMap.put("/**/*.svg", "anon");
    filterChainDefinitionMap.put("/**/*.pdf", "anon");
    filterChainDefinitionMap.put("/**/*.jpg", "anon");
    filterChainDefinitionMap.put("/**/*.png", "anon");
    filterChainDefinitionMap.put("/**/*.ico", "anon");

    filterChainDefinitionMap.put("/**/*.ttf", "anon");
    filterChainDefinitionMap.put("/**/*.woff", "anon");
    filterChainDefinitionMap.put("/**/*.woff2", "anon");

    filterChainDefinitionMap.put("/druid/**", "anon");
    filterChainDefinitionMap.put("/swagger-ui.html", "anon");
    filterChainDefinitionMap.put("/swagger**/**", "anon");
    filterChainDefinitionMap.put("/webjars/**", "anon");
    filterChainDefinitionMap.put("/v2/**", "anon");

    filterChainDefinitionMap.put("/actuator/metrics/**", "anon");
    filterChainDefinitionMap.put("/actuator/httptrace/**", "anon");
    filterChainDefinitionMap.put("/actuator/redis/**", "anon");

    filterChainDefinitionMap.put("/websocket/**", "anon");

    Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
    filterMap.put("jwt", new JwtAccessControlFilter());
    shiroFilterFactoryBean.setFilters(filterMap);

    filterChainDefinitionMap.put("/**", "jwt");

    shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
    shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  @Bean("securityManager")
  public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myRealm);

    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =
        new DefaultSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);
    return securityManager;
  }

  /**
   * 下面的代码是添加注解支持
   *
   * @return
   */
  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
        new DefaultAdvisorAutoProxyCreator();
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    defaultAdvisorAutoProxyCreator.setUsePrefix(true);
    defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
    return defaultAdvisorAutoProxyCreator;
  }

  @Bean
  public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }
}
