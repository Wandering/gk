package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import cn.thinkjoy.common.exception.BizException;

/**
 * Created by yangguorong on 16/4/14.
 */
public class ModelUtil {

    /**
     * 初始化对象
     *
     * @param domain
     */
    public static void initBuild(CreateBaseDomain domain) {
        domain.setStatus(0);
        domain.setCreator(0l);
        domain.setCreateDate(System.currentTimeMillis());
        domain.setLastModifier(0l);
        domain.setLastModDate(System.currentTimeMillis());
    }

}
