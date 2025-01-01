package com.github.mustsd.modules.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.file.entity.SysFile;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.aop.annotation.DictTranslate;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.mvc.controller.BaseController;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.modules.file.service.ISysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author mustsd
 * @date 2022-03-22 15:45:09
 */
@Slf4j
@RestController
@Api(tags = "OSS文件")
@RequestMapping("/sys/file")
public class SysFileController extends BaseController<SysFile, ISysFileService> {

  @ApiOperation(value = "上传文件-图片", notes = "上传文件-图片")
  @PostMapping(value = "/uploadImage")
  public Result<?> uploadImage(MultipartHttpServletRequest request) {
    Object filePath = service.uploadFile(request, CommonConstant.IMAGE_BUCKET);
    return Result.ok(filePath);
  }

  @AutoLog(value = "上传文件-附件")
  @ApiOperation(value = "上传文件-附件", notes = "上传文件-附件")
  @PostMapping(value = "/uploadFile")
  public Result<?> uploadFile(MultipartHttpServletRequest request) {
    Object filePath = service.uploadFile(request, CommonConstant.ATTACHMENT_BUCKET);
    return Result.ok(filePath);
  }

  /**
   * 分页列表查询
   *
   * @param sysFile
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  @DictTranslate
  @AutoLog(value = "sysFile-分页列表查询")
  @ApiOperation(value = "sysFile-分页列表查询", notes = "sysFile-分页列表查询")
  @GetMapping(value = "/list")
  public Result<?> queryPageList(
      SysFile sysFile,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SysFile> queryWrapper = QueryWrapperBuilder.build(sysFile, req);
    Page<SysFile> page = new Page<SysFile>(pageNo, pageSize);
    return Result.ok(service.page(page, queryWrapper));
  }

  /**
   * 添加
   *
   * @param sysFile
   * @return
   */
  @AutoLog(value = "sysFile-添加")
  @ApiOperation(value = "sysFile-添加", notes = "sysFile-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SysFile sysFile) {
    service.save(sysFile);
    return Result.ok("添加成功！");
  }

  /**
   * 编辑
   *
   * @param sysFile
   * @return
   */
  @AutoLog(value = "sysFile-编辑")
  @ApiOperation(value = "sysFile-编辑", notes = "sysFile-编辑")
  @PutMapping(value = "/edit")
  public Result<?> edit(@RequestBody SysFile sysFile) {
    service.updateById(sysFile);
    return Result.ok("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "sysFile-通过id删除")
  @ApiOperation(value = "sysFile-通过id删除", notes = "sysFile-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<?> delete(@RequestParam(name = "id") String id) {
    service.removeFile(id);
    return Result.ok("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "sysFile-批量删除")
  @ApiOperation(value = "sysFile-批量删除", notes = "sysFile-批量删除")
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
  @AutoLog(value = "sysFile-通过id查询")
  @ApiOperation(value = "sysFile-通过id查询", notes = "sysFile-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<?> queryById(@RequestParam(name = "id") String id) {
    SysFile sysFile = service.getById(id);
    if (sysFile == null) {
      return Result.error("未找到对应数据");
    }
    return Result.ok(sysFile);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param sysFile
   */
  @PostMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SysFile sysFile) {
    return super.exportXls(request, sysFile, SysFile.class, "OSS文件");
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
    return super.importExcel(request, response, SysFile.class);
  }
}
