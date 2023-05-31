package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.web.server.entity.Talk;
import com.brian.web.server.service.TalkService;
import com.brian.web.server.mapper.TalkMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_talk(说说)】的数据库操作Service实现
* @createDate 2023-05-21 17:16:57
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk>
    implements TalkService{

}




