package com.brian.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.admin.convert.MenuConvert;
import com.brian.admin.entity.Menu;
import com.brian.admin.mapper.MenuMapper;
import com.brian.admin.service.MenuService;
import com.brian.admin.vo.MenuVO;
import com.brian.common.core.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author Brian
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2023-05-24 19:11:57
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public Result listMenus() {
        return Result.success(treefiy());
    }
    @Override
    public List<MenuVO> treefiy() {
        // 获取所有菜单信息
        List<Menu> Menus = this.list(new QueryWrapper<Menu>().orderByAsc("order_num"));

        // 转成树状结构
        return buildTreeMenu(Menus);
    }

    private List<MenuVO> buildTreeMenu(List<Menu> menus) {

        List<MenuVO> finalMenus = new ArrayList<>();
        List<MenuVO> menuVOList = MenuConvert.INSTANCE.convertVo(menus);
        for (MenuVO menu : menuVOList) {
            for (MenuVO e : menuVOList) {
                if (Objects.equals(menu.getId(), e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        return finalMenus;
    }
}




