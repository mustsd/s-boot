package com.github.mustsd.modules.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.log.entity.SysLog;
import com.github.mustsd.modules.log.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Api(tags = "操作日志")
@RestController
@RequestMapping("/sys/operationLog")
@Slf4j
public class SysLogController extends BaseController<SysLog, ISysLogService> {

  /**
   * 分页列表查询
   *
   * @param sysLog
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "操作日志-分页列表查询", notes = "操作日志-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysLog sysLog,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysLog> queryWrapper = QueryWrapperBuilder.build(sysLog, req.getParameterMap());
    Page<SysLog> page = new Page<SysLog>(pageNo, pageSize);
    IPage<SysLog> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysLog
   * @return
   */
  @AutoLog(value = "操作日志-添加")
  @ApiOperation(value = "操作日志-添加", notes = "操作日志-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysLog sysLog) {
    service.save(sysLog);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysLog
   * @return
   */
  @AutoLog(value = "操作日志-编辑")
  @ApiOperation(value = "操作日志-编辑", notes = "操作日志-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysLog sysLog) {
    service.updateById(sysLog);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "操作日志-通过id删除")
  @ApiOperation(value = "操作日志-通过id删除", notes = "操作日志-通过id删除")
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
  @AutoLog(value = "操作日志-批量删除")
  @ApiOperation(value = "操作日志-批量删除", notes = "操作日志-批量删除")
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
  @AutoLog(value = "操作日志-通过id查询")
  @ApiOperation(value = "操作日志-通过id查询", notes = "操作日志-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysLog sysLog = service.getById(id);
    if (sysLog == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysLog);
  }
}
