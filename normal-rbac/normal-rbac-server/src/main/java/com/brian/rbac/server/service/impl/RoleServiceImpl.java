package com.brian.rbac.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.rbac.server.entity.Role;
import com.brian.rbac.server.service.RoleService;
import com.brian.rbac.server.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2023-05-24 14:57:38
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




