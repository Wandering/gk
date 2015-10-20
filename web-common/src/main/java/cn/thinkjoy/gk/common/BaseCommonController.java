package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.constant.DomainConst;
import cn.thinkjoy.gk.util.CookieUtil;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseCommonController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

//	protected HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

//	protected HttpServletResponse response =  ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	public String getDomainValue(){
		StringBuffer url = request.getRequestURL();
		if(url.indexOf(DomainConst.SN_DOMAIN)>-1){
			return DomainConst.SN_DOMAIN;
		} else if(url.indexOf(DomainConst.HN_DOMAIN)>-1){
			return DomainConst.HN_DOMAIN;
		} else if(url.indexOf(DomainConst.GX_DOMAIN)>-1){
			return DomainConst.GX_DOMAIN;
		} else if(url.indexOf(DomainConst.HA_DOMAIN)>-1){
			return DomainConst.HA_DOMAIN;
		} else {
			return null;
		}
	}

	public String getCookieValue(){
		String domainValue = getDomainValue();

		if(domainValue.equals(DomainConst.SN_DOMAIN)){
			return CookieUtil.getCookieValue(request.getCookies(), CookieConst.SN_USER_COOKIE_NAME);
		} else if(domainValue.equals(DomainConst.HN_DOMAIN)){
			return CookieUtil.getCookieValue(request.getCookies(), CookieConst.SN_USER_COOKIE_NAME);
		} else if(domainValue.equals(DomainConst.GX_DOMAIN)){
			return CookieUtil.getCookieValue(request.getCookies(), CookieConst.SN_USER_COOKIE_NAME);
		} else if(domainValue.equals(DomainConst.HA_DOMAIN)){
			return CookieUtil.getCookieValue(request.getCookies(), CookieConst.SN_USER_COOKIE_NAME);
		}
		return null;
	}

}