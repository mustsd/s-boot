package com.github.mustsd.modules.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.article.service.ISysArticleService;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.article.entity.SysArticle;
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
 * @date 2022-03-22 17:37:01
 */
@Slf4j
@RestController
@Api(tags = "Article")
@RequestMapping("/sys/article")
public class SysArticleController extends BaseController<SysArticle, ISysArticleService> {

  @ApiOperation(value = "Article-article tree query", notes = "Article-article tree query")
  @PostMapping(value = "/tree")
  public Result<?> loadArticleTree(@RequestBody SysArticle sysArticle) {
    return Result.ok(service.loadArticleTree(sysArticle.getPersonal()));
  }

  /**
   * Query list.
   *
   * @param sysArticle
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysArticle-Page list query")
  @ApiOperation(value = "sysArticle-Page list query", notes = "sysArticle-Page list query")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysArticle sysArticle,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysArticle> queryWrapper = QueryWrapperBuilder.build(sysArticle, req);
    Page<SysArticle> page = new Page<SysArticle>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * Add.
   *
   * @param sysArticle
   * @return
   */
  @AutoLog(value = "sysArticle-Add")
  @ApiOperation(value = "sysArticle-Add", notes = "sysArticle-Add")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysArticle sysArticle) {
    service.save(sysArticle);
    return Result.ok("Added successfully!");
  }

  /**
   * Edit.
   *
   * @param sysArticle
   * @return
   */
  @AutoLog(value = "sysArticle-Edit")
  @ApiOperation(value = "sysArticle-Edit", notes = "sysArticle-Edit")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysArticle sysArticle) {
    service.updateById(sysArticle);
    return Result.ok("Edited successfully!");
  }

  /**
   * Delete by id.
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysArticle-Delete by id")
  @ApiOperation(value = "sysArticle-Delete by id", notes = "sysArticle-Delete by id")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.removeById(id);
    return Result.ok("Delete successfully!");
  }

  /**
   * Delete in batch.
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sysArticle-Delete in batch")
  @ApiOperation(value = "sysArticle-Delete in batch", notes = "sysArticle-Delete in batch")
  @DeleteMapping(value = "/deleteBatch")
  public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
    service.removeByIds(Arrays.asList(ids.split(",")));
    return Result.ok("批量删除成功!");
  }

  /**
   * Query by id.
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysArticle-Query by id")
  @ApiOperation(value = "sysArticle-Query by id", notes = "sysArticle-Query by id")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysArticle sysArticle = service.getById(id);
    if (sysArticle == null) {
      return Result.error("Data not found.");
    }
    return Result.ok(sysArticle);
  }

  /**
   * Export excel
   *
   * @param request
   * @param sysArticle
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysArticle sysArticle) {
    return super.exportXls(request, sysArticle, SysArticle.class, "Article");
  }

  /**
   * Import excel.
   *
   * @param request
   * @param response
   * @return
   */
  @PostMapping(value = "/importExcel")
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, SysArticle.class);
  }
}
