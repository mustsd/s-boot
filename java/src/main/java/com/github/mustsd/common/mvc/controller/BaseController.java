package com.github.mustsd.common.mvc.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.common.mvc.vo.Result;
import com.github.mustsd.common.query.QueryWrapperBuilder;
import com.github.mustsd.common.xls.EntityExcelView;
import com.github.mustsd.common.xls.ExcelReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author mustsd
 * @date 2024-12-31
 */
public class BaseController<T, S extends IService<T>> {
  @Autowired protected S service;

  protected ModelAndView exportXls(
      HttpServletRequest request, T object, Class<T> clazz, String title) {
    QueryWrapper<T> queryWrapper = QueryWrapperBuilder.build(object, request.getParameterMap());
    String selections = request.getParameter("selections");
    if (StringUtils.hasText(selections)) {
      String selectionKey = request.getParameter("selectionKey");
      String column = StringUtils.hasText(selectionKey) ? selectionKey : CommonConstant.PRIMARY_KEY;
      queryWrapper.in(column, selections.split(CommonConstant.E_COMMA));
    }
    List<T> data = service.list(queryWrapper);
    return new ModelAndView(new EntityExcelView<T>(title, clazz, data));
  }

  protected Result<?> importExcel(
      HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile file = entity.getValue();
      try {
        EasyExcel.read(file.getInputStream(), clazz, new ExcelReadListener<T, S>(service))
            .sheet()
            .headRowNumber(1)
            .doRead();
      } catch (IOException e) {
        throw new BusinessException("Importing failed.");
      }
    }
    return Result.ok();
  }
}
