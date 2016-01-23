package cn.thinkjoy.gk.interceptor;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.constant.CookieTimeConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.ss.api.IUserAccountService;
import cn.thinkjoy.ss.api.IUserInfoService;
import cn.thinkjoy.ss.api.bean.UserInfoBean;
import cn.thinkjoy.ss.bean.war.UserAccountBean;
import cn.thinkjoy.ss.domain.UserInfo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
		if(url.indexOf("/question/insert.do")>-1||url.indexOf("/answer/findMyQuestion.do")>-1){

			String value = request.getParameter("token");

			String uInfo = DESUtil.decrypt(value, DESUtil.key);

			String uid = DESUtil.getUserInfo(uInfo)[0];

			UserAccountBean userAccountBean = userAccountService.findUserAccountBeanByToken(uid,7);

			if(userAccountBean==null){

				UserAccountPojo userAccountPojo = userAccountExService.findUserAccountPojoById(Long.valueOf(uid));

				UserInfoBean userInfoBean = new UserInfoBean();

				userInfoBean.setToken(uid);

				userInfoBean.setName(userAccountPojo.getName());

				userInfoBean.setIcon(userAccountPojo.getIcon());

				userInfoBean.setPhone(userAccountPojo.getAccount());

				userInfoBean.setSourceType(7);

				long id = userInfoService.insertUserInfo(userInfoBean);

				if(id==0){
					throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
				}

			} else {

				String key = UserRedisConst.USER_KEY + value;

				UserAccountPojo userAccountPojo = JSON.parseObject(RedisUtil.getInstance().get(key).toString(), UserAccountPojo.class);

				List<String> one = new ArrayList<>();

				one.add(userAccountPojo.getName());
				one.add(userAccountPojo.getIcon());

				List<String> two = new ArrayList<>();

				two.add(userAccountBean.getName());
				two.add(userAccountBean.getIcon());

				if(one.hashCode()!=two.hashCode()){
					UserInfo userInfo = new UserInfo();

					userInfo.setId(userAccountBean.getId());
					userInfo.setName(userAccountPojo.getName());
					userInfo.setIcon(userAccountPojo.getIcon());

					userInfoService.updateUserInfo(userInfo);

				}
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
