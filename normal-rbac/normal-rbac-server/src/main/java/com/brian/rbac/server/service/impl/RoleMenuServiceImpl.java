package com.brian.rbac.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.rbac.server.entity.RoleMenu;
import com.brian.rbac.server.service.RoleMenuService;
import com.brian.rbac.server.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【sys_role_menu】的数据库操作Service实现
* @createDate 2023-05-24 14:50:51
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




