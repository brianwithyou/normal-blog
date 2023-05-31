package com.brian.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author brian
 */
@RefreshScope
@SpringBootApplication
@MapperScan("com.brian.admin.mapper")
@EnableFeignClients(basePackages = {"com.brian.web", "com.brian.user.api"})
public class NormalAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalAdminApplication.class, args);
    }

}
