package cn.thinkjoy.gk.filter;

import cn.thinkjoy.gk.common.CookieUtil;
import cn.thinkjoy.gk.common.ExpertUserContext;
import cn.thinkjoy.gk.constant.ExpertAdminConst;
import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangguorong on 16/10/17.
 */
public class ExpertAdminLoginFilter implements Filter {

    private static final Logger logger = Logger.getLogger(ExpertAdminLoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();

        logger.info("user request path : " + path);

        for (int i = 0; i < ExpertAdminConst.NoFilter_Pages.length; i++) {
            Pattern pattern = Pattern.compile(ExpertAdminConst.NoFilter_Pages[i]);
            Matcher matcher = pattern.matcher(path);
            if (matcher.matches()) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        ExpertUserDTO userInfoDto = null;
        String userInfoDtoStr = CookieUtil.getCookieValueByName(servletRequest, ExpertAdminConst.USER_COOKIE_KEY);
        try {
            userInfoDto = JSON.parseObject(URLDecoder.decode(userInfoDtoStr,ExpertAdminConst.CHARSET), ExpertUserDTO.class);
        }catch (Exception e){}

        // 判断如果没有取到员工信息,就跳转到登陆页面
        if (userInfoDto == null) {
            // 须重定向至登陆页面
            servletResponse.sendRedirect(ExpertAdminConst.LOGIN_PATH);
        } else {
            //设置用户上下文
            ExpertUserContext.setCurrentUser(userInfoDto);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
