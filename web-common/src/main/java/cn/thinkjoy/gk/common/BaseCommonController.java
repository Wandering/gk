package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.DESUtil;
import cn.thinkjoy.gk.util.DomainUtil;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseCommonController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
							 HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	public String getAccoutId(){
		String uid = "0";
		if (request.getParameter("token") == null) {
			return uid;
		}
		try{
			String value = request.getParameter("token");
			String uInfo = DESUtil.decrypt(value, DESUtil.key);
			uid = DESUtil.getUserInfo(uInfo)[0];
		}catch (Exception e)
		{
			throw new BizException("error","The token is invalid!");
		}

		return uid;
	}
}