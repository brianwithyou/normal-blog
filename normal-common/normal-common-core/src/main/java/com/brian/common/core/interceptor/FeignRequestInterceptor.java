package com.brian.common.core.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * @author : brian
 * @since 0.1
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 将 调用方的header 全部 原封不动的 传到 下一级中，当本服务调用 下一级服务时，也就包含了 token
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            // 跳过 content-length，解决too many bites written的问题
            if (headerName.equalsIgnoreCase("content-length")) {
                continue;
            }
            String headerVal = requestAttributes.getRequest().getHeader(headerName);
            requestTemplate.header(headerName, headerVal);
        }
    }
}
