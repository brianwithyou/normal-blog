package com.brian.user.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.user.server.entity.Follower;
import com.brian.user.server.service.FollowerService;
import com.brian.user.server.mapper.FollowerMapper;
import org.springframework.stereotype.Service;

/**
* @author brian
*/
@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower>
    implements FollowerService{

}




