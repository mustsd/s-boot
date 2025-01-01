package com.github.mustsd.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.system.vo.PwdModify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Api(tags = "sys_user")
@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends BaseController<SysUser, ISysUserService> {

  @ApiOperation(value = "sys_user-用户管理-修改密码", notes = "sys_user-用户管理-修改密码")
  @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
  public Result<?> changePassword(@RequestBody SysUser sysUser) {
    SysUser u =
        this.service.getOne(
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, sysUser.getAccount()));
    if (u == null) {
      return Result.error("用户不存在！");
    }
    u.setNewPwd(sysUser.getPwd());
    service.changePassword(u);
    return Result.ok(sysUser.getPwd());
  }

  @ApiOperation(value = "sys_user-用户中心-修改密码", notes = "sys_user-修改密码")
  @PostMapping(value = "/modifyPwd")
  public Result<?> modifyPwd(@RequestBody PwdModify pwd) {
    Object token = service.modifyPwd(pwd);
    return Result.ok(token);
  }

  @DictTranslate
  @ApiOperation(value = "sys_user-查询角色用户", notes = "sys_user-查询角色用户")
  @GetMapping(value = "/listByRoleId")
  public Result<?> listByRoleId(@RequestParam(name = "roleId") String roleId) {
    List<SysUser> users = service.listByRoleId(roleId);
    return Result.ok(users);
  }

  @ApiOperation(value = "sys_user-查询用户角色", notes = "sys_user-查询用户角色")
  @GetMapping(value = "/listRoleIds")
  public Result<?> listRoles(@RequestParam(name = "id", required = false) String id) {
    List<String> roleIds = service.listRoleIds(id);
    return Result.ok(roleIds);
  }

  /**
   * 分页列表查询
   *
   * @param sysUser
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "sys_user-分页列表查询", notes = "sys_user-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysUser sysUser,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysUser> queryWrapper = QueryWrapperBuilder.build(sysUser, req.getParameterMap());
    Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
    IPage<SysUser> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysUser
   * @return
   */
  @AutoLog(value = "sys_user-添加")
  @ApiOperation(value = "sys_user-添加", notes = "sys_user-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysUser sysUser) {
    service.add(sysUser);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysUser
   * @return
   */
  @AutoLog(value = "sys_user-编辑")
  @ApiOperation(value = "sys_user-编辑", notes = "sys_user-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysUser sysUser) {
    service.updateWithRole(sysUser);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @RequiresRoles("admin")
  @AutoLog(value = "sys_user-通过id删除")
  @ApiOperation(value = "sys_user-通过id删除", notes = "sys_user-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.removeWithRole(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sys_user-批量删除")
  @ApiOperation(value = "sys_user-批量删除", notes = "sys_user-批量删除")
  @DeleteMapping(value = "/deleteBatch")
  public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    service.removeByIds(Arrays.asList(ids.split(",")));
    return Result.ok("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sys_user-通过id查询")
  @ApiOperation(value = "sys_user-通过id查询", notes = "sys_user-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysUser sysUser = service.getById(id);
    if (sysUser == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysUser);
  }
  /**
   * 导出excel
   *
   * @param request
   * @param sysUser
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysUser sysUser) {
    return super.exportXls(request, sysUser, SysUser.class, "测试sheet");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @PostMapping(value = "/importExcel")
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, SysUser.class);
  }
}
