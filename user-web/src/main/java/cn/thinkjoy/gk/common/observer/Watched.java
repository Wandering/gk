package cn.thinkjoy.gk.common.observer;

import java.util.Map;

/**
 * Created by admin on 2016/3/19.
 */
public interface Watched {
    public void addWatcher(Watcher watcher);

    public void removeWatcher(Watcher watcher);

    public void notifyWatchers(Map<String,Object> map);
}
