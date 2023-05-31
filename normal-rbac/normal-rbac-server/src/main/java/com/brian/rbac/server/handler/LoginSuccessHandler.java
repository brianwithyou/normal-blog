package com.brian.rbac.server.handler;

import cn.hutool.json.JSONUtil;
import com.brian.common.core.Result;
import com.brian.rbac.server.util.JwtUtil;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author : brian
 * @since 0.1
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 登录成功，生成jwt，并放置到请求头中
        String jwt = jwtUtil.generateToken(authentication.getName());
        response.addHeader(jwtUtil.getHeader(), jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        UserDTO user = userFeignClient.getUserByAccount(authentication.getName());

        Result<UserDTO> result = Result.success(user);
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}

