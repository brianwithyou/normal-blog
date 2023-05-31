package com.brian.rbac.server.mapper;

import com.brian.rbac.server.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Brian
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2023-05-24 14:50:36
* @Entity com.brian.rbac.server.entity.Menu
*/
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

}




