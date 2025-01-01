package com.github.mustsd.common.xls;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author yangz
 * @date 2022-03-19 11:03
 */
public class EntityExcelView<T> extends AbstractView {

  private String title;

  private Class<T> clazz;

  private List<T> data;

  public EntityExcelView(String title, Class<T> clazz, List<T> data) {
    this.title = title;
    this.clazz = clazz;
    this.data = data;
  }

  @Override
  protected void renderMergedOutputModel(
      Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setHeader("content-disposition", "attachment");
    response.setHeader("filename", URLEncoder.encode(title, "UTF-8"));
    response.setHeader("ext", ExcelConstant.FILE_EXT);
    response.setHeader("Access-Control-Expose-Headers", "ext,filename");
    ServletOutputStream outputStream = response.getOutputStream();
    ExcelWriterBuilder writerBuilder =
        EasyExcel.write(outputStream, clazz)
            .excelType(ExcelTypeEnum.XLSX)
            .registerConverter(new StringStringConverter())
            .registerConverter(new IntegerNumberConverter())
            .registerWriteHandler(new ExportStyle());
    ExcelWriterSheetBuilder sheetBuilder = writerBuilder.sheet(title);
    sheetBuilder.doWrite(data);
    outputStream.flush();
  }
}
