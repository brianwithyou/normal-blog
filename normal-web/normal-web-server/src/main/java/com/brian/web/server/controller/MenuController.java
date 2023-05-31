package com.brian.web.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.common.core.Result;
import com.brian.web.server.entity.Menu;
import com.brian.web.server.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public Result<?> list() {
        List<Menu> list = menuService.list(new QueryWrapper<Menu>()
                .ne("status", 0)
                .orderByDesc("sort")
        );
        return Result.success(list);
    }
}
