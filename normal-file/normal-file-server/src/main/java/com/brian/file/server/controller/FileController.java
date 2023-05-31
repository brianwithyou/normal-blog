package com.brian.file.server.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.brian.common.core.Result;
import com.brian.minio.handler.MinioHandler;
import com.brian.minio.properties.MinioConfigProperty;
import com.brian.minio.response.FileResponse;
import com.brian.minio.service.MinioService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.minio.StatObjectResponse;
import io.minio.messages.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Brian
 * @date 2023/5/30
 **/
@RestController
@RequestMapping("/")
public class FileController {

    @Resource
    private MinioService minioService;
    @Resource
    private MinioConfigProperty properties;
    @Resource
    private Optional<MinioHandler> minioHandler;

    @RequestMapping("/upload")
    public Result<?> upload(MultipartFile multipartFile) {

        return Result.success();
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Result<List<FileResponse>> fileUpload(
            @RequestParam("classification") String classification,
            @RequestPart("file") List<MultipartFile> files) throws IOException {
        minioHandler.ifPresent(handler -> handler.beforeUpload(classification, files));
        if (CollectionUtil.isEmpty(files)) {
            return Result.fail("文件不能为空");
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
            responses.add(buildFileResponse(minioService.getMetadata(source), minioService.getTags(source)));
        }
        AtomicReference<List<FileResponse>> reference = new AtomicReference<>(responses);
        minioHandler.ifPresent(handler -> reference.set(handler.afterUpload(responses)));
        return Result.success(reference.get());
    }

    private FileResponse buildFileResponse(StatObjectResponse metadata, Tags tags) {
        FileResponse.FileResponseBuilder builder = FileResponse.builder();
        String object = metadata.object();
        String objectPrefix = properties.getObjectPrefix();
        if (!objectPrefix.endsWith("/")) {
            objectPrefix = objectPrefix + "/";
        }
//        objectPrefix = objectPrefix + "minio/";
        builder
                .object(object)
                .detailPath(objectPrefix + "detail/" + object)
//                .viewPath(objectPrefix + "view/" + object)
                .viewPath(objectPrefix + object)
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
