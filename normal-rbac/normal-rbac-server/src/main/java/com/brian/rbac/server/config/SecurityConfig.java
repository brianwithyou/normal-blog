//package com.brian.rbac.server.config;
//
//import com.brian.common.constant.core.AuthTypeEnum;
////import com.brian.rbac.server.filter.JwtAuthenticationFilter;
//import com.brian.rbac.server.handler.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.annotation.Resource;
//
///**
// * @author : brian
// * @since 0.1
// */
//@Configuration
//@EnableWebSecurity
//@AutoConfiguration
//@ComponentScan("com.brian.rbac")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Resource
//    private LoginSuccessHandler loginSuccessHandler;
//
//    @Resource
//    private LoginFailureHandler loginFailureHandler;
//
//    @Resource
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Resource
//    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    @Resource
//    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
//
//    @Resource
//    private AuthenticationConfiguration authenticationConfiguration;
//
//    @Value("${auth.type}")
//    private String authType;
//
//    private static final String[] URL_WHITELIST = {
//            "/",
//            "/oauth",
//            "/rbac/oauth/**",
//            "/rbac/login",
//            "/rbac/logout",
//            "/rbac/register",
//            "/web/blog/list",
//            "/web/blog/feed",
//            "/web/blog/hotBlogs",
//            "/logout",
//            "/favicon.ico",
//            "/area/**",
//            "/file/down/**"
//    };
//
////    @Bean
////    JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().antMatchers(URL_WHITELIST);
//    }
//    /**
//     * 由于oauth2.0 需要配置 AuthorizationServerSecurityConfigurer
//     * 而 AuthorizationServerSecurityConfigurer 与 SecurityFilterChain不能共存
//     * 所以暂时只能通过旧的方式设置httpSecurity
//     *
//     * spring security @Deprecated 了 默认的 AuthorizationServerConfigurerAdapter配置，建议使用第三方的配置，如keycloak
//     * 参考这篇，No Authorization Server Support
//     * https://spring.io/blog/2019/11/14/spring-security-oauth-2-0-roadmap-update
//     * 迁移指南 https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide
//     * OAuth2.0 资源服务器现在是 spring-security 核心的一部分。不幸的是，他们没有 EnableAuthorizationServer 的替代品。
//     *
//     * 配置httpSecurity
//     * 配置webSecurity
//     * @see WebSecurityCustomizer
//     */
////    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        // web鉴权，使用自定义/login方法
//        if (!AuthTypeEnum.WEB.getValue().equals(authType)) {
//            httpSecurity.formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler);
//        }
//
//        httpSecurity
//                // 允许跨域
//                .cors()
//                .and()
//                // 禁用 csrf
//                .csrf().disable()
//                .logout().logoutSuccessHandler(jwtLogoutSuccessHandler)
//                // 禁用session
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers(URL_WHITELIST).permitAll()
////                .anyRequest().authenticated()
//                // 异常处理
//                .and().exceptionHandling()
////                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
////                .and()
////                .addFilter(jwtAuthenticationFilter(authenticationConfiguration))
//        ;
//
//        return httpSecurity.build();
//    }
//}