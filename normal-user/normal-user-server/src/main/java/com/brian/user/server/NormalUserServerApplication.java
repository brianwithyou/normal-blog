package com.brian.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author brian
 */
@RefreshScope
@SpringBootApplication
public class NormalUserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NormalUserServerApplication.class, args);
    }

}
