package cn.thinkjoy.gk.util;

import cn.thinkjoy.gk.pojo.UserAccountPojo;

/**
 * 用户上下文
 * <p/>
 * 创建时间: 14-9-1 下午11:21<br/>
 *
 * @author qyang
 * @since v0.0.1
 * @author Michael
 * @since v0.0.10
 */
public class UserContext {
    private static ThreadLocal<UserAccountPojo> context = new ThreadLocal<UserAccountPojo>();

    public static UserAccountPojo getCurrentUser(){
        return context.get();
    }

    public static void setCurrentUser(UserAccountPojo user){
        context.set(user);
    }

    public static void removeCurrentUser()
    {
        context.remove();
    }
}
