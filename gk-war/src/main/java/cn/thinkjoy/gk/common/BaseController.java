package cn.thinkjoy.gk.common;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.gk.constant.CookieTimeConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		if(!RedisUtil.getInstance().exists(key)){
			userAccountBean = userAccountExService.findUserAccountPojoById(id);
			if(null!=userAccountBean){
				RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 4L, TimeUnit.HOURS);
			}
		} else{
			userAccountBean = JSON.parseObject(RedisUtil.getInstance().get(key).toString(),UserAccountPojo.class);
		}
		return userAccountBean;
	}

	protected void setUserAccountPojo(UserAccountPojo userAccountBean) throws Exception {
		if(null!=userAccountBean){
			String key = UserRedisConst.USER_KEY + userAccountBean.getId();
			if(RedisUtil.getInstance().exists(key)){
				RedisUtil.getInstance().del(key);
			}
			RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 4L, TimeUnit.HOURS);
		}
	}

	protected Long getAreaCookieValue() throws Exception {

		String areaId = CookieUtil.getCookieValue(request.getCookies(), CookieConst.AREA_COOKIE_NAME);

		if(StringUtils.isEmpty(areaId)){

			areaId = "0";

			String domain = DynConfigClientFactory.getClient().getConfig("login", "domain");

			response.addCookie(CookieUtil.addCookie(domain,CookieConst.AREA_COOKIE_NAME, areaId, CookieTimeConst.DEFAULT_COOKIE));
		}

		return Long.valueOf(areaId);

	}

}