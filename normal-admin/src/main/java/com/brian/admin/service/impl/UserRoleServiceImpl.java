package com.brian.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.admin.entity.UserRole;
import com.brian.admin.service.UserRoleService;
import com.brian.admin.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【sys_user_role】的数据库操作Service实现
* @createDate 2023-05-26 11:05:13
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




