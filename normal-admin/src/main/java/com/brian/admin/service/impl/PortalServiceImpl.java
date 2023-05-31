package com.brian.admin.service.impl;

import com.brian.admin.service.PortalService;
import com.brian.web.api.PortalMenuFeignClient;
import com.brian.web.api.dto.MenuDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Service
public class PortalServiceImpl implements PortalService {

    @Resource
    private PortalMenuFeignClient portalMenuFeignClient;

    @Override
    public List<MenuDTO> listPortalMenus() {
        return portalMenuFeignClient.list();
    }

    @Override
    public void create(MenuDTO menu) {
        portalMenuFeignClient.create(menu);
    }
}
