package com.brian.rbac.server.config;

import com.brian.rbac.server.handler.JwtAccessDeniedHandler;
import com.brian.rbac.server.handler.JwtLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(-1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Value("${auth.type}")
    private String authType;

    private static final String[] URL_WHITELIST = {
            // 注意 不需要加 context-path
            "/",
            "/auth/login",
            "/auth/register",
            "/auth/logout",
            "/register",
            "/web/blog/list",
            "/web/blog/feed",
            "/web/blog/hotBlogs",
            "/logout",
            "/favicon.ico",
            "/area/**",
            "/file/down/**"
    };

    //客户端配置
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication()
//                .passwordEncoder(bCryptPasswordEncoder)
//                .withUser("normal")
//                .password(bCryptPasswordEncoder.encode("normal"))
//                .roles("admin")
//                ;
//         这是对用户设置为从 数据库中获取， AuthorizationServerConfig中的配置是对client信息从数据库中获取
//         表名 users groups authorities...
//        auth.jdbcAuthentication().dataSource(dataSource);
        // 自定义表用户名密码
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    /***
     * 忽略安全拦截的URL
     * @param web web
     * @throws Exception ex
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//                "/",
//                "/rbac/auth/login",
//                "/rbac/auth/logout");
        web.ignoring().antMatchers();
//        web.ignoring().antMatchers(URL_WHITELIST);
    }

    /***
     * 创建授权管理认证对象
     * @return res
     * @throws Exception ex
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
//        return authenticationManager();
    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    /****
     *
     * @param httpSecurity httpSecurity
     * @throws Exception ex
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        // web鉴权，使用自定义/login方法
//        if (!AuthTypeEnum.WEB.getValue().equals(authType)) {
//            httpSecurity.formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler);
//        }

        httpSecurity
                .cors().and()
                .csrf().disable()
                .logout().logoutSuccessHandler(jwtLogoutSuccessHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic()        //启用Http基本身份验证
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .and()
//                .addFilter(jwtAuthenticationFilter(authenticationConfiguration))
        ;

//                .formLogin()       //启用表单身份验证
//                .and()
//                .authorizeRequests()    //限制基于Request请求访问
//                .anyRequest()
//                .authenticated();       //其他请求都需要经过验证
    }

//    @Bean
//    JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
//    }

}
