package cn.thinkjoy.gk.controller.api.base;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@Scope(SpringMVCConst.SCOPE)
public class BaseApiController<T> extends ZGKBaseController{

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
