package com.brian.user.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.user.server.entity.User;
import com.brian.user.server.mapper.UserMapper;
import com.brian.user.server.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author brian
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




