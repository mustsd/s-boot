package com.github.mustsd.common.xls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangz
 * @date 2022-03-21 18:58
 */
@Slf4j
public class ExcelReadListener<T, S extends IService<T>> implements ReadListener<T> {

  /** daoService */
  private S service;
  /** dataList */
  private List<T> list;

  public ExcelReadListener(S service) {
    this.service = service;
    this.list = new ArrayList<>();
  }

  @Override
  public void invoke(T data, AnalysisContext context) {
    list.add(data);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    service.saveOrUpdateBatch(this.list);
  }
}
