package com.brian.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.admin.entity.Tag;
import com.brian.admin.vo.TagVO;

/**
* @author Brian
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2023-05-28 18:26:25
*/
public interface TagService extends IService<Tag> {

    boolean create(TagVO tag);

    boolean delete(TagVO tag);

}
