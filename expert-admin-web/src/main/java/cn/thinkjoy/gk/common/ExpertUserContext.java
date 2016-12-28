package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.pojo.ExpertUserDTO;

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
public class ExpertUserContext {
    private static ThreadLocal<ExpertUserDTO> context = new ThreadLocal<ExpertUserDTO>();

    public static ExpertUserDTO getCurrentUser(){
        return context.get();
    }

    public static void setCurrentUser(ExpertUserDTO user){
        context.set(user);
    }

    public static void removeCurrentUser()
    {
        context.remove();
    }
}
