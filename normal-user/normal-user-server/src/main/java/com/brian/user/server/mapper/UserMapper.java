package com.brian.user.server.mapper;

import com.brian.user.api.dto.UserStatisticDTO;
import com.brian.user.server.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author brian
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<UserStatisticDTO> getStatistic(@Param("id") Long id);
}




