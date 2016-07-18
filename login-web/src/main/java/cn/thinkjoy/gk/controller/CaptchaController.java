package cn.thinkjoy.gk.controller;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.CaptchaConst;
import cn.thinkjoy.gk.constant.RedisConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CaptchaUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.sms.api.SMSService;
import cn.thinkjoy.sms.domain.SMSCheckCode;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/captcha")
public class CaptchaController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    private SMSService zgkSmsService;

//    @Autowired
//    private cn.thinkjoy.push.service.sms.SMSService smsService;

    @Autowired
    private IUserAccountExService userAccountExService;

    @RequestMapping(value = "/captcha")
    @ResponseBody
    public String captcha(@RequestParam(value="account",required=false) String account,
                          @RequestParam(value="type",required=false) Integer type) {

        if(StringUtils.isEmpty(account) || type == null){
            ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
        }

        int count = userAccountExService.findUserAccountCountByPhone(
                account,
                null);

        // type=0为注册，type=1找回密码
        if(type == 0 && count > 0) {
            // 注册账号已存在
            ModelUtil.throwException(ERRORCODE.PHONENUM_HAS_EXIST);
        }else if(type == 1 && count == 0){
            // 找回密码账号不存在
            ModelUtil.throwException(ERRORCODE.PHONENUM_NOT_EXIST);
        }

        long time = CaptchaConst.CAPTCHA_TIME;
        String timeKey = RedisConst.CAPTCHA_AUTH_TIME_KEY + account;
        RedisRepository redis = RedisUtil.getInstance();
        JSONObject result = new JSONObject();
        // 验证码存在缓存中
        if(redis.exists(timeKey)){
            time = time -
                    ((System.currentTimeMillis() -
                    Long.valueOf(redis.get(timeKey).toString()))/1000);
            result.put("time", time);
            return result.toJSONString();
        }

        String randomString = CaptchaUtil.getRandomNumString(6);

//        cn.thinkjoy.push.domain.sms.SMSCheckCode smsCheckCode = new cn.thinkjoy.push.domain.sms.SMSCheckCode();
//        smsCheckCode.setPhone(account);
//        smsCheckCode.setBizTarget(CaptchaConst.CAPTCHA_TARGET);
//        smsCheckCode.setCheckCode(randomString);

//        boolean smsResult = smsService.sendSMS(smsCheckCode,false);

        SMSCheckCode zgkSmsCheckCode = new SMSCheckCode();
        zgkSmsCheckCode.setPhone(account);
        zgkSmsCheckCode.setCheckCode(randomString);
        zgkSmsCheckCode.setBizTarget(CaptchaConst.CAPTCHA_TARGET);

        boolean smsResult = zgkSmsService.sendSMS(zgkSmsCheckCode,false);

        if(!smsResult) {
            // 发送失败切换短信通道
            smsResult = zgkSmsService.sendSMS(zgkSmsCheckCode,true);
        }

        if(smsResult){
            String userCaptchaKey = RedisConst.USER_CAPTCHA_KEY + account;
            redis.set(userCaptchaKey,randomString);
            redis.expire(userCaptchaKey, 600, TimeUnit.SECONDS);
            redis.set(timeKey, String.valueOf(System.currentTimeMillis()));
            redis.expire(timeKey, 60, TimeUnit.SECONDS);
        }else {
            // 再次发送失败,抛出异常
            ModelUtil.throwException(ERRORCODE.SEND_SMSCODE_ERROR);
        }

        result.put("time", time);
        return result.toJSONString();
    }

}
