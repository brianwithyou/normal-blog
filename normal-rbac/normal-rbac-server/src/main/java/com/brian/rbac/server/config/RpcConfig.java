package com.brian.rbac.server.config;

import com.brian.common.core.GlobalControllerAdvice;
import com.brian.common.core.LogAspect;
import com.brian.rbac.api.RbacAuthFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : brian
 * @since 0.1
 */
@Configuration
@EnableFeignClients(clients = {RbacAuthFeignClient.class})
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
