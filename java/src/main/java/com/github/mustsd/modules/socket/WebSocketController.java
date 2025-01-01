package com.github.mustsd.modules.socket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.socket.service.ISocketService;
import com.github.mustsd.modules.socket.vo.Notify;
import com.github.mustsd.modules.system.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangz
 * @date 2022-03-26 14:00
 */
@Slf4j
@RestController
@Api(tags = "socket模块")
@RequestMapping("/sys/socket")
public class WebSocketController {

  @Autowired
  ISocketService socketService;

  @ApiOperation(value = "强制登出用户", notes = "强制登出用户")
  @PostMapping(value = "/notify")
  public Result<?> notify(@RequestBody Notify notify) {
    socketService.notify(notify);
    return Result.ok();
  }

  @ApiOperation(value = "强制登出用户", notes = "强制登出用户")
  @GetMapping(value = "/forceLogout")
  public Result<?> forceLogout(@RequestParam("account") String account) {
    socketService.forceLogout(account);
    return Result.ok();
  }

  @ApiOperation(value = "list在线用户", notes = "list在线用户")
  @GetMapping(value = "/listOnlineUser")
  public Result<?> listOnlineUser(
      SysUser user,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysUser> queryWrapper = QueryWrapperBuilder.build(user, req.getParameterMap());
    Page<SysUser> page = new Page<>(pageNo, pageSize);
    socketService.listOnlineUser(page, queryWrapper);
    return Result.ok(page);
  }
}
