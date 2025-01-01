package com.github.mustsd.common.xls;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author yangz
 * @date 2022-06-20 11:50
 */
public class StringStringConverter extends BaseDictConverter implements Converter<String> {
  @Override
  public Class<?> supportJavaTypeKey() {
    return String.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.STRING;
  }

  @Override
  public String convertToJavaData(
      ReadCellData<?> cellData,
      ExcelContentProperty contentProperty,
      GlobalConfiguration globalConfiguration) {
    return cellData.getStringValue();
  }

  @Override
  public WriteCellData<?> convertToExcelData(
      String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
    return new WriteCellData<>(getValue(value, contentProperty.getField()));
  }
}
