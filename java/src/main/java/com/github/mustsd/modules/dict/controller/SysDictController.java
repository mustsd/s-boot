package com.github.mustsd.modules.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.shiro.CurrentUser;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.constant.CacheConstant;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.dict.entity.SysDict;
import com.github.mustsd.modules.dict.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Api(tags = "字典")
@RestController
@RequestMapping("/sys/dict")
@Slf4j
public class SysDictController extends BaseController<SysDict, ISysDictService> {

  @GetMapping(value = "/cacheRefresh")
  @CacheEvict(
      allEntries = true,
      value = {CacheConstant.SYS_DICT_CACHE, CacheConstant.SYS_DICT_KEY_CACHE})
  public Result<?> refreshCache() {
    return Result.ok();
  }

  @ApiOperation(value = "字典-查询dictItem", notes = "字典-查询dictItem")
  @GetMapping(value = "/getDictItem")
  public Result<?> getDictItem(
      @RequestParam("dictCode") String dictCode, @RequestParam("orgFilter") Boolean orgFilter) {
    if (orgFilter) {
      return Result.ok(service.getTableDictItem(CurrentUser.getOrgId(), dictCode));
    } else {
      return Result.ok(service.getDictItem(dictCode));
    }
  }

  /**
   * 分页列表查询
   *
   * @param sysDict
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @ApiOperation(value = "字典-分页列表查询", notes = "字典-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysDict sysDict,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysDict> queryWrapper = QueryWrapperBuilder.build(sysDict, req.getParameterMap());
    Page<SysDict> page = new Page<SysDict>(pageNo, pageSize);
    IPage<SysDict> pageList = service.page(page, queryWrapper);
    return Result.ok(pageList);
  }

  /**
   * 添加
   *
   * @param sysDict
   * @return
   */
  @AutoLog(value = "字典-添加")
  @ApiOperation(value = "字典-添加", notes = "字典-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysDict sysDict) {
    service.save(sysDict);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysDict
   * @return
   */
  @AutoLog(value = "字典-编辑")
  @ApiOperation(value = "字典-编辑", notes = "字典-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysDict sysDict) {
    service.updateById(sysDict);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "字典-通过id删除")
  @ApiOperation(value = "字典-通过id删除", notes = "字典-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
    service.deleteWithItem(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "字典-批量删除")
  @ApiOperation(value = "字典-批量删除", notes = "字典-批量删除")
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
  @AutoLog(value = "字典-通过id查询")
  @ApiOperation(value = "字典-通过id查询", notes = "字典-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
    SysDict sysDict = service.getById(id);
    if (sysDict == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysDict);
  }
}
