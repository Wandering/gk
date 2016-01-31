package cn.thinkjoy.gk.interceptor;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.UserAreaContext;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.constant.ServletPathConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.gk.util.UserContext;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	public int TOKEN_EXPIRE_TIME = 60*60;
	private static final Logger LOGGER= LoggerFactory.getLogger(LoginInterceptor.class);
	@Autowired
	private IUserAccountExService userAccountExService;

	public LoginInterceptor() { }


    @Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		UserAreaContext.setCurrentUserArea(request.getParameter("userKey"));
		String url = request.getServletPath();
		if(!ServletPathConst.MAPPING_URLS.contains(url)){
			return true;
		}

		LOGGER.info("url:"+url);

		String value = request.getParameter("token");

		LOGGER.info("cookie:"+value);
		String key = UserRedisConst.USER_KEY+value;

		boolean redisFlag = RedisUtil.getInstance().exists(key);
		LOGGER.info("redis is exists:"+ redisFlag);

		if (StringUtils.isEmpty(value)||!redisFlag) {
			throw new BizException("1000004","请先登录后再进行操作");
		}
		callWhenAuthenticationSuccess(key);
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

	private void callWhenAuthenticationSuccess(String key){

		UserContext.setCurrentUser(getUserAccountPojo(key));
	}

//	/**
//	 * 获取用户信息
//	 * @return
//	 */
//	protected UserAccountPojo getUserAccountPojo(HttpServletRequest request) {
//		UserAccountPojo userAccountBean  = null;
//		String uid="17";
//		userAccountBean = userAccountExService.findUserAccountPojoById(Long.parseLong(uid));
//		return userAccountBean;
//	}

	/**
	 * 获取用户信息
	 * @return
	 */
	protected UserAccountPojo getUserAccountPojo(String key) {
		UserAccountPojo userAccountBean  = null;
		userAccountBean = JSON.parseObject(RedisUtil.getInstance().get(key).toString(),UserAccountPojo.class);
		//对token进行延期
		store(key,userAccountBean);
		return userAccountBean;
	}

	/**
	 * 对token进行延期
	 * @param key
	 */
	public void store(String key,UserAccountPojo userAccountBean)
	{
		RedisUtil.getInstance().set(key,userAccountBean,TOKEN_EXPIRE_TIME,TimeUnit.SECONDS);
	}
}
