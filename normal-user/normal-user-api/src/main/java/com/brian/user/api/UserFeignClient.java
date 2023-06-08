package com.brian.user.api;

import com.brian.common.constant.Const;
import com.brian.common.constant.user.UserConst;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.user.api.dto.UserStatisticDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 用户服务")
@FeignClient(name = UserConst.USER_APP_NAME, path = "/user")
public interface UserFeignClient {

    /**
     * /feign/user
     */
    String PREFIX = Const.FEIGN_API_PREFIX + "/user";

    @GetMapping(PREFIX + "/getUserByUsername")
    @ApiOperation(value = "根据用户名获取用户", notes = "备注：用户名必填")
    UserDTO getUserByUsername(@Valid @RequestParam String username);

    @GetMapping(PREFIX + "/getUserByAccount")
    @ApiOperation(value = "根据账号获取用户", notes = "备注：账号必填")
    UserDTO getUserByAccount(@Valid @RequestParam String account);

//    @PostMapping("/save")
//    @ApiOperation(value = "保存用户", notes = "备注：用户名，密码必填")
//    boolean save(@RequestBody UserDTO user);

    @GetMapping(PREFIX + "/testGetCurrentUser")
    @ApiOperation(value = "根据用户名获取用户", notes = "备注：用户名必填")
    String testGetCurrentUser();

    @RequestMapping(PREFIX + "/register")
    @ApiOperation(value = "根据用户名获取用户", notes = "备注：用户名必填")
    Result register(@RequestBody UserDTO user);

    @GetMapping(PREFIX + "/users")
    @ApiOperation(value = "获取全部用户", notes = "全部用户")
    List<UserDTO> listUsers();

    @RequestMapping(PREFIX + "/page")
    @ApiOperation(value = "分页获取全部用户", notes = "全部用户")
    PageResult<UserDTO> page(@RequestBody UserDTO userDTO, @RequestParam Integer pageNum, @RequestParam Integer pageSize);


    @GetMapping(PREFIX + "/user")
    @ApiOperation(value = "根据用户id获取用户信息", notes = "全部用户")
    UserDTO getUserById(@RequestParam Long uid);

    @RequestMapping(PREFIX + "/listByIds")
    @ApiOperation(value = "获取全部用户", notes = "全部用户")
    List<UserDTO> listByIds(@RequestBody List<Long> ids);

    @RequestMapping(PREFIX + "/update")
    @ApiOperation(value = "更新用户昵称", notes = "单个用户")
    void update(@RequestBody UserDTO userDTO);

    @RequestMapping(PREFIX + "/statistic")
    @ApiOperation(value = "根据用户id获取用户活跃统计", notes = "单个用户")
    List<UserStatisticDTO> statistic(@RequestBody Long id);
}
