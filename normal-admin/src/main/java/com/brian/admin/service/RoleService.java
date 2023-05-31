package com.brian.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.admin.entity.Role;

import java.util.List;

/**
* @author Brian
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2023-05-24 19:12:03
*/
public interface RoleService extends IService<Role> {

    List<Role> listCurRoles();
}
