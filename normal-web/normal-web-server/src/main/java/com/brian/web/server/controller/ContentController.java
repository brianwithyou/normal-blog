package com.brian.web.server.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.brian.common.core.Result;
import com.brian.web.server.vo.BlogVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web端内容管理
 * 负责 我的博客 的搜索筛选
 * 根据时间搜索、tag搜索、关键词搜索
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @RequestMapping("/list")
    public Result<?> list(BlogVO blog) {

        OAuth2AuthenticationDetails tokenDetail = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String tokenValue = tokenDetail.getTokenValue();

        JWT jwt = JWTUtil.parseToken(tokenValue);
        String username = (String) jwt.getPayload().getClaim("username");

        return Result.success();

    }

}
