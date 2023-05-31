package com.brian.search.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author brian
 */
@RefreshScope
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.brian.web", "com.brian.user"})
@EnableElasticsearchRepositories(basePackages = {"com.brian.search.server.mapper"})
public class NormalSearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalSearchServerApplication.class, args);
    }

}


