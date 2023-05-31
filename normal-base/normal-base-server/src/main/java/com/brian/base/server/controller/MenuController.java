package com.brian.base.server.controller;

import com.brian.base.server.convert.MenuConvert;
import com.brian.base.server.entity.SysMenu;
import com.brian.base.server.service.SysMenuService;
import com.brian.base.server.vo.MenuVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/base/menu")
public class MenuController {
    
    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    public List<MenuVO> listMenu() {
        List<SysMenu> list = sysMenuService.list();
        return MenuConvert.INSTANCE.convertVOList(list);

    }
}
