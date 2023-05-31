package com.brian.web.api;

import com.brian.common.constant.Const;
import com.brian.common.constant.user.UserConst;
import com.brian.common.core.PageResult;
import com.brian.web.api.dto.BlogDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : Brian
 * @since 0.1
 */
@Api(tags = "RPC 服务 - 博客服务")
// 如果指定了server.servlet.context-path，那么一定要在path中配置前缀（就是context-path的值）
@FeignClient(name = UserConst.WEB_APP_NAME, path = "/web", contextId = "blog")
public interface BlogFeignClient {

    String PREFIX = Const.FEIGN_API_PREFIX + "/blog";

    /**
     * /feign/blog/list
     */
    @GetMapping(PREFIX + "/list")
    @ApiOperation(value = "查询所有博客", notes = "备注：需要分页查询")
    List<BlogDTO> list();

    @GetMapping(PREFIX + "/getById")
    @ApiOperation(value = "根据id查询博客", notes = "备注")
    BlogDTO getById(@RequestParam Long id);

    @RequestMapping("/page")
    @ApiOperation(value = "分页查询所有博客", notes = "备注：需要分页查询")
    PageResult<BlogDTO> page(@RequestParam String title, @RequestParam Long authorId, @RequestParam Integer pageNum, @RequestParam Integer pageSize);
}
