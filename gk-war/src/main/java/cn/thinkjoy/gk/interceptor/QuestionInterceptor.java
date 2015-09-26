package cn.thinkjoy.gk.interceptor;

import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.ss.api.IUserAccountService;
import cn.thinkjoy.ss.api.IUserInfoService;
import cn.thinkjoy.ss.api.bean.UserInfoBean;
import cn.thinkjoy.ss.bean.war.UserAccountBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER= LoggerFactory.getLogger(QuestionInterceptor.class);

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IUserAccountExService userAccountExService;

	@Autowired
	private IUserInfoService userInfoService;

	public QuestionInterceptor() { }


    @Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url = request.getServletPath();
		if(url.indexOf("/question/")>-1||url.indexOf("/answer/")>-1){

			String value = CookieUtil.getCookieValue(request.getCookies(), CookieConst.USER_COOKIE_NAME);

			UserAccountBean userAccountBean = userAccountService.findUserAccountBeanByToken(value);

			if(userAccountBean==null){

				UserAccountPojo userAccountPojo = userAccountExService.findUserAccountPojoById(Long.valueOf(value));

				UserInfoBean userInfoBean = new UserInfoBean();

				userInfoBean.setToken(value);

				userInfoBean.setName(userAccountPojo.getName());

				userInfoBean.setIcon(userAccountPojo.getIcon());

				userInfoBean.setPhone(userAccountPojo.getAccount());

				userInfoBean.setSourceType(7);

				userInfoService.insertUserInfo(userInfoBean);
			}
		}

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
