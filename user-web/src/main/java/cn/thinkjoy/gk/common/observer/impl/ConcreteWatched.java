package cn.thinkjoy.gk.common.observer.impl;

import cn.thinkjoy.agents.IGoodsCheck;
import cn.thinkjoy.gk.common.observer.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by admin on 2016/3/19.
 */
@Component
public class ConcreteWatched implements Watcher{

    @Autowired
    private IGoodsCheck goodsCheck;
    @Override
    public void update(Map<String,Object> map) {
        goodsCheck.goodsCheck(map);
    }
}
