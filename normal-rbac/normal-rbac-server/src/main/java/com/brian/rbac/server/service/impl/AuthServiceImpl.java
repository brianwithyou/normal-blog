package com.brian.rbac.server.service.impl;

import com.brian.common.core.Result;
import com.brian.rbac.server.dto.Token;
import com.brian.rbac.server.service.AuthService;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author : brian
 * @since 0.1
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Token login(String username, String password, String clientId, String clientSecret, String grantType) {

        // 认证服务器 就是本服务
        String appName = applicationContext.getId();
        ServiceInstance choose = loadBalancerClient.choose(appName);
        URI uri = choose.getUri();
        // gateway服务器 需要拼接 /rac-server
//        String url = uri + "/rbac-server/rbac/oauth/token";
        String url = uri + "/rbac/oauth/token";
//        String url = "http://localhost:8089" + "/rbac/oauth/token";


        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Authorization", httpBasicAuth(clientId, clientSecret));

        // 只能用MultiValueMap 不能用 HashMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);

        ResponseEntity<Token> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Token.class);
        return response.getBody();

    }

    @Override
    public Result register(UserDTO userDTO) {
        return userFeignClient.register(userDTO);
    }

    private String httpBasicAuth(String clientId, String clientSecret) {
        return "Basic " + Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
    }
}
