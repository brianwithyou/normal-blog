package com.brian.admin.config;

import com.brian.common.core.GlobalControllerAdvice;
import com.brian.common.core.LogAspect;
import com.brian.common.core.interceptor.FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Configuration
public class RpcConfig {

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {
        return new GlobalControllerAdvice();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
