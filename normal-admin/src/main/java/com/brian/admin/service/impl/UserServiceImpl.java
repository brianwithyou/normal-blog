package com.brian.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.admin.convert.RoleConvert;
import com.brian.admin.convert.UserConvert;
import com.brian.admin.entity.Role;
import com.brian.admin.entity.UserRole;
import com.brian.admin.service.RoleService;
import com.brian.admin.service.UserRoleService;
import com.brian.admin.service.UserService;
import com.brian.admin.vo.UserVO;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.RoleDTO;
import com.brian.user.api.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Override
    public Result list(UserVO user) {
        UserDTO userDTO = UserConvert.INSTANCE.convertDto(user);
        PageResult<UserDTO> page = userFeignClient.page(userDTO, user.getPageNum(), user.getPageSize());

        List<UserDTO> users = page.getList();
        List<Long> userIds = users.stream().map(UserDTO::getId).collect(Collectors.toList());
        Map<Long, Role> roleMap = new HashMap<>();
        List<Role> roles = roleService.list(new QueryWrapper<Role>().ne("status", 0));
        roles.forEach(item -> {
            roleMap.put(item.getId(), item);
        });

        for (UserDTO dto : users) {
            List<UserRole> userRole = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", dto.getId()));
            List<RoleDTO> roleList = new ArrayList<>();
            for (UserRole role : userRole) {
                Role roleItem = roleMap.get(role.getRoleId());
                roleList.add(RoleConvert.INSTANCE.convertDto(roleItem));
            }
            dto.setRoles(roleList);
        }
        return Result.success(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(UserVO user) {

        if (CollectionUtil.isNotEmpty(user.getRoleIds())) {
            boolean remove = userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
            List<UserRole> userRoles = new ArrayList<>();
            Long userId = user.getId();
            user.getRoleIds().forEach(item -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(item);

                userRoles.add(userRole);
            });
            boolean b = userRoleService.saveOrUpdateBatch(userRoles);
            log.info("更新用户角色结果：{}", b);
        }
        UserDTO userDTO = UserConvert.INSTANCE.convertDto(user);
        if (StrUtil.isNotBlank(user.getNickname())) {
            userFeignClient.update(userDTO);
        }
        return Result.success();
    }

}
