package com.brian.rbac.server.mapper;

import com.brian.rbac.server.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Brian
* @description 针对表【sys_role_menu】的数据库操作Mapper
* @createDate 2023-05-24 14:50:51
* @Entity com.brian.rbac.server.entity.RoleMenu
*/
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}




