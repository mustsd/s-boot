package com.github.mustsd.common.xls;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author yangz
 * @date 2022-06-20 12:09
 */
public class IntegerNumberConverter extends BaseDictConverter implements Converter<Integer> {

  @Override
  public Class<?> supportJavaTypeKey() {
    return Integer.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.NUMBER;
  }

  @Override
  public Integer convertToJavaData(
      ReadCellData<?> cellData,
      ExcelContentProperty contentProperty,
      GlobalConfiguration globalConfiguration) {
    return cellData.getNumberValue().intValue();
  }

  @Override
  public WriteCellData<?> convertToExcelData(
      Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration)
      throws Exception {
    return new WriteCellData(getValue(value, contentProperty.getField()));
  }
}
