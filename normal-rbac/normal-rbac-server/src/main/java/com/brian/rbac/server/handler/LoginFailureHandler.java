package com.brian.rbac.server.handler;

import cn.hutool.json.JSONUtil;
import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import com.brian.common.core.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

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
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            response.setContentType("application/json;charset=utf-8");
            Result<String> result = Result.fail(GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR);
            if (exception instanceof BadCredentialsException) {
                result = Result.fail(GlobalErrorCodeEnum.INVALID_PASSWORD);
            }
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        }finally {
            if(outputStream != null ) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}