package com.brian.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.admin.entity.Category;
import com.brian.admin.mapper.CategoryMapper;
import com.brian.admin.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_category】的数据库操作Service实现
* @createDate 2023-05-28 18:26:31
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Override
    public boolean create(Category body) {
        return saveOrUpdate(body);
    }

    @Override
    public boolean delete(Category body) {
        Category entity = new Category();
        entity.setId(body.getId());
        entity.setStatus(0);
        return updateById(entity);
    }
}




