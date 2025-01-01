package com.github.mustsd.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.common.tree.TreeNode;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 *
 * @author yangz
 * @date 2022-03-03 7:56
 */
@Api(tags = "m_permission")
@RestController
@RequestMapping("/sys/permission")
@Slf4j
public class SysPermissionController extends BaseController<SysPermission, ISysPermissionService> {

  @GetMapping(value = "/listRoleByPermission")
  public Result<?> listRoleByPermission(@RequestParam String permissionId) {
    List<SysRole> roles = service.listRoleByPermission(permissionId);
    return Result.ok(roles);
  }

  @GetMapping(value = "/listUserPermissionTree")
  public Result<?> listUserPermissionTree(@RequestParam String userId) {
    Map<String, Object> tree = service.listUserPermissionTree(userId);
    return Result.ok(tree);
  }

  @GetMapping(value = "/listRolePermissionTree")
  public Result<?> listRolePermissionTree(@RequestParam String roleId) {
    Map<String, Object> tree = service.listRolePermissionTree(roleId);
    return Result.ok(tree);
  }

  @ApiOperation(value = "m_permission-加载菜单下拉选", notes = "m_permission-加载菜单下拉选")
  @GetMapping(value = "/listOptions")
  public Result<?> listOptions() {
    List<TreeNode> tree = service.listOptions();
    return Result.ok(tree);
  }

  @ApiOperation(value = "m_permission-查询菜单树", notes = "m_permission-查询菜单树")
  @GetMapping(value = "/listTree")
  public Result<?> listTree() {
    List<SysPermission> tree = service.listTree();
    return Result.ok(tree);
  }

  /**
   * 分页列表查询
   *
   * @param sysPermission
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "m_permission-分页列表查询", notes = "m_permission-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysPermission sysPermission,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysPermission> queryWrapper = QueryWrapperBuilder.build(sysPermission, req);
    Page<SysPermission> page = new Page<SysPermission>(pageNo, pageSize);
    IPage<SysPermission> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysPermission
   * @return
   */
  @AutoLog(value = "m_permission-添加")
  @ApiOperation(value = "m_permission-添加", notes = "m_permission-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysPermission sysPermission) {
    service.save(sysPermission);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysPermission
   * @return
   */
  @AutoLog(value = "m_permission-编辑")
  @ApiOperation(value = "m_permission-编辑", notes = "m_permission-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysPermission sysPermission) {
    service.updateById(sysPermission);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @RequiresRoles("admin")
  @AutoLog(value = "m_permission-通过id删除")
  @ApiOperation(value = "m_permission-通过id删除", notes = "m_permission-通过id删除")
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
  @AutoLog(value = "m_permission-批量删除")
  @ApiOperation(value = "m_permission-批量删除", notes = "m_permission-批量删除")
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
  @AutoLog(value = "m_permission-通过id查询")
  @ApiOperation(value = "m_permission-通过id查询", notes = "m_permission-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysPermission sysPermission = service.getById(id);
    if (sysPermission == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysPermission);
  }
}
