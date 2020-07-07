package com.lengqi.aop;

import java.lang.annotation.*;

// 标记一些元注解 - 标记在注解上的注解
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  IsLogin {
    //设置注解的方法,
    //是否需要强制登录，默认不需要强制登录
    boolean mustlogin() default false;
}
