package com.brian.web.server.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageResult;
import com.brian.web.api.PortalMenuFeignClient;
import com.brian.web.api.dto.MenuDTO;
import com.brian.web.server.convert.MenuConvert;
import com.brian.web.server.entity.Menu;
import com.brian.web.server.service.MenuService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PortalMenuFeignService implements PortalMenuFeignClient {

    @Resource
    private MenuService menuService;
    @Override
    public List<MenuDTO> list() {

        List<Menu> list = menuService.list(new QueryWrapper<Menu>()
                .ne("status", 0)
                .orderByDesc("sort")
        );
        return MenuConvert.INSTANCE.convertDto(list);
    }

    @Override
    public PageResult<MenuDTO> page(String name, Integer pageNum, Integer pageSize) {
        Page<Menu> page = new Page<>(pageNum, pageSize);

        Page<Menu> pageData = menuService.page(page, new QueryWrapper<Menu>()
                .like(name != null, "name", name));
        List<Menu> records = pageData.getRecords();
        List<MenuDTO> menuDTOS = MenuConvert.INSTANCE.convertDto(records);

        PageResult<MenuDTO> res = new PageResult<>();
        res.setTotal(pageData.getTotal());
        res.setPageSize(Long.valueOf(pageSize));
        res.setPageNum(Long.valueOf(pageNum));
        res.setList(menuDTOS);
        return res;
    }

    @Override
    public void create(MenuDTO menu) {
        Menu convert = MenuConvert.INSTANCE.convert(menu);
        menuService.saveOrUpdate(convert);

    }
}
