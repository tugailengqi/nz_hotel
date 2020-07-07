package com.lengqi.aop;

import com.lengqi.entity.User;

public class LoginParams {
    //jvm进程中只有一份，方法区（元空间）
    private static ThreadLocal<User> user = new ThreadLocal<>();

    public static User getUser() {
        return user.get();
    }

    public static void setUser(User user) {
        LoginParams.user.set(user);
    }
}
