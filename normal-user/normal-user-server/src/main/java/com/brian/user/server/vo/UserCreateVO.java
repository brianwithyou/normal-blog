package com.brian.user.server.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class UserCreateVO {
    /**
     * 账号，与username保持一致
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 呢称
     */
    private String nickname;

    /**
     * 用户名/昵称
     */
    private String username;

    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    private String avatar;
}
