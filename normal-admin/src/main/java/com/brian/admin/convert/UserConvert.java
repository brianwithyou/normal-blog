package com.brian.admin.convert;

import com.brian.admin.vo.UserVO;
import com.brian.user.api.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    UserDTO convertDto(UserVO bean);

    List<UserDTO> convertVo(List<UserVO> bean);

//    List<UserDTO> convertDto(List<User> bean);

//    User convert(UserDTO user);
}
