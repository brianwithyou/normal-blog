package com.brian.admin.controller;

import com.brian.admin.service.PortalService;
import com.brian.common.core.Result;
import com.brian.web.api.dto.MenuDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@RestController
@RequestMapping("/portal")
public class PortalController {

    @Resource
    private PortalService portalService;

    @RequestMapping("/menu/list")
    public Result listMenus() {

        return Result.success(portalService.listPortalMenus());
    }

    @RequestMapping("/menu/create")
    public Result create(@RequestBody MenuDTO menuDTO) {
        portalService.create(menuDTO);
        return Result.success();
    }

}
