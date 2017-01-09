package cn.thinkjoy.gk.interceptor;

import cn.thinkjoy.gk.common.ResponseContentHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossInterceptor extends HandlerInterceptorAdapter {

    public CrossInterceptor()
    {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "1800");
        if("IE".equals(request.getParameter("browserType"))) {
            response.setContentType("text/plain");
            response.addHeader("XDomainRequestAllowed","1");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if("IE".equals(request.getParameter("browserType")))
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            response.addHeader("XDomainRequestAllowed","1");
//            System.out.println(ResponseContentHolder.getResponseContents().get());
        }
    }

}
