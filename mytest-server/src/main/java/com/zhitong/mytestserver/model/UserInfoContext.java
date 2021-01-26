package com.zhitong.mytestserver.model;


/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.filter
 * @Description:
 * @date Date : 2021年01月26日 14:38
 */
public class UserInfoContext {
    private static ThreadLocal<User> userInfo = new ThreadLocal<User>();

    public UserInfoContext() {
    }

    public static User getUser(){
        return (User) userInfo.get();
    }

    public static void setUser(User user){
        userInfo.set(user);
    }

    public static void clearUser(){
        userInfo.remove();
    }

}
