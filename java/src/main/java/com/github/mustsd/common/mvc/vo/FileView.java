package com.github.mustsd.common.mvc.vo;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author mustsd
 * @date 2022-04-26 17:51
 */
public class FileView extends AbstractView {

  private String fileName;
  private String fileExt;
  private byte[] bytes;

  public FileView(String fileName, String fileExt, byte[] bytes) {
    this.fileName = fileName;
    this.fileExt = fileExt;
    this.bytes = bytes;
  }

  @Override
  protected void renderMergedOutputModel(
      Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setContentType("application/octet-stream");
    response.addHeader("Content-Length", "" + bytes.length);
    response.setHeader("content-disposition", "attachment");
    response.setHeader("filename", URLEncoder.encode(this.fileName, "UTF-8"));
    response.setHeader("ext", this.fileExt);
    response.setHeader("Access-Control-Expose-Headers", "ext,filename");
    ServletOutputStream outputStream = response.getOutputStream();

    outputStream.write(this.bytes);
    outputStream.flush();
  }
}
