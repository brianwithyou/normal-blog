package com.brian.minio.handler;

import com.brian.minio.response.FileResponse;
import io.minio.StatObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * minio starter的扩展点，类似于 afterProperties， @PostConstruct等
 * @author brian
 */
public interface MinioHandler {

  /**
   * beforeUpload
   * @param classification classification
   * @param files files
   */
  default void beforeUpload(String classification, List<MultipartFile> files) {}

  /**
   * afterUpload
   * @param responses response
   * @return fileResponses
   */
  default List<FileResponse> afterUpload(List<FileResponse> responses) {
    return responses;
  }

  /**
   * beforeView
   * @param object params
   */
  default void beforeView(String object) {}

  /**
   * beforeDownloadGetObject
   * @param object params
   */
  default void beforeDownloadGetObject(String object) {}

  /**
   * beforeDownload
   * @param response params
   * @return response
   */
  default StatObjectResponse beforeDownload(StatObjectResponse response) {
    return response;
  }

  /**
   * beforeDelete
   * @param object params
   */
  default void beforeDelete(String object) {}

  /**
   * afterDelete
   * @param object params
   */
  default void afterDelete(String object) {}

  /**
   * beforeGetDetail
   * @param object params
   */
  default void beforeGetDetail(String object) {}

  /**
   * afterGetDetail
   * @param response params
   * @return response
   */
  default FileResponse afterGetDetail(FileResponse response) {
    return response;
  }
}