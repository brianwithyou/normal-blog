package com.brian.admin.mapper;

import com.brian.admin.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Brian
* @description 针对表【t_category】的数据库操作Mapper
* @createDate 2023-05-28 18:26:31
* @Entity com.brian.admin.entity.Category
*/
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}




