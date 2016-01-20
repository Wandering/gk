package cn.thinkjoy.gk.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域操作拦截器（在服务器返回信息后处理）
 *
 * Created by gryang on 15-1-5.
 */
@Component
public class CrossFilter extends OncePerRequestFilter {

    private  String HTTP = "http://";

    @Autowired
    private GlobalCrossHolder globalCrossHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","*");
        response.addHeader("Access-Control-Max-Age","3600");
        response.addHeader("Access-Control-Allow-Credentials","false");
        response.addHeader("Access-Control-Allow-Headers: Content-Type","*");
        filterChain.doFilter(request, response);
    }

    private boolean checkReferer(String referer)
    {
        boolean hasDomain = false;
        String[] domains = GlobalCrossHolder.getOrigin().split("\r\n");
        for (String domain : domains)
        {
            if(domain.trim().equals(referer))
            {
                hasDomain = true;
                break;
            }
        }

        return hasDomain;
    }

    private   String getOriginUrl(String referer)
    {
        referer = referer.replace(HTTP,"");
        int endIndex = referer.indexOf("/");
        String domain = referer.substring(0,endIndex);
        return HTTP+domain;
    }

}
