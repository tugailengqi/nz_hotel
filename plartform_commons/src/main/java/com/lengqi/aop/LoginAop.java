package com.lengqi.aop;

import com.lengqi.entity.ResultData;
import com.lengqi.entity.User;
import com.lengqi.util.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切面类必须被扫描到
 */
@Aspect //声明切面类
@Component
public class LoginAop {
    /**
     * 实现增强
     * 1.前置增强
     * 2、后置增强
     * 3、环绕增强
     * 4、后置完成增强
     * 5、异常增强
     */
    //增强的方法返回值什么类型，该方法就是什么类型，增强只是对目标方法的增强，
    // 而不是改变本质


    @Around("@annotation(IsLogin)") //环绕
    public ResultData<?> isLogin(ProceedingJoinPoint joinPoint){
        //前置增强
        //获得请求参数的jwtToken
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .getRequest();
        String jwtToken = request.getParameter("jwtToken");
        User user = null;
        if (jwtToken!=null) {
            //有jwtToken令牌，验证jwt令牌
             user = JwtUtil.parseJwtToken(jwtToken);
        }
        //验证用户对象是否为空，如果是空表示未登录或登陆过期
        if (user == null) {

            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            if (isLogin.mustlogin()) {
                //表示需要强制登录
                return new ResultData<>().setCode(ResultData.Code.CODE_RELOGIN);
            }
        }

        //用户信息以及获取
        //用户信息为空
        //加入目标方法需要user，怎么传递user过去？

        //方式1
//        Object[] args = joinPoint.getArgs();
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].getClass() == User.class){
//                args[i] = user;
//            }
//        }

        //方式2
        LoginParams.setUser(user);

        //调用目标方法
        ResultData resultData = null;
        try {
            //调用目标方法
            //如果有很多方法都加了IsLogin注解，那么调用哪个方法，得看请求了controller哪个方法
             resultData = (ResultData) joinPoint.proceed();
             //后置增强
//            System.out.println("后置增强了。。。");
        } catch (Throwable throwable) {
            //异常增强
//            System.out.println("出问题了。。。");
            throwable.printStackTrace();

        }
        //后置完成增强
//        System.out.println("不管出不出问题都完成了。。。");
        return resultData;
    }
}
