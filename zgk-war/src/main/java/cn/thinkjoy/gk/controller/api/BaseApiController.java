package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ERRORCODE;

/**
 * 开发平台api基类
 * <p/>
 * 创建时间: 15/7/25 下午3:11<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class BaseApiController<T> {
    protected String setDefault(String str,String defaultStr){
        if(str==null || "".equals(str)){
            str=defaultStr;
        }
        return str;
    }

    protected Integer setDefault(Integer num,Integer defaultNum){
        if(num==null){
            num=defaultNum;
        }
        return num;
    }
    protected boolean setDefault(Boolean num,boolean defaultBoo){
        if(num==null){
            num=defaultBoo;
        }
        return num;
    }
    protected T isNull(T o){
        if(o==null){
            throw new BizException(ERRORCODE.RESOURCEISNULL.getCode(),ERRORCODE.RESOURCEISNULL.getMessage());
        }
        return o;
    }
}
