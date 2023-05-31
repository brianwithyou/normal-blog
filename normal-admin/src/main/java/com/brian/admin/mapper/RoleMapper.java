package com.brian.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brian.admin.entity.Role;

import java.util.List;

/**
* @author Brian
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2023-05-24 19:12:03
* @Entity com.brian.admin.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> listRolesByUserId(Long userId);
}




