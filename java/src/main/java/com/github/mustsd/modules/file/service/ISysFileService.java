package com.github.mustsd.modules.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.file.entity.SysFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author manage
 * @date 2022-03-22 15:45:09
 */
public interface ISysFileService extends IService<SysFile> {
  /**
   * 上传并保存记录
   *
   * @param request
   * @param bucket
   * @return
   */
  String uploadFile(MultipartHttpServletRequest request, String bucket);

  /**
   * 移除oss存储文件与系统记录
   *
   * @param id
   */
  void removeFile(String id);
}
