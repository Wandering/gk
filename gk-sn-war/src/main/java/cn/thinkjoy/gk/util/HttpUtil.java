package cn.thinkjoy.gk.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhwang on 15/6/29.
 */
public class HttpUtil {
    public static String getParameter(HttpServletRequest request,String param,String initVal){
        String paramVal = request.getParameter(param);
        if(StringUtils.isNotBlank(paramVal)){
            return paramVal;
        }else {
            return initVal;
        }
    }
}
