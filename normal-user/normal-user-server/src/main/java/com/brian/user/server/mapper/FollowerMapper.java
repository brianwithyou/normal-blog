package com.brian.user.server.mapper;

import com.brian.user.server.entity.Follower;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author brian
*/
@Mapper
@Repository
public interface FollowerMapper extends BaseMapper<Follower> {

}




