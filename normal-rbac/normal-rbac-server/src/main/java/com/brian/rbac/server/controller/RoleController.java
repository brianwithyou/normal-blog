package com.brian.rbac.server.controller;

import com.brian.common.core.Result;
import com.brian.rbac.server.entity.Role;
import com.brian.rbac.server.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Brian
 * @date 2023/5/24
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/create")
    public Result create(@RequestBody Role role) {
        LocalDateTime now = LocalDateTime.now();
        role.setUpdateTime(now);
        role.setCreateTime(now);

        roleService.save(role);
        return Result.success();

    }
    @RequestMapping("/list")
    public Result list() {
        List<Role> list = roleService.list();

        return Result.success(list);

    }
    @RequestMapping("/update")
    public Result update(@RequestBody Role role) {
        boolean b = roleService.updateById(role);
        return Result.success();

    }

}
