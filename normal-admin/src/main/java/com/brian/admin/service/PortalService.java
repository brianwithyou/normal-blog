package com.brian.admin.service;

import com.brian.web.api.dto.MenuDTO;

import java.util.List;

public interface PortalService {
    List<MenuDTO> listPortalMenus();

    void create(MenuDTO menu);
}
