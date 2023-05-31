package com.brian.rbac.server.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import com.brian.common.core.Result;
import com.brian.rbac.server.convert.UserConvert;
import com.brian.rbac.server.dto.Token;
import com.brian.rbac.server.service.AuthService;
import com.brian.rbac.server.vo.UserVO;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录注册入口
 *
 * @author : brian
 * @since 0.1
 */
@Slf4j
@RestController
@Api(tags = "权限控制器")
@RequestMapping("/auth")
public class AuthController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVO user) {

        // 授权码 或者 密码
        String grantType = "password";
        Token token;
//        try {
            token = authService.login(user.getUsername(), user.getPassword(), clientId, clientSecret, grantType);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return Result.fail(GlobalErrorCodeEnum.INVALID_PASSWORD);
//        }
        if (ObjectUtil.isEmpty(token)) {
            return Result.fail(GlobalErrorCodeEnum.INVALID_PASSWORD);
        }
        UserDTO userDto = userFeignClient.getUserByUsername(user.getUsername());
        UserVO userVo = UserConvert.INSTANCE.convertVo(userDto);
        token.setUserVO(userVo);

        String accessToken = token.getAccessToken();
        JWT jwt = JWTUtil.parseToken(accessToken);
        JSONObject payloads = jwt.getPayloads();
        return Result.success(token);
    }
    @RequestMapping("/logout")
    public Result<?> logout() {

        // 授权码 或者 密码
        return Result.success();
    }

    @PostMapping("/getInfo")
    public Result<?> getInfo(@RequestBody Map<String, String> user) {
//        roles, name, avatar, introduction
        return Result.success();
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserVO user) {
        UserDTO userDTO = UserConvert.INSTANCE.convertDto(user);
        return authService.register(userDTO);
    }


}
