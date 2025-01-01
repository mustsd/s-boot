package com.github.mustsd.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mustsd.modules.dict.entity.SysDict;
import com.github.mustsd.modules.dict.vo.DictItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysDictMapper extends BaseMapper<SysDict> {

  @Select(
      "select sdi.item_value text from sys_dict_item sdi "
          + "inner join sys_dict sd on sd.id=sdi.dict_id "
          + "where sd.code=#{code} and sdi.item_key=#{key} ")
  String queryDictTextByKey(@Param("code") String code, @Param("key") String key);

  @Select("select ${text} as text from ${table} where ${code}= #{key}")
  String queryTableDictTextByKey(
      @Param("table") String table,
      @Param("text") String text,
      @Param("code") String code,
      @Param("key") String key);

  @Select(
      "select item_key value, item_value title from sys_dict_item sdi "
          + "inner join sys_dict sd on sd.id=sdi.dict_id "
          + "where sd.code=#{dictCode}")
  List<DictItem> getDictItem(@Param("dictCode") String dictCode);

  @Select("select ${title} as title,${value} as value from ${table} ")
  List<DictItem> getTableItem(
      @Param("table") String param, @Param("title") String param1, @Param("value") String param2);

  @Select("select ${title} as title,${value} as value from ${table} where org_id= #{orgId} ")
  List<DictItem> getOrgTableItem(
      @Param("orgId") String orgId,
      @Param("table") String param,
      @Param("title") String param1,
      @Param("value") String param2);
}
