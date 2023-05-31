package com.brian.web.server.convert;

import com.brian.web.api.dto.MenuDTO;
import com.brian.web.server.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    MenuDTO convertDto(Menu bean);
    List<MenuDTO> convertDto(List<Menu> bean);

    Menu convert(MenuDTO bean);
}
