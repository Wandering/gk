package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.GkxtUtil;
import cn.thinkjoy.gk.common.HttpClientUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserInfoPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.IUserAccountExService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/login")
public class LoginController extends ZGKBaseController {

	private static final Logger LOGGER= LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private IUserAccountExService userAccountExService;
	//高考学堂注册接口
	private String gkxtRegistUrl = "http://xuetang.zhigaokao.cn/userapi/reg?mobile=%s&password=%s";

	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam(value="account",required=false) String account,
					  @RequestParam(value="password",required=false) String password,
					  @RequestParam(value="basePassword",required = false) String basePassword,
		              @RequestParam(value = "userId", required = false) String userId,
		              @RequestParam(value = "aliUserId", required = false) String aliUserId) throws Exception {
		long id = 0L;
		UserInfoPojo userInfoPojo=null;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (StringUtils.isEmpty(account)) {
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
			}
			if (StringUtils.isEmpty(password)) {
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
			}

			UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
			if(userAccountBean==null){
				ModelUtil.throwException(ERRORCODE.LOGIN_ACCOUNT_NO_EXIST);
			}else {
				if (!password.equals(userAccountBean.getPassword())) {
					throw new BizException(ERRORCODE.LOGIN_PASSWORD_ERROR.getCode(), ERRORCODE.LOGIN_PASSWORD_ERROR.getMessage());
				}
				if (userAccountBean.getStatus() != 0) {
					throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "用户状态异常，请联系管理员!");
				}
				id = userAccountBean.getId();
				userInfoPojo=userAccountExService.getUserInfoPojoById(id);
			}

			if(null != userInfoPojo)
			{
				// 支付宝登陆
				if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(aliUserId))
				{
					long userIdLong = Long.parseLong(userId);
					UserAccountPojo userInfo = userAccountExService.findUserAccountPojoById(userIdLong);
					if(!userInfo.getAccount().equals(aliUserId))
					{
						throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "参数错误");
					}
					try
					{
						boolean flag = userAccountExService.bindUserAccountExist(userAccountBean, userId, aliUserId);
						if (!flag)
						{
							throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "账户绑定失败");
						}
					}
					catch (Exception e)
					{
						throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "账户绑定失败");
					}
				}
				/**
				 * 老用户生成二维码
				 */
				if(null == userInfoPojo.getAccountId() && null == userInfoPojo.getQrCodeUrl())
				{
					userAccountExService.insertUserMarketInfo(0L, 0 , id);
					userInfoPojo=userAccountExService.getUserInfoPojoById(id);
				}
				/**
				 * 判断VIP用户是否失效
				 */
				if("1".equals(userInfoPojo.getVipStatus()))
				{
					if(null != userInfoPojo.getEndDate() && System.currentTimeMillis() > Long.parseLong(userInfoPojo.getEndDate()))
					{
						userInfoPojo.setVipStatus("0");
					}
				}
				String token = DESUtil.getEightByteMultypleStr(String.valueOf(id), userInfoPojo.getAccount());
				String encryptToken = DESUtil.encrypt(token, DESUtil.key);
				setUserAccountPojo(userAccountBean, encryptToken);
				resultMap.put("token", encryptToken);
				String gkxtToken = GkxtUtil.getLoginToken(userInfoPojo.getAccount(), userInfoPojo.getName());
				userInfoPojo.setGkxtToken(gkxtToken);
				userInfoPojo.setPassword(null);
				userInfoPojo.setId(null);
				userInfoPojo.setStatus(null);
				resultMap.put("userInfo", userInfoPojo);
				resultMap.put("gkxtToken", gkxtToken);
				gkxtRegistUrl = String.format(gkxtRegistUrl, account, basePassword);
				/**
				 * 注册高考学堂
				 */
				if (userInfoPojo.getIsRegisterXueTang()!=1) {
					gkxtRegistUrl = String.format(gkxtRegistUrl, account, basePassword);
					/**
					 * 注册高考学堂
					 */
					String registResult = HttpClientUtil.getContents(gkxtRegistUrl);

					if (registResult.contains("\"ret\":\"200\"")) {
						LOGGER.error("帐号" + account + ", 注册高考学堂成功!");
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("id",id);
						map.put("isRegisterXueTang",1);
						userAccountExService.updateUserAccountRegistXueTang(map);
					} else if (registResult.contains("\"ret\":\"403\"") && registResult.contains("\"msg\":\"该手机已被注册\"")) {
						LOGGER.debug("帐号" + account + ", 高考学堂已经注册!");
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("id",id);
						map.put("isRegisterXueTang",1);
						userAccountExService.updateUserAccountRegistXueTang(map);
					} else {
						LOGGER.error("帐号" + account + ", 注册高考学堂失败!");
					}
				}
			}
		}catch(Exception e){
			throw e;
		}finally{

		}
		return resultMap;
	}

	@RequestMapping(value = "/qqLogin")
	@ResponseBody
	public Map<String,Object> qqLogin(@RequestParam(value = "userId", required = false) String userId,
									  @RequestParam(value = "qqUserId", required = false) String qqUserId) throws Exception {


		if (StringUtils.isBlank(userId) || StringUtils.isBlank(qqUserId)) {
			ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
		}

		long userIdLong = Long.parseLong(userId);
		UserAccountPojo userInfo = userAccountExService.findUserAccountPojoById(userIdLong);
		if (!userInfo.getQqUserId().equals(qqUserId)) {
			ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
		}

		UserInfoPojo userInfoPojo = userAccountExService.getUserInfoPojoById(userIdLong);

		UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoById(userIdLong);

		String token = DESUtil.getEightByteMultypleStr(userId, userInfoPojo.getAccount());
		String encryptToken = DESUtil.encrypt(token, DESUtil.key);
		setUserAccountPojo(userAccountBean, encryptToken);
		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("token", encryptToken);
		String gkxtToken = GkxtUtil.getLoginToken(userInfoPojo.getAccount(), userInfoPojo.getName());
		userInfoPojo.setGkxtToken(gkxtToken);
		userInfoPojo.setPassword(null);
		userInfoPojo.setId(null);
		userInfoPojo.setStatus(null);
		resultMap.put("userInfo", userInfoPojo);
		resultMap.put("gkxtToken", gkxtToken);

		return resultMap;
	}

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() throws Exception {
		return "index";
	}

}
