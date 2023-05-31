package com.brian.minio.response;

import lombok.Builder;
import lombok.Data;
import okhttp3.Headers;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : brian
 * @since 0.1
 */
@Data
@Builder
public class FileResponse {

    private String object;
    private String filename;
    private String contentType;
    private Map<String, String> userMetadata;
    private Headers headers;
    private Map<String, String> tags;
    private Long fileSize;
    private LocalDateTime lastModified;
    private String detailPath;
    private String viewPath;
    private String downloadPath;
    private String deletePath;
}
