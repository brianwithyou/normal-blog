package com.brian.admin.service;

import com.brian.admin.vo.UserVO;
import com.brian.common.core.Result;

public interface UserService {

    public Result list(UserVO user);

    Result update(UserVO user);
}
