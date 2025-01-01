package com.github.mustsd.modules.system.controller;

import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.modules.system.entity.SysUserRole;
import com.github.mustsd.modules.system.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "m_user_role")
@RestController
@RequestMapping("/sys/userRole")
@Slf4j
public class SysUserRoleController extends BaseController<SysUserRole, ISysUserRoleService> {

  /**
   * 添加
   *
   * @param sysUserRole
   * @return
   */
  @AutoLog(value = "m_user_role-添加")
  @ApiOperation(value = "m_user_role-添加", notes = "m_user_role-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysUserRole sysUserRole) {
    service.save(sysUserRole);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysUserRole
   * @return
   */
  @AutoLog(value = "m_user_role-编辑")
  @ApiOperation(value = "m_user_role-编辑", notes = "m_user_role-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysUserRole sysUserRole) {
    service.updateById(sysUserRole);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "m_user_role-通过id删除")
  @ApiOperation(value = "m_user_role-通过id删除", notes = "m_user_role-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
    service.removeById(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "m_user_role-批量删除")
  @ApiOperation(value = "m_user_role-批量删除", notes = "m_user_role-批量删除")
  @DeleteMapping(value = "/deleteBatch")
  public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    this.service.removeByIds(Arrays.asList(ids.split(",")));
    return Result.ok("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  @AutoLog(value = "m_user_role-通过id查询")
  @ApiOperation(value = "m_user_role-通过id查询", notes = "m_user_role-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysUserRole sysUserRole = service.getById(id);
    if (sysUserRole == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysUserRole);
  }
}
