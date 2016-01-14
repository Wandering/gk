package cn.thinkjoy.gk.controller.api;

/**
 * 开发平台api基类
 * <p/>
 * 创建时间: 15/7/25 下午3:11<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class BaseApiController {
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
}
