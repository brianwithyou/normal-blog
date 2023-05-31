package com.brian.rbac.server.mapper;

import com.brian.rbac.server.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Brian
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2023-05-24 14:57:38
* @Entity com.brian.rbac.server.entity.Role
*/
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}




