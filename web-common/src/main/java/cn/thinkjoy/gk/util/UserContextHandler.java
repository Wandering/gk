package cn.thinkjoy.gk.util;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/1/30.
 */
@Component
@Aspect
public class UserContextHandler {

    @Before("@annotation(cn.thinkjoy.gk.annotation.VipMethonTag)")
    public void isVip()
    {
        int status=UserContext.getCurrentUser().getVipStatus();
        if(status==1){
            return;
        }else if(status==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }else {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
    }
}
