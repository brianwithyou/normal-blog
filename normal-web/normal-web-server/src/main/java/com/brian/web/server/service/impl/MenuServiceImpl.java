package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.web.server.entity.Menu;
import com.brian.web.server.service.MenuService;
import com.brian.web.server.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_web_menu(用户端菜单表)】的数据库操作Service实现
* @createDate 2023-05-19 13:17:07
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




