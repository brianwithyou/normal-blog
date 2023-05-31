package com.brian.admin.mapper;

import com.brian.admin.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Brian
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2023-05-24 19:11:57
* @Entity com.brian.admin.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




