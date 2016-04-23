package cn.thinkjoy.gk.common;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by liusven on 16/1/21.
 */
@EnableAspectJAutoProxy
@Aspect
public class ResponseContentHolder {

    private static ThreadLocal<Object> responseContents = new ThreadLocal<>();

    public static ThreadLocal<Object> getResponseContents() {
        return responseContents;
    }

    public ResponseContentHolder()
    {

    }

    @AfterReturning(value="execution(* cn.thinkjoy.gk.controller..*(..))" +
            "&&@annotation(org.springframework.web.bind.annotation.ResponseBody)",
            returning = "responseContent")
    public void switchDBBack(Object responseContent)
    {
        responseContents.set(responseContent);
    }

}
