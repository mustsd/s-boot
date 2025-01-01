package com.github.mustsd.common.xls;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

/**
 * @author yangz
 * @date 2022-05-12 13:36
 */
public class ExportStyle extends HorizontalCellStyleStrategy {

  @Override
  public int order() {
    return OrderConstant.DEFINE_STYLE;
  }

  public ExportStyle() {
    super();
    WriteCellStyle headWriteCellStyle = new WriteCellStyle();
    headWriteCellStyle.setWrapped(true);
    headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
    headWriteCellStyle.setLocked(true);
    headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
    headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
    headWriteCellStyle.setBorderTop(BorderStyle.THIN);
    headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
    headWriteCellStyle.setBorderRight(BorderStyle.THIN);
    WriteFont headWriteFont = new WriteFont();
    headWriteFont.setFontName("微软雅黑");
    headWriteFont.setFontHeightInPoints((short) 12);
    headWriteFont.setBold(false);
    headWriteCellStyle.setWriteFont(headWriteFont);
    setHeadWriteCellStyle(headWriteCellStyle);

    WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
    contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
    WriteFont contentWriteFont = new WriteFont();
    contentWriteFont.setFontName("微软雅黑");
    contentWriteFont.setFontHeightInPoints((short) 10);
    contentWriteCellStyle.setWriteFont(contentWriteFont);
    setContentWriteCellStyleList(ListUtils.newArrayList(contentWriteCellStyle));
  }
}
