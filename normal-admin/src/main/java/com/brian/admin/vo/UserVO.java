package com.brian.admin.vo;

import com.brian.common.core.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends PageParam {

    private Long id;

    /**
     * 账号
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
    private List<Long> roleIds;


}
