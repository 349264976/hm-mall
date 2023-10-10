package com.hmall.common.annotation;

import com.hmall.common.config.MvcConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MvcConfig.class)
public @interface EnableUserinterceptor {
}
