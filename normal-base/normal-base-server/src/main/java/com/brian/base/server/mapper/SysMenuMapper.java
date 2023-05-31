package com.brian.base.server.mapper;

import com.brian.base.server.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author brian
*/
@Mapper
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}




