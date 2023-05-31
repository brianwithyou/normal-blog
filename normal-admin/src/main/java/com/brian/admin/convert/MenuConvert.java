package com.brian.admin.convert;

import com.brian.admin.entity.Menu;
import com.brian.admin.vo.MenuVO;
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
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);


    Menu convert(Menu bean);
    @Mappings(
            @Mapping(source = "name", target = "title")
    )
    List<MenuVO> convertVo(List<Menu> bean);

//    List<UserDTO> convertDto(List<User> bean);

//    User convert(UserDTO user);
}
