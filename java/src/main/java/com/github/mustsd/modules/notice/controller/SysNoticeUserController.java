package com.github.mustsd.modules.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.notice.service.ISysNoticeUserService;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.notice.entity.SysNoticeUser;
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
 * @date 2022-05-24 09:08:35
 */
@Slf4j
@RestController
@Api(tags = "通知用户表")
@RequestMapping("/notice/sysNoticeUser")
public class SysNoticeUserController extends BaseController<SysNoticeUser, ISysNoticeUserService> {

  @ApiOperation(value = "sysNoticeUser-消息标记已读", notes = "sysNoticeUser-消息标记已读")
  @PostMapping(value = "/markRead")
  public Result<?> markRead(@RequestParam("id") String id) {
    service.markRead(id);
    return Result.ok();
  }

  @ApiOperation(value = "sysNoticeUser-未读消息数", notes = "sysNoticeUser-未读消息数")
  @PostMapping(value = "/count")
  public Result<?> countUnreadNotice() {
    String userId = CurrentUser.getUserId();
    long count = service.countUnreadNotice(userId);
    return Result.ok(count);
  }

  /**
   * 分页列表查询
   *
   * @param sysNoticeUser
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysNoticeUser-分页列表查询")
  @ApiOperation(value = "sysNoticeUser-分页列表查询", notes = "sysNoticeUser-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysNoticeUser sysNoticeUser,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysNoticeUser> queryWrapper = QueryWrapperBuilder.build(sysNoticeUser, req);
    Page<SysNoticeUser> page = new Page<SysNoticeUser>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysNoticeUser
   * @return
   */
  @AutoLog(value = "sysNoticeUser-添加")
  @ApiOperation(value = "sysNoticeUser-添加", notes = "sysNoticeUser-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysNoticeUser sysNoticeUser) {
    service.save(sysNoticeUser);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysNoticeUser
   * @return
   */
  @AutoLog(value = "sysNoticeUser-编辑")
  @ApiOperation(value = "sysNoticeUser-编辑", notes = "sysNoticeUser-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysNoticeUser sysNoticeUser) {
    service.updateById(sysNoticeUser);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysNoticeUser-通过id删除")
  @ApiOperation(value = "sysNoticeUser-通过id删除", notes = "sysNoticeUser-通过id删除")
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
  @AutoLog(value = "sysNoticeUser-批量删除")
  @ApiOperation(value = "sysNoticeUser-批量删除", notes = "sysNoticeUser-批量删除")
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
  @AutoLog(value = "sysNoticeUser-通过id查询")
  @ApiOperation(value = "sysNoticeUser-通过id查询", notes = "sysNoticeUser-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysNoticeUser sysNoticeUser = service.getById(id);
    if (sysNoticeUser == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysNoticeUser);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysNoticeUser
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysNoticeUser sysNoticeUser) {
    return super.exportXls(request, sysNoticeUser, SysNoticeUser.class, "通知用户表");
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
    return super.importExcel(request, response, SysNoticeUser.class);
  }
}
