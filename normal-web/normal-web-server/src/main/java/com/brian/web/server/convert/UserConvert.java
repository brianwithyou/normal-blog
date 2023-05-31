package com.brian.web.server.convert;

import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    
    UserDTO convertDto(UserVO userVo);

    @Mappings({
            @Mapping(source = "accountNumber", target = "account"),
            @Mapping(source = "avatar", target = "avatar"),
            @Mapping(target = "password", ignore = true),
    })
    UserVO convertVo(UserDTO userDTO);

    UserVO convertVo(com.brian.rbac.api.dto.UserDTO userDTO);
}
