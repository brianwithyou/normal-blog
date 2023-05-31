package com.brian.user.server.convert;

import com.brian.user.api.dto.UserDTO;
import com.brian.user.server.entity.User;
import com.brian.user.server.vo.UserCreateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mappings(
            @Mapping(source = "account", target = "accountNumber")
    )
    User convert(UserCreateVO bean);

    UserDTO convertDto(User bean);


    List<UserDTO> convertDto(List<User> bean);

    User convert(UserDTO user);
}
