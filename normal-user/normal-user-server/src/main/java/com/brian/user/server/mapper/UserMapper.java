package com.brian.user.server.mapper;

import com.brian.user.server.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author brian
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}




