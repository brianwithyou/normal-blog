package com.brian.rbac.server.convert;

import com.brian.rbac.server.vo.UserVO;
import com.brian.user.api.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserVO convertVo(UserDTO bean);

    UserDTO convertDto(UserVO user);

//    List<UserDTO> convertDto(List<User> bean);

//    User convert(UserDTO user);
}
