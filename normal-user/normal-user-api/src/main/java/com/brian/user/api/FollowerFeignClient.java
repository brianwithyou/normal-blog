package com.brian.user.api;

import com.brian.common.constant.Const;
import com.brian.common.constant.user.UserConst;
import com.brian.user.api.dto.FollowerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 用户服务")
@FeignClient(name = UserConst.USER_APP_NAME, contextId = "follower")
public interface FollowerFeignClient {

    String PREFIX = Const.FEIGN_API_PREFIX + "/follower";

    @GetMapping(PREFIX + "/")
    @ApiOperation(value = "根据用户名获取用户", notes = "备注：用户名必填")
    List<FollowerDTO> listFollowers(@RequestParam("userId") Long userId);

}
