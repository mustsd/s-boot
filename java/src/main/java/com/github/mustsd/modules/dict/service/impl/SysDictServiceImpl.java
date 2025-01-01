package com.github.mustsd.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.constant.CacheConstant;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.modules.dict.service.ISysDictItemService;
import com.github.mustsd.modules.dict.entity.SysDict;
import com.github.mustsd.modules.dict.entity.SysDictItem;
import com.github.mustsd.modules.dict.mapper.SysDictMapper;
import com.github.mustsd.modules.dict.service.ISysDictService;
import com.github.mustsd.modules.dict.vo.DictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict>
    implements ISysDictService {

  @Autowired
  ISysDictItemService dictItemService;

  @Override
  @Cacheable(value = CacheConstant.SYS_DICT_KEY_CACHE, key = "#code+':'+#key")
  public String queryDictTextByKey(String code, String key) {
    return baseMapper.queryDictTextByKey(code, key);
  }

  @Override
  public String queryTableDictTextByKey(String table, String text, String code, String key) {
    return baseMapper.queryTableDictTextByKey(table, text, code, key);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteWithItem(String id) {
    this.removeById(id);
    dictItemService.remove(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId, id));
  }

  @Override
  @Cacheable(value = CacheConstant.SYS_DICT_CACHE, key = "#dictCode")
  public List<DictItem> getDictItem(String dictCode) {
    if (dictCode.contains(CommonConstant.E_COMMA)) {
      String[] params = dictCode.split(CommonConstant.E_COMMA);
      for (String param : params) {
        if (SqlInjectionUtils.check(param)) {
          throw new BusinessException("非法sql参数");
        }
      }
      if (params.length == 3) {
        return baseMapper.getTableItem(params[0], params[1], params[2]);
      } else {
        throw new BusinessException("字典参数错误");
      }
    }
    return baseMapper.getDictItem(dictCode);
  }

  @Override
  //  @Cacheable(value = CacheConstant.SYS_DICT_CACHE, key = "#dictCode+ ':'+#orgId")
  public List<DictItem> getTableDictItem(String orgId, String dictCode) {
    if (dictCode.contains(CommonConstant.E_COMMA)) {
      String[] params = dictCode.split(CommonConstant.E_COMMA);
      for (String param : params) {
        if (SqlInjectionUtils.check(param)) {
          throw new BusinessException("非法sql参数");
        }
      }
      if (params.length == 3) {
        return baseMapper.getOrgTableItem(orgId, params[0], params[1], params[2]);
      } else {
        throw new BusinessException("字典参数错误");
      }
    } else {
      throw new BusinessException("字典参数错误");
    }
  }
}
