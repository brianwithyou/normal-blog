package com.brian.rbac.server.convert;

import com.brian.rbac.server.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    Menu convert(Menu bean);

//    List<UserDTO> convertDto(List<User> bean);

//    User convert(UserDTO user);
}
