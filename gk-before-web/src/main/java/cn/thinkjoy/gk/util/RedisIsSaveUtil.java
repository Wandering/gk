package cn.thinkjoy.gk.util;

/**
 * Created by duanjiashu on 16/2/18.
 */
public class RedisIsSaveUtil {
    public static Object existsKey(String key){
        Object object =null;
        Boolean flag=false;
        flag=RedisUtil.getInstance().exists(key);
        if(flag){
            object=RedisUtil.getInstance().get(key);
        }
        return object;
    }
}
