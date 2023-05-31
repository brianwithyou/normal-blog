package com.brian.gateway.filter;

import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * 全局过滤器 :用于鉴权(获取令牌 解析 判断)
 *
 * @author brian
 * @since 1.0
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "Authorization";

    private static final Set<String> URL_WHITELIST = new HashSet<>() {
        {
                    add("/");
                    add("/rbac-server/rbac/auth/login");
                    add("/rbac-server/rbac/auth/logout");
                    add("/rbac-server/rbac/auth/register");
                    add("/rbac-server/rbac/role/list");

                    add("/web-server/web/blog/list");
                    add("/web-server/web/blog/info");
                    add("/web-server/web/tag/list");
                    add("/web-server/web/rank/blogs");
                    add("/web-server/web/menu/list");
                    add("/web-server/web/comment/list");

                    add("/search-server/search/search");
//                    add("/web-server/web/content/list");

                    add("/logout");
                    add("/favicon.ico");
                    add("/area/**");
                    add("/file/down/**");
        }
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

//        if(request.getURI().getPath().startsWith("/rbac-server/rbac/auth/login")){
        if(URL_WHITELIST.contains(request.getURI().getPath())){
            return chain.filter(exchange);
        }

        // 分别从header cookie param中取出数据
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if(StrUtil.isEmpty(token)){
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(first!=null){
                token=first.getValue();
            }
        }

        if(StrUtil.isEmpty(token)){
            token= request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }

        if(StrUtil.isEmpty(token)){
            //4.4. 如果没有数据 结束.
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        if (!token.startsWith("bearer ") && !token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        //添加头信息 传递给 各个微服务
        request.mutate().header(AUTHORIZE_TOKEN,token);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
