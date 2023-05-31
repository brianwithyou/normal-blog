package com.brian.rbac.server.service;

import com.brian.common.core.Result;
import com.brian.rbac.server.dto.Token;
import com.brian.user.api.dto.UserDTO;

/**
 * @author : Brian
 * @since 0.1
 */
public interface AuthService {
    Token login(String accountNumber, String password, String clientId, String clientSecret, String grantType);

    Result register(UserDTO userDTO);
}
