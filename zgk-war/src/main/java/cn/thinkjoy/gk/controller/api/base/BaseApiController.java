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
import java.util.Iterator;
import java.util.Map;


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


    /**
     * 数据校验
     * @param map 校验字段
     * @param dataMap 页面提交字段
     */
    public void paramCheck(Map<String,Object> map,Map<String,Object> dataMap){
        Iterator<String> iterator=map.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            if(!dataMap.containsKey(key)) {
                throw new BizException("error","缺少"+map.get(key)+","+key+"参数");
            }
        }
    }
}
