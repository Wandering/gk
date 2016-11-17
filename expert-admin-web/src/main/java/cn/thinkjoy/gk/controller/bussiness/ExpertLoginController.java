package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.ErrorCode;
import cn.thinkjoy.gk.common.ExceptionUtil;
import cn.thinkjoy.gk.common.RandomCodeUtil;
import cn.thinkjoy.gk.constant.ExpertAdminConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;
import cn.thinkjoy.gk.service.IExpertLoginServcie;
import cn.thinkjoy.sms.api.SMSService;
import cn.thinkjoy.sms.domain.SMSCheckCode;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyongping on 2016/11/16.
 * 专家登录重置密码
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert/admin/login")
public class ExpertLoginController {
    @Autowired
    IExpertLoginServcie expertLoginServcie;

    @Autowired
    private SMSService zgkSmsService;

    @Autowired
    private RedisRepository<String, Object> redis;
    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ExpertUserDTO login(ExpertUser expertUser,HttpServletRequest request) {
        checkExpertUser(expertUser);
        ExpertUserDTO expertUserDTO = expertLoginServcie.login(expertUser);
        // 登陆成功,将用户ID存入session
        HttpSession session = request.getSession();
        session.setAttribute(ExpertAdminConst.USER_SESSION_KEY, JSON.toJSON(expertUserDTO));
        // session过期时间
        session.setMaxInactiveInterval(ExpertAdminConst.USER_SESSION_TIMEOUT);
        return expertUserDTO;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return true;
    }

    /**
     * 重置密码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public void resetPassword(ExpertUser expertUser,String newPassword,HttpServletRequest request) {
        checkExpertUser(expertUser);
        if (StringUtils.isEmpty(newPassword)){}{
            ExceptionUtil.throwException(ErrorCode.NEW_PWD_NULL);
        }
        //修改密码
        Boolean flag = expertLoginServcie.resetPassword(expertUser,newPassword);
        if (flag) {
            expertUser.setPassword(newPassword);
            ExpertUserDTO expertUserDTO = expertLoginServcie.login(expertUser);
            // 登陆成功,将用户ID存入session
            HttpSession session = request.getSession();
            session.setAttribute(ExpertAdminConst.USER_SESSION_KEY, JSON.toJSON(expertUserDTO));
            // session过期时间
            session.setMaxInactiveInterval(ExpertAdminConst.USER_SESSION_TIMEOUT);
        }
        return;
    }

    @ResponseBody
    @ApiDesc(value = "发送验证码",owner = "杨国荣")
    @RequestMapping(value = "/sendSmsCode",method = RequestMethod.POST)
    public Map<String,Object> sendSmsCode(@RequestParam String account){

        Map<String,Object> paramMap = Maps.newHashMap();
        ExpertUser expertUser = new ExpertUser();
        if(!expertLoginServcie.userExist(account)){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_NULL);
        }
        String timeKey = ExpertAdminConst.CAPTCHA_AUTH_TIME_KEY + account;
        String smsCodeKey = ExpertAdminConst.USER_CAPTCHA_KEY + account;
        // 发送验证码间隔时间未到(获取验证码太频繁)
        if(redis.exists(timeKey)){
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_FREQUENCY);
        }

        SMSCheckCode smsCode = new SMSCheckCode();
        smsCode.setPhone(account);
        smsCode.setCheckCode(RandomCodeUtil.generateNumCode(6));
        smsCode.setBizTarget(ExpertAdminConst.ZGK);

        boolean smsResult = zgkSmsService.sendSMS(smsCode,false);

        if(!smsResult) {
            // 发送失败切换短信通道
            smsResult = zgkSmsService.sendSMS(smsCode,true);
        }

        if(smsResult){
            // 验证码有效时间10分钟
            redis.set(
                    smsCodeKey,
                    smsCode.getCheckCode(),
                    600,
                    TimeUnit.SECONDS
            );
            // 发送验证码间隔时间1分钟
            redis.set(
                    timeKey,
                    System.currentTimeMillis(),
                    60,
                    TimeUnit.SECONDS
            );
        }else {
            // 再次发送失败,抛出异常
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_FAIL);
        }

        Map<String,Object> returnMap = Maps.newHashMap();
        returnMap.put("time",60);
        return returnMap;
    }

    private void checkExpertUser(ExpertUser expertUser){
        if (StringUtils.isEmpty(expertUser.getAccount())){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_NULL);
        }
        if (StringUtils.isEmpty(expertUser.getPassword())){
            ExceptionUtil.throwException(ErrorCode.PWD_NULL);
        }
    }
}
