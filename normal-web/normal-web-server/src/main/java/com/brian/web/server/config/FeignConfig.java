package com.brian.web.server.config;

import com.brian.common.core.GlobalControllerAdvice;
import com.brian.common.core.LogAspect;
import com.brian.common.core.interceptor.FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求传递header信息
 * @author : brian
 * @since 0.1
 */
@Configuration
public class FeignConfig {

    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {
        return new GlobalControllerAdvice();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        //当主线程的请求执行完毕后，Servlet容器会被销毁当前的Servlet，因此在这里需要做判空
//        if (attributes != null) {
//            HttpServletRequest request = attributes.getRequest();
//            Enumeration<String> headerNames = request.getHeaderNames();
//
//            while (headerNames.hasMoreElements()) {
//                String name = headerNames.nextElement();
//                //不能把所有消息头都传递下去，否则会引起其他异常；header的name都是小写
//                if (name.equals(AUTHORIZATION.toLowerCase(Locale.ROOT))) {
//                    requestTemplate.header(name,request.getHeader(name));
//                }
//            }
//        }
//    }

}
