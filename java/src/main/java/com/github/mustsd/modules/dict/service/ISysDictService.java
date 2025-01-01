package com.github.mustsd.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.dict.entity.SysDict;
import com.github.mustsd.modules.dict.vo.DictItem;

import java.util.List;

public interface ISysDictService extends IService<SysDict> {

  String queryDictTextByKey(String code, String key);

  String queryTableDictTextByKey(String table, String text, String code, String key);

  void deleteWithItem(String id);

  List<DictItem> getDictItem(String dictCode);

  List<DictItem> getTableDictItem(String orgId, String dictCode);
}
