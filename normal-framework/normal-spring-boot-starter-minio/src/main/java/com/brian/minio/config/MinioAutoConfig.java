package com.brian.minio.config;

import com.brian.minio.properties.MinioConfigProperty;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@Configuration
@AutoConfiguration
@AllArgsConstructor
@ComponentScan("com.brian.minio")
@EnableConfigurationProperties(MinioConfigProperty.class)
public class MinioAutoConfig {

    private final MinioConfigProperty properties;

    @Bean
    public MinioClient minioClient()
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(properties.getUrl())
                        .credentials(properties.getAccessKey(), properties.getSecretKey())
                        .build();
        minioClient.setTimeout(
                properties.getConnectTimeout().toMillis(),
                properties.getWriteTimeout().toMillis(),
                properties.getReadTimeout().toMillis());
        if (properties.isCheckBucket()) {
            String bucketName = properties.getBucketName();
            BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            boolean bucketExists = minioClient.bucketExists(existsArgs);
            if (!bucketExists) {
                if (properties.isCreateBucketIfNotExist()) {
                    MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
                    minioClient.makeBucket(makeBucketArgs);
                } else {
                    throw new IllegalStateException("Bucket does not exist: " + bucketName);
                }
            }
        }
        log.info("minio initialized...");
        return minioClient;
    }
}
