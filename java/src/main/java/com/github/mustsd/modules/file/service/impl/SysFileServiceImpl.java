package com.github.mustsd.modules.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.common.util.MinioUtil;
import com.github.mustsd.config.properties.MinioProperties;
import com.github.mustsd.modules.file.entity.SysFile;
import com.github.mustsd.modules.file.mapper.SysFileMapper;
import com.github.mustsd.modules.file.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author manage
 * @date 2022-03-22 15:45:09
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile>
    implements ISysFileService {

  @Autowired
  MinioUtil minioUtil;
  @Autowired
  MinioProperties properties;

  @Override
  public String uploadFile(MultipartHttpServletRequest request, String bucket) {
    MultipartFile multipartFile = request.getFile("file");
    minioUtil.createBucket(bucket);
    String filePath = minioUtil.upload(bucket, multipartFile);
    long fileBytes = multipartFile.getSize();
    SysFile file = new SysFile();
    file.setName(multipartFile.getOriginalFilename());
    file.setBucket(bucket);
    file.setPath(filePath);
    file.setFileSize(
        new BigDecimal(fileBytes)
            .divide(CommonConstant.FILE_SIZE_MB)
            .setScale(2, RoundingMode.HALF_UP));
    this.save(file);
    return filePath;
  }

  @Override
  public void removeFile(String id) {
    SysFile file = this.getById(id);
    if (file != null && removeById(id)) {
      minioUtil.deleteFile(CommonConstant.ATTACHMENT_BUCKET, file.getName());
    } else {
      throw new BusinessException("删除失败");
    }
  }
}
