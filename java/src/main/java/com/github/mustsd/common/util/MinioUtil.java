package com.github.mustsd.common.util;

import com.github.mustsd.config.properties.MinioProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * MinIO util
 *
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class MinioUtil {

  @Autowired
  MinioProperties minioProperties;
  @Autowired MinioClient minioClient;

  /**
   * 判断 bucket是否存在
   *
   * @param bucketName: 桶名
   * @return: boolean
   */
  @SneakyThrows(Exception.class)
  public boolean bucketExists(String bucketName) {
    BucketExistsArgs args = BucketExistsArgs.builder().bucket(bucketName).build();
    return minioClient.bucketExists(args);
  }

  /**
   * 创建 bucket
   *
   * @param bucketName: 桶名
   * @return: void
   */
  @SneakyThrows(Exception.class)
  public void createBucket(String bucketName) {
    boolean isExist = this.bucketExists(bucketName);
    if (!isExist) {
      MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();
      minioClient.makeBucket(args);
    }
  }

  /**
   * 获取全部bucket
   *
   * @param :
   * @return: java.util.List<io.minio.messages.Bucket>
   * @date : 2020/8/16 23:28
   */
  @SneakyThrows(Exception.class)
  public List<Bucket> getAllBuckets() {
    return minioClient.listBuckets();
  }

  /**
   * 文件上传
   *
   * @param bucketName: 桶名
   * @param fileName: 文件名
   * @param inputStream: 文件流
   * @return: java.lang.String : 文件url地址
   */
  @SneakyThrows(Exception.class)
  public String upload(String bucketName, String fileName, InputStream inputStream) {
    PutObjectArgs args =
        PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                inputStream, inputStream.available(), -1)
            .build();
    minioClient.putObject(args);
    inputStream.close();
    return getFileUrl(bucketName, fileName);
  }

  /**
   * 文件上传
   *
   * @param bucketName: 桶名
   * @param file: 文件
   * @return: java.lang.String : 文件url地址
   */
  @SneakyThrows(Exception.class)
  public String upload(String bucketName, MultipartFile file) {
    final InputStream inputStream = file.getInputStream();
    final String fileName = file.getOriginalFilename();
    this.upload(bucketName, fileName, inputStream);
    return getFileUrl(bucketName, fileName);
  }

  /**
   * 删除文件
   *
   * @param bucketName: 桶名
   * @param fileName: 文件名
   * @return: void
   */
  @SneakyThrows(Exception.class)
  public void deleteFile(String bucketName, String fileName) {
    minioClient.removeObject(bucketName, fileName);
  }

  /**
   * 下载文件
   *
   * @param bucketName: 桶名
   * @param fileName: 文件名
   * @param response:
   * @return: void
   */
  @SneakyThrows(Exception.class)
  public void download(String bucketName, String fileName, HttpServletResponse response) {
    // 获取对象的元数据
    final ObjectStat stat = minioClient.statObject(bucketName, fileName);
    response.setContentType(stat.contentType());
    response.setCharacterEncoding("UTF-8");
    response.setHeader(
        "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    InputStream is = minioClient.getObject(bucketName, fileName);
    IOUtils.copy(is, response.getOutputStream());
    is.close();
  }

  /**
   * 获取minio文件的下载地址
   *
   * @param bucketName: 桶名
   * @param fileName: 文件名
   * @return: java.lang.String
   */
  @SneakyThrows(Exception.class)
  public String getFileUrl(String bucketName, String fileName) {
    return minioProperties.getUrl() + "/" + bucketName + "/" + fileName;
    //    return minioClient.getPresignedObjectUrl(
    //        GetPresignedObjectUrlArgs.builder()
    //            .method(Method.GET)
    //            .bucket(bucketName)
    //            .object(fileName)
    //            .build());
  }
}
