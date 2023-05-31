package com.brian.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.admin.entity.Category;

/**
* @author Brian
* @description 针对表【t_category】的数据库操作Service
* @createDate 2023-05-28 18:26:31
*/
public interface CategoryService extends IService<Category> {

    boolean create(Category body);

    boolean delete(Category body);

}
