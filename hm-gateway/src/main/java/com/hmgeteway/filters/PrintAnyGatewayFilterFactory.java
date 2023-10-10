package com.hmgeteway.filters;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<PrintAnyGatewayFilterFactory.config> {
    @Override
    public GatewayFilter apply(config config) {

        return new OrderedGatewayFilter(
                new GatewayFilter() {
                    @Override
                    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                        log.info("Filtering+++++++++++++++++++++++++++++++++++++++++++++++");
                        log.info(config.getA()+"++++++++++++++"
                                +config.getB()+"++++"+
                                config.getC()+"+++++"
                        );
                        return chain.filter(exchange);
                    }
                },1
        );
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("a", "b", "c");
    }

    @Override
    public Class<config> getConfigClass() {
        return config.class;
    }

    @Data
    public static class config{
        private String a;
        private String b;
        private String c;
    }
}
