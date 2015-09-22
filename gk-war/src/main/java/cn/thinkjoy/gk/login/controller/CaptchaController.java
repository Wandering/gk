package cn.thinkjoy.gk.login.controller;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.CaptchaTimeConst;
import cn.thinkjoy.gk.constant.RedisConst;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CaptchaUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.push.domain.sms.SMSCheckCode;
import cn.thinkjoy.push.service.sms.SMSService;
import com.alibaba.fastjson.JSONObject;
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

import java.util.concurrent.TimeUnit;

@Controller
@Scope("prototype")
@RequestMapping("/")
public class CaptchaController extends BaseController {

	private static final Logger LOGGER= LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private SMSService smsService;

	@Autowired
	private IUserAccountExService userAccountExService;

	@RequestMapping(value = "/captcha",method = RequestMethod.POST)
	@ResponseBody
	public String captcha(@RequestParam(value="account",required=false) String account,@RequestParam(value="type",required=false) Integer type) throws Exception {

		JSONObject result = new JSONObject();
		try{
			if(StringUtils.isEmpty(account)){

			}

			if(type==null){

			}

			int count = userAccountExService.findUserAccountCountByPhone(account);

			if(type==0) {
				if (count > 0) {
					throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "手机号已存在!");
				}
			}else if(type==1){
				if (count < 0) {
					throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "手机号不存在!");
				}
			}else{
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "类型错误!");
			}

			long time = CaptchaTimeConst.CAPTCHA_TIME;
			String timeKey  = RedisConst.CAPTCHA_AUTH_TIME_KEY+account;
			if(!RedisUtil.getInstance().exists(timeKey)){

				String bizTarget = DynConfigClientFactory.getClient().getConfig("sms", "bizTarget");

				String randomString = CaptchaUtil.getRandomNumString(6);

				System.out.println(randomString);
				SMSCheckCode smsCheckCode=new SMSCheckCode();
				smsCheckCode.setPhone(account);
				smsCheckCode.setBizTarget(bizTarget);
				smsCheckCode.setCheckCode(randomString);

				boolean smsResult =smsService.sendSMS(smsCheckCode,false);

				if(smsResult) {
					String userCaptchaKey = RedisConst.USER_CAPTCHA_KEY+account;
					RedisUtil.getInstance().set(userCaptchaKey,randomString);
					RedisUtil.getInstance().expire(timeKey, 7200, TimeUnit.SECONDS);
					RedisUtil.getInstance().set(timeKey, String.valueOf(System.currentTimeMillis()));
					RedisUtil.getInstance().expire(timeKey, 120, TimeUnit.SECONDS);
				}
			}else{
				time = time - ((System.currentTimeMillis() - Long.valueOf(RedisUtil.getInstance().get(timeKey).toString()))/1000);
			}

			result.put("time", time);
		} catch(Exception e){
			throw e;
		}
//		finally{
//			request.setAttribute("result", obj.toString());
//		}
		return result.toJSONString();
	}

}
