package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.web.server.entity.LeaveMessage;
import com.brian.web.server.service.LeaveMessageService;
import com.brian.web.server.mapper.LeaveMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t_leave_message(留言)】的数据库操作Service实现
* @createDate 2023-05-21 17:16:41
*/
@Service
public class LeaveMessageServiceImpl extends ServiceImpl<LeaveMessageMapper, LeaveMessage>
    implements LeaveMessageService{

}




