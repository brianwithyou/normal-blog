package com.brian.user.server.config;

import com.brian.user.api.UserFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author : brian
 * @since 0.1
 */
@Configuration
@EnableFeignClients(clients = {UserFeignClient.class})
public class RpcConfig {


}
