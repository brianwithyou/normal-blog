package com.brian.admin.convert;

import com.brian.admin.entity.Role;
import com.brian.admin.vo.MenuVO;
import com.brian.admin.vo.RoleVO;
import com.brian.user.api.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);


    Role convert(RoleVO bean);

    List<MenuVO> convertVo(List<Role> bean);

    List<RoleDTO> convertDto(List<Role> bean);

    RoleDTO convertDto(Role bean);

//    List<UserDTO> convertDto(List<User> bean);

//    User convert(UserDTO user);
}
