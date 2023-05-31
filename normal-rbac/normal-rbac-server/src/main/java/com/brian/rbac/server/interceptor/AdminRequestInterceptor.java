package com.brian.rbac.server.interceptor;

import com.brian.rbac.server.util.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 调用其他微服务时，创建一个管理员token，并携带给资源服务以通过资源服务器的校验
 * @author : brian
 * @since 0.1
 */
@Configuration
public class AdminRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // auth server调用其他服务时，为自己生成一个管理员token
        String token = JwtUtil.generateAdminToken();
        template.header("Authorization", "Bearer " + token);
    }
}
