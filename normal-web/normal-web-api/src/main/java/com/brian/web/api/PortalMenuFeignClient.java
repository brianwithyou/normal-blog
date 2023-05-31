package com.brian.web.api;

import com.brian.common.constant.Const;
import com.brian.common.constant.user.UserConst;
import com.brian.common.core.PageResult;
import com.brian.web.api.dto.MenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : Brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 门户菜单服务")
// 如果指定了server.servlet.context-path，那么一定要在path中配置前缀（就是context-path的值）
@FeignClient(name = UserConst.WEB_APP_NAME, path = "/web", contextId = "portal-menu")
public interface PortalMenuFeignClient {

    String PREFIX = Const.FEIGN_API_PREFIX + "/portal/menu";

    /**
     * /feign/blog/list
     */
    @GetMapping(PREFIX + "/list")
    @ApiOperation(value = "查询所有博客", notes = "备注：需要分页查询")
    List<MenuDTO> list();

    @RequestMapping("/menuPage")
    @ApiOperation(value = "分页查询门户菜单", notes = "备注：需要分页查询")
    PageResult<MenuDTO> page(@RequestParam String name, @RequestParam Integer pageNum, @RequestParam Integer pageSize);

    @RequestMapping("/create")
    @ApiOperation(value = "创建门户菜单", notes = "备注")
    void create(@RequestBody MenuDTO menu);
}
