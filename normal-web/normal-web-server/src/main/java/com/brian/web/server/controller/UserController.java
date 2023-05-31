package com.brian.web.server.controller;

import com.brian.common.core.Result;
import com.brian.rbac.api.RbacAuthFeignClient;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.convert.UserConvert;
import com.brian.web.server.util.ContextUtil;
import com.brian.web.server.vo.UserVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RbacAuthFeignClient rbacAuthFeignClient;

    @Resource
    private UserFeignClient userFeignClient;
    @RequestMapping("/currentUser")
    public Result<UserVO> getCurrentUser() {

        UserDTO currentUser = ContextUtil.getCurrentUser();
        UserVO userVO = UserConvert.INSTANCE.convertVo(currentUser);
        return Result.success(userVO);
    }

    @RequestMapping("/update")
    public Result<UserVO> update(@RequestBody UserVO userVO) {
        UserDTO userDTO = UserConvert.INSTANCE.convertDto(userVO);
        userFeignClient.update(userDTO);
        return Result.success(userVO);
    }
}
