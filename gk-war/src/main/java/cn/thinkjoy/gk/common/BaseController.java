package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

public class BaseController{

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

//	protected HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

//	protected HttpServletResponse response =  ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();

	@Autowired
	private IUserAccountExService userAccountExService;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	protected String getCookieValue() {
		return CookieUtil.getCookieValue(request.getCookies(), CookieConst.USER_COOKIE_NAME);
	}

	protected UserAccountPojo getUserAccountPojo() {
		Long id = Long.valueOf(getCookieValue());
		String key = UserRedisConst.USER_KEY + id;
		UserAccountPojo userAccountBean  = null;
		RedisUtil.getInstance().del(key);
		if(!RedisUtil.getInstance().exists(key)){
			userAccountBean = userAccountExService.findUserAccountPojoById(id);
			if(null!=userAccountBean){
				RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 10L, TimeUnit.MINUTES);

			}
		} else{
			userAccountBean = JSON.parseObject(RedisUtil.getInstance().get(key).toString(),UserAccountPojo.class);
		}
		return userAccountBean;
	}

	protected void setUserAccountPojo(UserAccountPojo userAccountBean) throws Exception {
		String key = UserRedisConst.USER_KEY + userAccountBean.getId();
		if(null!=userAccountBean){
			RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean));
		}
	}

}