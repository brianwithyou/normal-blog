package com.brian.user.server.feign;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import com.brian.user.api.dto.UserStatisticDTO;
import com.brian.user.server.convert.UserConvert;
import com.brian.user.server.entity.User;
import com.brian.user.server.mapper.UserMapper;
import com.brian.user.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@RestController
public class UserFeignService implements UserFeignClient {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        return UserConvert.INSTANCE.convertDto(user);
    }

    @Override
    public UserDTO getUserByAccount(String account) {
        User user = userService.getOne(new QueryWrapper<User>().eq("account_number", account));
        return UserConvert.INSTANCE.convertDto(user);
    }

    //    @Override
    public boolean save(UserDTO user) {
        if (StringUtils.isEmpty(user.getAccountNumber())) {
            user.setAccountNumber(user.getUsername());
        }
        if (StringUtils.isEmpty(user.getNickname())) {
            user.setAccountNumber(user.getUsername());
        }
        User userEntity = UserConvert.INSTANCE.convert(user);

        String rawPwd = user.getPassword();
        String pwd = bCryptPasswordEncoder.encode(rawPwd);
        userEntity.setPassword(pwd);

        return userService.save(userEntity);
    }

    @Override
    public String testGetCurrentUser() {
        return "hello feign user";
    }

    @Override
    public Result register(UserDTO user) {
        User convert = UserConvert.INSTANCE.convert(user);
        if (StrUtil.isEmpty(user.getAccountNumber())) {
            convert.setAccountNumber(convert.getUsername());
        }
        if (StrUtil.isEmpty(convert.getNickname())) {
            convert.setAccountNumber(convert.getUsername());
        }
        if (StrUtil.isEmpty(convert.getAvatar())) {
            convert.setAvatar("https://p3-search.byteimg.com/img/labis/d1bb6ac4c5b405f3dcbadcadd19a9692~tplv-tt-cs0:336:276.webp");
        }
        User userExisted = userService.getOne(new QueryWrapper<User>()
                .eq("account_number", convert.getAccountNumber()));
        if (userExisted != null) {
            return Result.fail(GlobalErrorCodeEnum.REPEATED_USER);
        }


        String rawPwd = user.getPassword();
        String pwd = bCryptPasswordEncoder.encode(rawPwd);
        convert.setPassword(pwd);
        boolean save = userService.save(convert);
        return Result.success(save ?  "注册成功" : "注册失败");
    }

    @Override
    public List<UserDTO> listUsers() {
        List<User> userEntities = userService.list(new QueryWrapper<User>().eq("deleted", 0));
        return UserConvert.INSTANCE.convertDto(userEntities);
    }

    @Override
    public PageResult<UserDTO> page(UserDTO userDTO, Integer pageNum, Integer pageSize) {

        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> pageData = userService.page(page);
        List<User> records = pageData.getRecords();

        List<UserDTO> userDTOS = UserConvert.INSTANCE.convertDto(records);


        PageResult<UserDTO> res = new PageResult<>();
        res.setList(userDTOS);
        res.setTotal(pageData.getTotal());
        res.setPageNum(pageData.getCurrent());
        res.setPageSize(pageData.getSize());
        return res;
    }

    @Override
    public UserDTO getUserById(Long uid) {
        User byId = userService.getById(uid);
        return UserConvert.INSTANCE.convertDto(byId);
    }

    @Override
    public List<UserDTO> listByIds(List<Long> ids) {
//        List<User> users = userService.listByIds(ids);
        List<User> users = userService.list(new QueryWrapper<User>().ne("status", 0));
        return UserConvert.INSTANCE.convertDto(users);
    }

    @Override
    public void update(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        if (StrUtil.isNotBlank(userDTO.getAvatar())) {
            user.setAvatar(userDTO.getAvatar());
        }
        boolean b = userService.updateById(user);
        log.info("更新用户头像和昵称结果：{}", b);
    }

    @Override
    public List<UserStatisticDTO> statistic(Long id) {
        return userMapper.getStatistic(id);
    }
}
