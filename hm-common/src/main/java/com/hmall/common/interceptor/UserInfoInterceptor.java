package com.hmall.common.interceptor;

import com.hmall.common.utils.UserContext;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.HandlerInterceptor;

public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //1.获取请求头中的用户
        String header = request.getHeader("user-info");
        //2.判断是否为空不为空 存入UserContext
        if (!Objects.isNull(header)) {
            UserContext.setUser(Long.valueOf(header));
        }
        //3.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除用户信息
        UserContext.removeUser();
    }


}
