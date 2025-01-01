package com.github.mustsd.modules.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.notice.service.ISysNoticeService;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.notice.entity.SysNotice;
import com.github.mustsd.modules.shiro.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author manage
 * @date 2022-05-24 10:26:10
 */
@Slf4j
@RestController
@Api(tags = "系统通知")
@RequestMapping("/notice/sysNotice")
public class SysNoticeController extends BaseController<SysNotice, ISysNoticeService> {

  @AutoLog(value = "sysNotice-查询并已读消息")
  @ApiOperation(value = "sysNotice-查询并已读消息", notes = "sysNotice-查询并已读消息")
  @PostMapping(value = "/readNotice")
  public Result<?> readNotice(@RequestBody SysNotice sysNotice) {
    SysNotice notice = service.readNotice(sysNotice);
    return Result.ok(notice);
  }

  @ApiOperation(value = "sysNotice-查询我的通知", notes = "sysNotice-查询我的通知")
  @GetMapping(value = "/pageMyNotice")
  public Result<?> pageMyNotice(
      SysNotice sysNotice,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    // 查询当前用户 已发布 通知
    sysNotice.setUserId(CurrentUser.getUserId());
    sysNotice.setStatus(2);
    QueryWrapper<SysNotice> queryWrapper = QueryWrapperBuilder.build(sysNotice, req);
    Page<SysNotice> page = new Page<SysNotice>(pageNo, pageSize);
    return Result.ok(service.pageMyNotice(page, queryWrapper));
  }

  @AutoLog(value = "sysNotice-撤销")
  @ApiOperation(value = "sysNotice-撤销", notes = "sysNotice-撤销")
  @GetMapping(value = "/cancel")
  public Result<?> cancel(@RequestParam(name = "id") String id) {
    service.cancel(id);
    return Result.ok();
  }

  @AutoLog(value = "sysNotice-发布")
  @ApiOperation(value = "sysNotice-发布", notes = "sysNotice-发布")
  @GetMapping(value = "/publish")
  public Result<?> publish(@RequestParam(name = "id") String id) {
    service.publish(id);
    return Result.ok();
  }

  /**
   * 分页列表查询
   *
   * @param sysNotice
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysNotice-分页列表查询")
  @ApiOperation(value = "sysNotice-分页列表查询", notes = "sysNotice-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysNotice sysNotice,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysNotice> queryWrapper = QueryWrapperBuilder.build(sysNotice, req);
    Page<SysNotice> page = new Page<SysNotice>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysNotice
   * @return
   */
  @AutoLog(value = "sysNotice-添加")
  @ApiOperation(value = "sysNotice-添加", notes = "sysNotice-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysNotice sysNotice) {
    service.add(sysNotice);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysNotice
   * @return
   */
  @AutoLog(value = "sysNotice-编辑")
  @ApiOperation(value = "sysNotice-编辑", notes = "sysNotice-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysNotice sysNotice) {
    service.updateById(sysNotice);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysNotice-通过id删除")
  @ApiOperation(value = "sysNotice-通过id删除", notes = "sysNotice-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.delete(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sysNotice-批量删除")
  @ApiOperation(value = "sysNotice-批量删除", notes = "sysNotice-批量删除")
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
  @AutoLog(value = "sysNotice-通过id查询")
  @ApiOperation(value = "sysNotice-通过id查询", notes = "sysNotice-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysNotice sysNotice = service.getById(id);
    if (sysNotice == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysNotice);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysNotice
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysNotice sysNotice) {
    return super.exportXls(request, sysNotice, SysNotice.class, "系统通知");
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
    return super.importExcel(request, response, SysNotice.class);
  }
}
