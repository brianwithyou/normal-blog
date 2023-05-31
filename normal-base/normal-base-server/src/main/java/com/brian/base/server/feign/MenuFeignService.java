package com.brian.base.server.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.base.api.MenuFeignClient;
import com.brian.base.api.dto.MenuDTO;
import com.brian.base.server.convert.MenuConvert;
import com.brian.base.server.entity.SysMenu;
import com.brian.base.server.service.SysMenuService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
public class MenuFeignService implements MenuFeignClient {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public MenuDTO getMenuByParentId(Long parentId) {
        SysMenu menu = sysMenuService.getOne(new QueryWrapper<SysMenu>().eq("parent_id", parentId));
        return MenuConvert.INSTANCE.convertDTO(menu);
    }
}
