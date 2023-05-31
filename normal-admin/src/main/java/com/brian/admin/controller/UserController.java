package com.brian.admin.controller;

import com.brian.admin.service.UserService;
import com.brian.admin.vo.UserVO;
import com.brian.common.core.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/list")
    public Result list(@RequestBody UserVO user) {

        return userService.list(user);
    }


    @RequestMapping("/update")
    public Result update(@RequestBody UserVO user) {

        return userService.update(user);
    }
}
