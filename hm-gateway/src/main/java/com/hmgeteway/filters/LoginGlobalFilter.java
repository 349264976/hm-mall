package com.hmgeteway.filters;

import com.hmall.common.exception.UnauthorizedException;
import com.hmgeteway.config.AuthProperties;
import com.hmgeteway.utils.JwtTool;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.*;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginGlobalFilter implements GlobalFilter, Ordered {
    //    @Autowired
    private final AuthProperties authProperties;
    private final PathMatcher pathMatch =new AntPathMatcher();
    private final JwtTool jwtTool;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1.获取请求request
        ServerHttpRequest request = exchange.getRequest();
        //2.获取token
        List<String> heads = request.getHeaders().get("authorization");
        //3.判断当前请求是否被拦截

        if (isAllowpath(request)) {
            //不需要拦截的路径
            return chain.filter(exchange);
        }

        if (heads == null || heads.isEmpty()) {
            throw new UnauthorizedException("Invalid authorization");
        }
        //4.需要拦截解析token
        String token = heads.get(0);
        Long userid=null;
        if (token != null) {

            try {
                userid= jwtTool.parseToken(token);
            } catch (Exception e) {
                ServerHttpResponse response= (ServerHttpResponse) exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                throw  new UnauthorizedException ("解析失败");
            }

        }
        String useridString = userid.toString();

        //5.传递用户信息 到下游服务
        ServerWebExchange webExchange = exchange.mutate().request(x -> x.header("user-info", useridString)).build();
        //6.放行
        return chain.filter(webExchange);
    }

    private boolean isAllowpath(ServerHttpRequest request) {

        //获取当前路径
        boolean flag = false;
        String path = request.getPath().toString();
        String methodValue = request.getMethodValue();
        //2.读取要放行的路径
        List<String> excludePaths = authProperties.getExcludePaths();
//        boolean contains = excludePaths.contains(path);
        flag = excludePaths.stream().anyMatch(x -> {
            boolean match = pathMatch.match(x, path);
//            boolean match = pathMatch.match(x, methodValue + ":" + path);
//            boolean equals = x.equals(path);
            return match;
        });
        return flag;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
