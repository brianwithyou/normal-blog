package com.brian.rbac.server.config;

import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * 下面是框架提供的URL路径：
 * <p>
 * /oauth/authorize：授权端点
 * /oauth/token：令牌端点
 * /oauth/confirm_access：用户批准授权的端点
 * /oauth/error：用于渲染授权服务器的错误
 * /oauth/check_token：资源服务器解码access token
 * /oauth/check_token：当使用JWT的时候，暴露公钥的端点
 *
 * @author brian
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;
    /**
     * jwt令牌转换器
     */
    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * SpringSecurity 用户自定义授权认证类
     */
    @Resource
    private UserDetailsService userDetailsService;
    /**
     * 授权认证管理器
     */
    @Resource
    private AuthenticationManager authenticationManager;
    //令牌持久化存储接口
    @Resource
    private TokenStore tokenStore;

    @Resource
    private ClientDetailsService clientDetailsService;

//    @Resource
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /***
     * 客户端信息配置
     * @param clients clientConfig
     * @throws Exception ex
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存鉴权，对client进行鉴权，数据库表 oauth_client_details
//        clients.inMemory()
//                        .withClient("normal")
//                                .secret("normal")
//                                        .redirectUris("http://localhost")
//                .accessTokenValiditySeconds(1200) // 120s
//                .refreshTokenValiditySeconds(1200)
//                // "authorization_code", "password", "implicit","client_credentials","refresh_token"
//                        .authorizedGrantTypes(
//                                "authorization_code",
//                                "client_credentials",
//                                "refresh_token",
//                                "password"
//                        ).scopes("app");

        clients.jdbc(dataSource).clients(clientDetailsService);
    }

//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /***
     * 授权服务器端点配置
     * @param endpoints endpoints
     * @throws Exception ex
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)   //认证管理器
                .tokenStore(tokenStore)                       //令牌存储
                .userDetailsService(userDetailsService);     //用户信息service
    }

    /***
     * 授权服务器的安全配置
     * @param oauthServer oauthServerConfig
     * @throws Exception ex
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients()
//                .passwordEncoder(bCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
        ;
    }

    /**
     * 读取密钥的配置
     */
    @Bean
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }


    /****
     * JWT令牌转换器
     * @param customUserAuthenticationConverter converter
     * @return res
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties().getKeyStore().getLocation(),                          //证书路径 normal.jks
                keyProperties().getKeyStore().getSecret().toCharArray())              //证书秘钥 normal
                .getKeyPair(
                        keyProperties().getKeyStore().getAlias(),                     //证书别名 normal
                        keyProperties().getKeyStore().getPassword().toCharArray());   //证书密码 normal
        converter.setKeyPair(keyPair);
        // 配置自定义的CustomUserAuthenticationConverter
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
        return converter;
    }
}

