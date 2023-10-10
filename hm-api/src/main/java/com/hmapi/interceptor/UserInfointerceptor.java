package com.hmapi.interceptor;

import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Objects;

public class UserInfointerceptor  implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String userId = UserContext.getUser().toString();
         if (Objects.nonNull(userId)){
             requestTemplate.header("user-info", userId);
         }
    }
}
