package com.brian.rbac.api;

import com.brian.common.constant.Const;
import com.brian.common.constant.user.UserConst;
import com.brian.rbac.api.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 用户服务")
@FeignClient(name = UserConst.RBAC_APP_NAME, contextId = "auth")
public interface RbacAuthFeignClient {

    String PREFIX = Const.FEIGN_API_PREFIX + "/user";


    @GetMapping(PREFIX + "/encodePwd")
    @ApiOperation(value = "对密码加密", notes = "备注")
    String encodePwd(String password);


    @GetMapping(PREFIX + "/feignGetCurrentUser")
    @ApiOperation(value = "根据token获取用户", notes = "备注")
    UserDTO feignGetCurrentUser();

}
