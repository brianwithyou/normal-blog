package com.brian.web.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brian.web.server.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author brian
*/
@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

}




