package com.github.mustsd.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.system.entity.SysOrg;
import com.github.mustsd.modules.system.service.ISysOrgService;
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
 * @date 2022-06-07 15:04:07
 */
@Slf4j
@RestController
@Api(tags = "机构")
@RequestMapping("/system/sysOrg")
public class SysOrgController
    extends BaseController<SysOrg, ISysOrgService> {

  @ApiOperation(value = "sys_org-查询所有机构", notes = "sys_org-查询所有机构")
  @GetMapping(value = "/listTransferByUser")
  public Result<?> listTransferByUser(@RequestParam("userId") String userId) {
    return Result.ok(service.listTransferByUser(userId));
  }

  /**
   * 分页列表查询
   *
   * @param sysOrg
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysOrg-分页列表查询")
  @ApiOperation(value = "sysOrg-分页列表查询", notes = "sysOrg-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysOrg sysOrg,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysOrg> queryWrapper = QueryWrapperBuilder.build(sysOrg, req);
    Page<SysOrg> page = new Page<SysOrg>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysOrg
   * @return
   */
  @AutoLog(value = "sysOrg-添加")
  @ApiOperation(value = "sysOrg-添加", notes = "sysOrg-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysOrg sysOrg) {
    service.save(sysOrg);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysOrg
   * @return
   */
  @AutoLog(value = "sysOrg-编辑")
  @ApiOperation(value = "sysOrg-编辑", notes = "sysOrg-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysOrg sysOrg) {
    service.updateById(sysOrg);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysOrg-通过id删除")
  @ApiOperation(value = "sysOrg-通过id删除", notes = "sysOrg-通过id删除")
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
  @AutoLog(value = "sysOrg-批量删除")
  @ApiOperation(value = "sysOrg-批量删除", notes = "sysOrg-批量删除")
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
  @AutoLog(value = "sysOrg-通过id查询")
  @ApiOperation(value = "sysOrg-通过id查询", notes = "sysOrg-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysOrg sysOrg = service.getById(id);
    if (sysOrg == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysOrg);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysOrg
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysOrg sysOrg) {
    return super.exportXls(request, sysOrg, SysOrg.class, "机构");
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
    return super.importExcel(request, response, SysOrg.class);
  }
}
