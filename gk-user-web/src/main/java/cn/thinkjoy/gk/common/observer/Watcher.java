package cn.thinkjoy.gk.common.observer;

import java.util.Map;

/**
 * Created by admin on 2016/3/19.
 */
public interface Watcher {
    public void update(Map<String,Object> map);
}
