package com.brian.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 请求路径
        String requestPath = request.getPath().pathWithinApplication().value();

        Route route = getGatewayRoute(exchange);


        String hostAddress = request.getRemoteAddress().getAddress().getHostAddress();
        String hostname = request.getRemoteAddress().getHostName();


        String scheme = request.getURI().getScheme();
        String requestMethod = request.getMethodValue();
        String targetServer = route.getId();
        URI targetUri = route.getUri();
        LocalDateTime now = LocalDateTime.now();
        log.info("schema: {}, requestMethod: {}, requestPath: {}, targetServer: {}, requestTime: {}, hostAddress: {}" +
                        ", hostname: {}, targetUri: {}"
                , scheme, requestMethod, requestPath, targetServer, now, hostAddress, hostname, targetUri);
        // TODO 日志持久化
        return chain.filter(exchange);
    }
    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }

}
