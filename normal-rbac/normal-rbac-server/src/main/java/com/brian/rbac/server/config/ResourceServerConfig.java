//package com.brian.rbac.server.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.stream.Collectors;
//
///**
// * oauth2.0 资源服务器 的配置
// * 资源服务器 相对于 认证服务器 normal-rbac-server
// *
// * @author : brian
// * @since 0.1
// */
//@Configuration
//// 开启资源校验服务，令牌校验【注意】认证服务器加入此类，会导致加密方式的变化，导致token无法被解析
//@EnableResourceServer
//// 激活方法上的 PreAuthorize注解
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    private static final String PUBLIC_KEY = "public.key";
//
//    @Bean
//    public TokenStore tokenStore(JwtAccessTokenConverter tokenConverter) {
//        return new JwtTokenStore(tokenConverter);
//    }
//
//    @Bean
//    @Primary
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setVerifierKey(getPublicKey());
//        return converter;
//    }
//
//    private String getPublicKey() {
//        Resource resource = new ClassPathResource(PUBLIC_KEY);
//        try {
//            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            return bufferedReader.lines().collect(Collectors.joining("\n"));
//        } catch (IOException e) {
//            throw new RuntimeException("获取public key失败");
//        }
//    }
//
//    @Override
//    public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests()
//                // 放行url
//                .antMatchers("/user/register"
//                        , "/blog/list"
//                        , "/server/list"
//                        , "/tag/list"
//                        , "/rank/blogs"
//                )
//                .permitAll()
//                // 其他地址全都需要认证
//                .anyRequest().authenticated();
//    }
//
//}
//
