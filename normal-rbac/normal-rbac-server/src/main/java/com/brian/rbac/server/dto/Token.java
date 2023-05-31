package com.brian.rbac.server.dto;

import com.brian.rbac.server.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class Token {

    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("refresh_token")
    private String refreshToken;

    /**
     * token唯一标识
     */
    private String jti;

    private UserVO userVO;

}
