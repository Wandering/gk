package cn.thinkjoy.gk.interceptor;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.AllowConst;
import cn.thinkjoy.gk.constant.ServletPathConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UploadInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER= LoggerFactory.getLogger(UploadInterceptor.class);

	public UploadInterceptor() { }


    @Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
//		System.out.println("===========HandlerInterceptor1 postHandle");

	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		System.out.println("===========HandlerInterceptor1 afterCompletion");
	}

}
