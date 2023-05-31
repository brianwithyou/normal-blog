package com.brian.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.admin.entity.Role;
import com.brian.admin.mapper.RoleMapper;
import com.brian.admin.mapper.UserRoleMapper;
import com.brian.admin.service.RoleService;
import com.brian.admin.util.ContextUtil;
import com.brian.user.api.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Brian
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2023-05-24 19:12:03
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    private UserRoleMapper userRoleMapper;
    @Override
    public List<Role> listCurRoles() {

        UserDTO currentUser = ContextUtil.getCurrentUser();
        Long userId = currentUser.getId();
//        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
//                .eq(userId != null, UserRole::getUserId, userId));

        return roleMapper.listRolesByUserId(userId);
    }
}




