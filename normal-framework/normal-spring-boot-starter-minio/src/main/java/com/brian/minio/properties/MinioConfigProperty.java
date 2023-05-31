package com.brian.minio.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Set;

/**
 * @author : <a href="mailto:xxxx@qq.com">xxxx</a>
 * @since 0.1
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "minio.config")
public class MinioConfigProperty implements InitializingBean {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private Set<String> classifications;

    private String objectPrefix;

    private Duration connectTimeout = Duration.ofSeconds(10);

    private Duration writeTimeout = Duration.ofSeconds(60);

    private Duration readTimeout = Duration.ofSeconds(10);

    private boolean checkBucket = true;

    private boolean createBucketIfNotExist = true;

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(accessKey, "accessKey must not be empty.");
        Assert.hasText(secretKey, "secretKey must not be empty.");
        Assert.hasText(bucketName, "bucketName must not be empty.");
        Assert.hasText(objectPrefix, "objectPrefix must not be empty.");
    }
}
