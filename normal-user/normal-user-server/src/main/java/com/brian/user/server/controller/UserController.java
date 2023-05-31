package com.brian.user.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.common.core.Result;
import com.brian.user.server.convert.UserConvert;
import com.brian.user.server.entity.User;
import com.brian.user.server.service.UserService;
import com.brian.user.server.vo.UserCreateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.brian.common.constant.exception.GlobalErrorCodeEnum.REPEATED_USER;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@RestController
@Api(tags = "用户服务")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/getUser")
    @ApiImplicitParam(name = "username", value = "用户名", example = "Alice", required = true, allowMultiple = false)
    public User getUserByUsername(@RequestParam String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        return user;
    }

    @RequestMapping("/getCurrentUser")
    @ApiImplicitParam()
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return null;
    }
//    @PostAuthorize()
//    @PreFilter()
//    @PostFilter()
    @PreAuthorize("hasAnyRole('admin')")
    @RequestMapping("/list")
    public List<User> list() {

        List<User> users = userService.list(new QueryWrapper<User>().ne("status", 0));
        return users;
    }
    @RequestMapping("/add")
    public Result<?> add(@RequestBody UserCreateVO userVo) {

        User userCheck = userService.getOne(new QueryWrapper<User>()
                .eq("account_number", userVo.getUsername()));
        if (userCheck != null) {
            return Result.fail(REPEATED_USER);
        }
        LocalDateTime now = LocalDateTime.now();
        User user = UserConvert.INSTANCE.convert(userVo);
        user.setUpdateTime(now);
        user.setCreateTime(now);
        boolean save = userService.save(user);
        if (!save) {
            return Result.fail();
        }
        return Result.success("新增成功");
    }

}
