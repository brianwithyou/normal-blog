package com.brian.web.server.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class UserVO {

    private Long id;

    /**
     * 账号
     */
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
