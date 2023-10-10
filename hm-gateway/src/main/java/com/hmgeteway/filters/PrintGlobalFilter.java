package com.hmgeteway.filters;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PrintGlobalFilter implements GlobalFilter, Ordered{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Printing global filter+++++++++++++++");
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }
}
