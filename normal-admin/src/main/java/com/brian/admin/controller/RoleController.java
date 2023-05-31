package com.brian.admin.controller;

import com.brian.admin.convert.RoleConvert;
import com.brian.admin.entity.Role;
import com.brian.admin.entity.RoleMenu;
import com.brian.admin.service.RoleMenuService;
import com.brian.admin.service.RoleService;
import com.brian.admin.vo.RoleVO;
import com.brian.common.core.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private RoleMenuService roleMenuService;

    @RequestMapping("/create")
    public Result create(@RequestBody RoleVO role) {
        LocalDateTime now = LocalDateTime.now();
        role.setUpdateTime(now);
        role.setCreateTime(now);

        Role convert = RoleConvert.INSTANCE.convert(role);

        roleService.save(convert);

        List<Long> menuIds = role.getMenuIds();
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(convert.getId());
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        roleMenuService.saveBatch(roleMenus);
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
    @RequestMapping("/listCurRoles")
    public Result listCurRoles() {
        List<Role> roles = roleService.listCurRoles();
        return Result.success(roles);
    }

}
