package com.brian.admin.service;

import com.brian.admin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.admin.vo.MenuVO;
import com.brian.common.core.Result;

import java.util.List;

/**
 * @author Brian
 * @description 针对表【sys_menu】的数据库操作Service
 * @createDate 2023-05-24 19:11:57
 */
public interface MenuService extends IService<Menu> {

    Result listMenus();

    List<MenuVO> treefiy();


}
