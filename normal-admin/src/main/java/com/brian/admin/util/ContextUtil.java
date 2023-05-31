package com.brian.admin.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.brian.user.api.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author Brian
 * @date 2023/5/26
 **/
public class ContextUtil {

    public static UserDTO getCurrentUser() {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                SecurityContextHolder.getContext().getAuthentication().getDetails();
        JWT jwt = JWTUtil.parseToken(details.getTokenValue());

        JSONObject payloads = jwt.getPayloads();
        return JSONUtil.toBean(payloads, UserDTO.class);
    }
}
