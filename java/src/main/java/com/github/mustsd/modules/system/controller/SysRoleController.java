package com.github.mustsd.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Api(tags = "m_role")
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController extends BaseController<SysRole, ISysRoleService> {

  /**
   * 分页列表查询
   *
   * @param sysRole
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "m_role-分页列表查询", notes = "m_role-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysRole sysRole,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysRole> queryWrapper = QueryWrapperBuilder.build(sysRole, req);
    Page<SysRole> page = new Page<SysRole>(pageNo, pageSize);
    IPage<SysRole> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysRole
   * @return
   */
  @AutoLog(value = "m_role-添加")
  @ApiOperation(value = "m_role-添加", notes = "m_role-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysRole sysRole) {
    service.save(sysRole);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysRole
   * @return
   */
  @AutoLog(value = "m_role-编辑")
  @ApiOperation(value = "m_role-编辑", notes = "m_role-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysRole sysRole) {
    service.updateById(sysRole);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "m_role-通过id删除")
  @ApiOperation(value = "m_role-通过id删除", notes = "m_role-通过id删除")
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
  @AutoLog(value = "m_role-批量删除")
  @ApiOperation(value = "m_role-批量删除", notes = "m_role-批量删除")
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
  @AutoLog(value = "m_role-通过id查询")
  @ApiOperation(value = "m_role-通过id查询", notes = "m_role-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysRole sysRole = service.getById(id);
    if (sysRole == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysRole);
  }
}
