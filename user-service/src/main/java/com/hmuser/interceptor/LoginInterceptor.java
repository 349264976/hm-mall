package com.hmuser.interceptor;

import com.hmall.common.utils.UserContext;

import com.hmuser.utils.JwtTool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtTool jwtTool;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


//        String requestURI = request.getRequestURI();

        // 判断需要不拦截的路径，例如登录页面、注册页面等
//        if (requestURI.equals("/login") ) {
//            return true; // 不拦截，继续向下执行
//        }
        // 1.获取请求头中的 token
        String token = request.getHeader("authorization");
        // 2.校验token
        Long userId = jwtTool.parseToken(token);
        // 3.存入上下文
        UserContext.setUser(userId);
        // 4.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户
        UserContext.removeUser();
    }
}
