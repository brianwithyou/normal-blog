package com.brian.user.server.config;

import com.brian.common.core.GlobalControllerAdvice;
import com.brian.common.core.LogAspect;
import com.brian.user.api.UserFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : brian
 * @since 0.1
 */
@Configuration
@EnableFeignClients(clients = {UserFeignClient.class})
public class RpcConfig {

    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {
        return new GlobalControllerAdvice();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
