package cn.thinkjoy.gk.common;

import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/1/7.
 */
@Component
public class UserAreaContext {


    private static ThreadLocal<String> context = new ThreadLocal<String>();

    public static String getCurrentUserArea(){
        return context.get();
    }

    public static void setCurrentUserArea(String area){
        //缓存记录
//        SessionCacheFactory.getInstance().put(user.getName(), user);
        context.set(area);
    }

    /**
     * 应该显示调用
     */
    public static void removeCurrentUseraArea() {
        context.remove();
    }


//    private static IRedisRepository store;

//    @PostConstruct
//    private void initStore()
//    {
//        try {
//            store = RedisRepositoryFactory.getRepository("zgk", "user", "area");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getCurrentUserArea(String key) {
//        return store.get(key) + "";
//    }
//
//    public static void setCurrentUserArea(String key, String area) {
//        store.set(key, area);
//    }

}
