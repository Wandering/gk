package cn.thinkjoy.gk.common;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.cloudstack.dynconfig.IChangeListener;
import cn.thinkjoy.cloudstack.dynconfig.domain.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/9/9 下午3:57<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Component
public class GlobalCrossHolder {
    private static String Origin ;

    @PostConstruct
    public void init() {
        try {
            Origin = DynConfigClientFactory.getClient().getConfig("cross", "url");
        } catch (Exception e) {
        }

        DynConfigClientFactory.getClient().registerListeners("cross", "url", new IChangeListener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(final Configuration configuration) {
                getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Origin = configuration.getConfig();

                    }
                });
            }
        });
    }

    public static String getOrigin() {
        return Origin;
    }
}
