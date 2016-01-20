package cn.thinkjoy.gk.controller.api.base;

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
    protected T isNull(T o){
        if(o==null){
            throw new BizException(ERRORCODE.RESOURCEISNULL.getCode(),ERRORCODE.RESOURCEISNULL.getMessage());
        }
        return o;
    }
    protected void idIsNull(String o){
        if(o==null || "".equals(o)){
            throw new BizException(ERRORCODE.IDISNOTNULL.getCode(),ERRORCODE.IDISNOTNULL.getMessage());
        }
    }
}
