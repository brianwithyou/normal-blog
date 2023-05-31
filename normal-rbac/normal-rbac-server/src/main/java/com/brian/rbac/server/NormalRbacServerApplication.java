package com.brian.rbac.server;

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
@MapperScan("com.brian.rbac.server.mapper")
@EnableFeignClients(basePackages = {"com.brian.rbac.api", "com.brian.user.api"})
public class NormalRbacServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NormalRbacServerApplication.class, args);
	}

}

