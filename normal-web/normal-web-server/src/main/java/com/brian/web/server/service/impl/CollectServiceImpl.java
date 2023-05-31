package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.web.server.entity.Collect;
import com.brian.web.server.service.CollectService;
import com.brian.web.server.mapper.CollectMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_collect(用户收藏表)】的数据库操作Service实现
* @createDate 2023-05-20 18:01:29
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{

}




