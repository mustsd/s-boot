package com.github.mustsd.modules.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.constant.CacheConstant;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.dict.entity.SysDictItem;
import com.github.mustsd.modules.dict.service.ISysDictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Api(tags = "字典项")
@RestController
@RequestMapping("/sys/dictItem")
@Slf4j
public class SysDictItemController extends BaseController<SysDictItem, ISysDictItemService> {

  /**
   * 分页列表查询
   *
   * @param sysDictItem
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "字典项-分页列表查询", notes = "字典项-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysDictItem sysDictItem,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysDictItem> queryWrapper =
        QueryWrapperBuilder.build(sysDictItem, req.getParameterMap());
    Page<SysDictItem> page = new Page<SysDictItem>(pageNo, pageSize);
    IPage<SysDictItem> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysDictItem
   * @return
   */
  @AutoLog(value = "字典项-添加")
  @ApiOperation(value = "字典项-添加", notes = "字典项-添加")
  @PostMapping(value = "/add")
  @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, key = "#sysDictItem.dictCode")
  public Result<?> add(@RequestBody SysDictItem sysDictItem) {
    service.save(sysDictItem);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysDictItem
   * @return
   */
  @AutoLog(value = "字典项-编辑")
  @ApiOperation(value = "字典项-编辑", notes = "字典项-编辑")
  @PutMapping(value = "/edit")
  @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, key = "#sysDictItem.dictCode")
  public Result<?> edit(@RequestBody SysDictItem sysDictItem) {
    service.updateById(sysDictItem);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "字典项-通过id删除")
  @ApiOperation(value = "字典项-通过id删除", notes = "字典项-通过id删除")
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
  @AutoLog(value = "字典项-批量删除")
  @ApiOperation(value = "字典项-批量删除", notes = "字典项-批量删除")
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
  @AutoLog(value = "字典项-通过id查询")
  @ApiOperation(value = "字典项-通过id查询", notes = "字典项-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysDictItem sysDictItem = service.getById(id);
    if (sysDictItem == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysDictItem);
  }
}
