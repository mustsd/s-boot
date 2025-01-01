package com.github.mustsd.modules.system.controller;

import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.modules.notice.service.ISysNoticeUserService;
import com.github.mustsd.modules.shiro.CurrentUser;
import com.github.mustsd.modules.system.service.ISysPermissionService;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.system.vo.Captcha;
import com.github.mustsd.modules.system.vo.Login;
import com.github.mustsd.modules.system.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yangz
 * @date 2022-02-24 15:37
 */
@Api(tags = "login")
@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

  @Autowired ISysUserService userService;
  @Autowired ISysPermissionService permissionService;
  @Autowired ISysNoticeUserService noticeUserService;

  @ApiOperation(value = "auth-获取验证码", notes = "auth-获取验证码")
  @GetMapping(value = "/captcha/{key}")
  public Result<?> getCaptcha(@PathVariable String key) {
    Captcha captcha = userService.getCaptcha(key);
    return Result.ok(captcha);
  }

  @ApiOperation(value = "auth-查询当前登录用户权限", notes = "auth-查询当前登录用户权限")
  @GetMapping(value = "/nav")
  public Result<?> listUserAuth() {
    String userId = CurrentUser.getUserId();
    Map<String, Object> authMap = permissionService.listUserAuth(userId);
    return Result.ok(authMap);
  }

  @AutoLog(value = "获取用户信息")
  @ApiOperation(value = "auth-获取用户信息", notes = "auth-获取用户信息")
  @GetMapping(value = "/info")
  public Result<?> userInfo() {
    UserInfo userInfo = CurrentUser.getUser();
    String userId = userInfo.getId();
    long unread = noticeUserService.countUnreadNotice(userId);
    userInfo.setUnread(unread);
    return Result.ok(userInfo);
  }

  @AutoLog(value = "用户登出")
  @ApiOperation(value = "auth-登出", notes = "auth-登出")
  @PostMapping(value = "/logout")
  public Result<?> logout(HttpServletRequest request) {
    String token = request.getHeader(CommonConstant.ACCESS_TOKEN);
    userService.logout(token);
    return Result.ok();
  }

  @ApiOperation(value = "auth-登录", notes = "auth-登录")
  @PostMapping(value = "/login")
  public Result<?> login(HttpServletRequest request, @RequestBody Login login) {
    UserInfo userInfo = userService.login(login);
    return Result.ok(userInfo);
  }
}
