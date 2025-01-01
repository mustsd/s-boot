package com.github.mustsd.modules.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.quartz.entity.QuartzJob;
import com.github.mustsd.modules.quartz.service.IQuartzJobService;
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
 * @date 2022-03-25 08:52:07
 */
@Slf4j
@RestController
@Api(tags = "定时任务")
@RequestMapping("/quartz/sysQuartzJob")
public class QuartzJobController extends BaseController<QuartzJob, IQuartzJobService> {

  @ApiOperation(value = "sysQuartzJob-启动任务", notes = "sysQuartzJob-启动任务")
  @GetMapping(value = "/startJob")
  public Result<?> startJob(@RequestParam String id) {
    service.startJob(id);
    return Result.ok();
  }

  @ApiOperation(value = "sysQuartzJob-暂停任务", notes = "sysQuartzJob-暂停任务")
  @GetMapping(value = "/pauseJob")
  public Result<?> pauseJob(@RequestParam String id) {
    service.pauseJob(id);
    return Result.ok();
  }

  @ApiOperation(value = "sysQuartzJob-校验定时任务合法性", notes = "sysQuartzJob-校验定时任务合法性")
  @GetMapping(value = "/verifyJobClazz")
  public Result<?> verifyJobClazz(@RequestParam String clazz) {
    service.verifyJobClazz(clazz);
    return Result.ok();
  }

  /**
   * 分页列表查询
   *
   * @param sysQuartzJob
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysQuartzJob-分页列表查询")
  @ApiOperation(value = "sysQuartzJob-分页列表查询", notes = "sysQuartzJob-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      QuartzJob sysQuartzJob,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<QuartzJob> queryWrapper = QueryWrapperBuilder.build(sysQuartzJob, req);
    Page<QuartzJob> page = new Page<QuartzJob>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysQuartzJob
   * @return
   */
  @AutoLog(value = "sysQuartzJob-添加")
  @ApiOperation(value = "sysQuartzJob-添加", notes = "sysQuartzJob-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody QuartzJob sysQuartzJob) {
    service.addJob(sysQuartzJob);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysQuartzJob
   * @return
   */
  @AutoLog(value = "sysQuartzJob-编辑")
  @ApiOperation(value = "sysQuartzJob-编辑", notes = "sysQuartzJob-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody QuartzJob sysQuartzJob) {
    service.editJob(sysQuartzJob);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysQuartzJob-通过id删除")
  @ApiOperation(value = "sysQuartzJob-通过id删除", notes = "sysQuartzJob-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.removeJob(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sysQuartzJob-批量删除")
  @ApiOperation(value = "sysQuartzJob-批量删除", notes = "sysQuartzJob-批量删除")
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
  @AutoLog(value = "sysQuartzJob-通过id查询")
  @ApiOperation(value = "sysQuartzJob-通过id查询", notes = "sysQuartzJob-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    QuartzJob sysQuartzJob = service.getById(id);
    if (sysQuartzJob == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysQuartzJob);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysQuartzJob
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, QuartzJob sysQuartzJob) {
    return super.exportXls(request, sysQuartzJob, QuartzJob.class, "定时任务");
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
    return super.importExcel(request, response, QuartzJob.class);
  }
}
