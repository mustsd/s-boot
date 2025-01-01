package com.github.mustsd.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.system.entity.SysUserOrg;
import com.github.mustsd.modules.system.service.ISysUserOrgService;
import com.github.mustsd.modules.system.vo.SysUserOrgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author manage
 * @date 2022-06-07 15:03:51
 */
@Slf4j
@RestController
@Api(tags = "用户机构表")
@RequestMapping("/system/sysUserOrg")
public class SysUserOrgController
    extends BaseController<SysUserOrg, ISysUserOrgService> {

  @AutoLog(value = "用户机构关联表-用户机构调整")
  @ApiOperation(value = "用户机构关联表-用户机构调整", notes = "用户机构关联表-用户机构调整")
  @PostMapping(value = "/editUserOrg")
  public Result<?> editUserOrg(@RequestBody SysUserOrgVO userOrgVO) {
    service.editUserOrg(userOrgVO);
    return Result.ok("添加成功！");
  }

  /**
   * 分页列表查询
   *
   * @param sysUserOrg
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysUserOrg-分页列表查询")
  @ApiOperation(value = "sysUserOrg-分页列表查询", notes = "sysUserOrg-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysUserOrg sysUserOrg,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysUserOrg> queryWrapper = QueryWrapperBuilder.build(sysUserOrg, req);
    Page<SysUserOrg> page = new Page<SysUserOrg>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysUserOrg
   * @return
   */
  @AutoLog(value = "sysUserOrg-添加")
  @ApiOperation(value = "sysUserOrg-添加", notes = "sysUserOrg-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysUserOrg sysUserOrg) {
    service.save(sysUserOrg);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysUserOrg
   * @return
   */
  @AutoLog(value = "sysUserOrg-编辑")
  @ApiOperation(value = "sysUserOrg-编辑", notes = "sysUserOrg-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysUserOrg sysUserOrg) {
    service.updateById(sysUserOrg);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysUserOrg-通过id删除")
  @ApiOperation(value = "sysUserOrg-通过id删除", notes = "sysUserOrg-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.removeById(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sysUserOrg-批量删除")
  @ApiOperation(value = "sysUserOrg-批量删除", notes = "sysUserOrg-批量删除")
  @DeleteMapping(value = "/deleteBatch")
  public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
    service.removeByIds(Arrays.asList(ids.split(",")));
    return Result.ok("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysUserOrg-通过id查询")
  @ApiOperation(value = "sysUserOrg-通过id查询", notes = "sysUserOrg-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysUserOrg sysUserOrg = service.getById(id);
    if (sysUserOrg == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysUserOrg);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysUserOrg
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysUserOrg sysUserOrg) {
    return super.exportXls(request, sysUserOrg, SysUserOrg.class, "用户机构表");
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
    return super.importExcel(request, response, SysUserOrg.class);
  }
}
