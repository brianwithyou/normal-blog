package com.brian.base.server.convert;


import com.brian.base.api.dto.MenuDTO;
import com.brian.base.server.entity.SysMenu;
import com.brian.base.server.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    /**
     * entity -> vo
     * @return res
     */
    List<MenuVO> convertVOList(List<SysMenu> menus);

    MenuDTO convertDTO(SysMenu menu);
}
