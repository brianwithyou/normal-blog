package com.brian.base.api;

import com.brian.base.api.dto.MenuDTO;
import com.brian.common.constant.Const;
import com.brian.common.constant.base.BaseConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author : brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 菜单服务")
@FeignClient(name = BaseConst.API_CLIENT_NAME)
public interface MenuFeignClient {

    String PREFIX = Const.FEIGN_API_PREFIX + "/base/menu";

    @GetMapping(PREFIX + "/")
    @ApiOperation(value = "根据父节点获取菜单", notes = "备注：parentId必填")
    MenuDTO getMenuByParentId(@Valid @RequestParam Long parentId);


}
