package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.web.server.entity.Tag;
import com.brian.web.server.service.TagService;
import com.brian.web.server.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author brian
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-05-19 11:30:21
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




