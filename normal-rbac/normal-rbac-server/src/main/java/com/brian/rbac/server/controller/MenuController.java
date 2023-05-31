package com.brian.rbac.server.controller;

import com.brian.common.core.Result;
import com.brian.rbac.server.convert.MenuConvert;
import com.brian.rbac.server.entity.Menu;
import com.brian.rbac.server.service.MenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/create")
    public Result create(@RequestBody Menu menu) {

        LocalDateTime now = LocalDateTime.now();

        Menu convert = MenuConvert.INSTANCE.convert(menu);
        convert.setCreateTime(now);
        convert.setUpdateTime(now);

        menuService.save(menu);
        return Result.success();
    }

    @RequestMapping("/list")
    public Result list(@RequestBody Menu menu) {
        List<Menu> list = menuService.list();
        return Result.success(list);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Menu menu) {
        Menu convert = MenuConvert.INSTANCE.convert(menu);
        convert.setUpdateTime(LocalDateTime.now());

        boolean b = menuService.updateById(menu);
        return Result.success();
    }
}
