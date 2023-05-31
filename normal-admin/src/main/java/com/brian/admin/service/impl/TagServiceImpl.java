package com.brian.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.admin.convert.TagConvert;
import com.brian.admin.entity.Tag;
import com.brian.admin.mapper.TagMapper;
import com.brian.admin.service.TagService;
import com.brian.admin.vo.TagVO;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-05-28 18:26:25
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public boolean create(TagVO tag) {
        Tag tagEntity = TagConvert.INSTANCE.convert(tag);
        return saveOrUpdate(tagEntity);
    }

    @Override
    public boolean delete(TagVO tag) {
        Tag tagEntity = new Tag();
        tagEntity.setId(tag.getUid());
        tagEntity.setStatus(0);
        return updateById(tagEntity);
    }
}




