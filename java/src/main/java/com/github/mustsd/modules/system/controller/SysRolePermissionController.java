package com.github.mustsd.modules.system.controller;

import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.modules.system.entity.SysRolePermission;
import com.github.mustsd.modules.system.service.ISysRolePermissionService;
import com.github.mustsd.modules.system.vo.GrantPerm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "m_role_permission")
@RestController
@RequestMapping("/sys/rolePermission")
@Slf4j
public class SysRolePermissionController
    extends BaseController<SysRolePermission, ISysRolePermissionService> {

  /**
   * 赋权
   *
   * @param grantPerm
   * @return
   */
  @AutoLog(value = "m_role_permission-添加")
  @ApiOperation(value = "m_role_permission-添加", notes = "m_role_permission-添加")
  @PostMapping(value = "/grantPerm")
  public Result<?> grantPerm(@RequestBody GrantPerm grantPerm) {
    service.grantPerm(grantPerm);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysRolePermission
   * @return
   */
  @AutoLog(value = "m_role_permission-编辑")
  @ApiOperation(value = "m_role_permission-编辑", notes = "m_role_permission-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysRolePermission sysRolePermission) {
    service.updateById(sysRolePermission);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "m_role_permission-通过id删除")
  @ApiOperation(value = "m_role_permission-通过id删除", notes = "m_role_permission-通过id删除")
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
  @AutoLog(value = "m_role_permission-批量删除")
  @ApiOperation(value = "m_role_permission-批量删除", notes = "m_role_permission-批量删除")
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
  @AutoLog(value = "m_role_permission-通过id查询")
  @ApiOperation(value = "m_role_permission-通过id查询", notes = "m_role_permission-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysRolePermission sysRolePermission = service.getById(id);
    if (sysRolePermission == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysRolePermission);
  }
}
