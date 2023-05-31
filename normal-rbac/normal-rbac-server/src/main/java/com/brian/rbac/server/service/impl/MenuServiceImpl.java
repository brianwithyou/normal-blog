package com.brian.rbac.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.rbac.server.entity.Menu;
import com.brian.rbac.server.service.MenuService;
import com.brian.rbac.server.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2023-05-24 14:50:36
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




