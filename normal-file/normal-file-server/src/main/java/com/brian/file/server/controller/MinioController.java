package com.brian.file.server.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.brian.minio.handler.MinioHandler;
import com.brian.minio.properties.MinioConfigProperty;
import com.brian.minio.response.FileResponse;
import com.brian.minio.service.MinioService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.minio.StatObjectResponse;
import io.minio.messages.Tags;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Brian
 * @date 2023/5/29
 **/

@Slf4j
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Resource
    private MinioService minioService;
    @Resource
    private MinioConfigProperty properties;
    @Resource
    private Optional<MinioHandler> minioHandler;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<FileResponse>> fileUpload(
            @RequestParam("classification") String classification,
            @RequestPart("files") List<MultipartFile> files) {
        minioHandler.ifPresent(handler -> handler.beforeUpload(classification, files));
        if (CollectionUtil.isEmpty(files)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if (!properties.getClassifications().contains(classification)) {
            throw new IllegalArgumentException("classification is not config.");
        }
        List<FileResponse> responses = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            String fileId = UUID.randomUUID().toString().replace("-", "");
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = fileId;
            }
            StringBuilder fileName = new StringBuilder(fileId);
            if (originalFilename.contains(".")) {
                fileName.append(originalFilename.substring(originalFilename.lastIndexOf(".")));
            }
            Path source = Path.of(classification, fileName.toString());
            Multimap<String, String> userMetadata = ArrayListMultimap.create(1, 1);
            userMetadata.put("original_file_name", originalFilename);
            minioService.upload(source, file.getInputStream(), null, userMetadata);
            responses.add(
                    buildFileResponse(minioService.getMetadata(source), minioService.getTags(source)));
        }
        AtomicReference<List<FileResponse>> reference = new AtomicReference<>(responses);
        minioHandler.ifPresent(handler -> reference.set(handler.afterUpload(responses)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reference.get());
    }

    @GetMapping("/view/**")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> viewFile(HttpServletRequest request) throws Exception {
        String object = request.getRequestURL().toString().split("/view/")[1];
        minioHandler.ifPresent(handler -> handler.beforeView(object));
        Path objectPath = Path.of(object);
        InputStream inputStream = minioService.get(objectPath);
        StatObjectResponse metadata = minioService.getMetadata(objectPath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.contentType()))
                .contentLength(metadata.size())
                .header("Content-disposition", "attachment; filename=" + getFileName(metadata.object()))
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/download/**")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request) throws Exception {
        String object = request.getRequestURL().toString().split("/download/")[1];
        minioHandler.ifPresent(handler -> handler.beforeDownloadGetObject(object));
        Path objectPath = Path.of(object);
        InputStream inputStream = minioService.get(objectPath);
        StatObjectResponse metadata = minioService.getMetadata(objectPath);
        AtomicReference<StatObjectResponse> ref = new AtomicReference<>(metadata);
        minioHandler.ifPresent(
                handler -> {
                    StatObjectResponse response = handler.beforeDownload(metadata);
                    if (response == null) {
                        log.warn("response can not be null.");
                    } else {
                        ref.set(response);
                    }
                });
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(ref.get().size())
                .header("Content-disposition", "attachment; filename=" + getFileName(ref.get().object()))
                .body(new InputStreamResource(inputStream));
    }

    @DeleteMapping("/delete/**")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeFile(HttpServletRequest request) {
        String object = request.getRequestURL().toString().split("/delete/")[1];
        minioHandler.ifPresent(handler -> handler.beforeDelete(object));
        Path objectPath = Path.of(object);
        minioService.remove(objectPath);
        minioHandler.ifPresent(handler -> handler.afterDelete(object));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detail/**")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FileResponse> getFileDetail(HttpServletRequest request) {
        String object = request.getRequestURL().toString().split("/detail/")[1];
        minioHandler.ifPresent(handler -> handler.beforeGetDetail(object));
        Path objectPath = Path.of(object);
        StatObjectResponse metadata = minioService.getMetadata(objectPath);
        FileResponse response = buildFileResponse(metadata, minioService.getTags(objectPath));
        AtomicReference<FileResponse> reference = new AtomicReference<>(response);
        minioHandler.ifPresent(handler -> reference.set(handler.afterGetDetail(response)));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reference.get());
    }

    private FileResponse buildFileResponse(StatObjectResponse metadata, Tags tags) {
        FileResponse.FileResponseBuilder builder = FileResponse.builder();
        String object = metadata.object();
        String objectPrefix = properties.getObjectPrefix();
        if (!objectPrefix.endsWith("/")) {
            objectPrefix = objectPrefix + "/";
        }
        objectPrefix = objectPrefix + "minio/";
        builder
                .object(object)
                .detailPath(objectPrefix + "detail/" + object)
                .viewPath(objectPrefix + "view/" + object)
                .downloadPath(objectPrefix + "download/" + object)
                .deletePath(objectPrefix + "delete/" + object)
                .lastModified(metadata.lastModified().toLocalDateTime())
                .fileSize(metadata.size())
                .filename(getFileName(metadata.object()))
                .contentType(metadata.contentType())
                .userMetadata(metadata.userMetadata())
                .headers(metadata.headers());
        if (tags != null) {
            builder.tags(tags.get());
        }
        return builder.build();
    }


    private String getFileName(String object) {
        if (StrUtil.isBlank(object)) {
            return UUID.randomUUID().toString().replace("-", "");
        }
        if (!object.contains("/") || object.endsWith("/")) {
            return object;
        }
        return object.substring(object.lastIndexOf("/") + 1);
    }

}