package cn.thinkjoy.gk.protocol;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import cn.thinkjoy.common.exception.BizException;

/**
 * Created by yangguorong on 16/5/12.
 */
public class ModeUtil {

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

    /**
     * 抛出异常
     *
     * @param errorCode
     */
    public static void throwException(ERRORCODE errorCode){
        throw new BizException(errorCode.getCode(),errorCode.getMessage());
    }
}
