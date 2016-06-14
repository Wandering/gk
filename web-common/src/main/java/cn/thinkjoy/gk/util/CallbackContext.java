package cn.thinkjoy.gk.util;


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
public class CallbackContext {
    private static ThreadLocal<String> callback = new ThreadLocal<String>();

    public static String getCallback(){
        return callback.get();
    }

    public static void setCallback(String call){
        callback.set(call);
    }

    public static void removeCallback(){
        callback.remove();
    }
}
