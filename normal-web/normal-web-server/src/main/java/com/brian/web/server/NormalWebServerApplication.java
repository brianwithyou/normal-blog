package com.brian.web.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author brian
 */
@RefreshScope
@SpringBootApplication
@ComponentScan("com.brian.web")
// util 工具类
@ComponentScan("com.brian.normal")
@EnableFeignClients(basePackages = {"com.brian.user.api", "com.brian.rbac.api"})
public class NormalWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalWebServerApplication.class, args);
    }

}
