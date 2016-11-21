package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ErrorCode;
import cn.thinkjoy.gk.common.ExceptionUtil;
import cn.thinkjoy.gk.constant.ExpertAdminConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.ExpertInfo;
import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;
import cn.thinkjoy.gk.service.IExpertInfoService;
import cn.thinkjoy.gk.service.IExpertLoginServcie;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    private IExpertInfoService expertInfoService;

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
    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        try {
            response.sendRedirect(ExpertAdminConst.LOGIN_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置密码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Boolean resetPassword(ExpertUser expertUser,String newPassword) {
        checkExpertUser(expertUser);
        ExpertInfo expertInfo = (ExpertInfo)expertInfoService.findOne("expert_phone",expertUser.getAccount());
        if (!expertUser.equals(expertInfo.getPassword())){
            ExceptionUtil.throwException(ErrorCode.PWD_ERROR);
        }
        if (StringUtils.isEmpty(newPassword)){
            ExceptionUtil.throwException(ErrorCode.NEW_PWD_NULL);
        }
        //修改密码
        Boolean flag = expertLoginServcie.resetPassword(expertUser,newPassword);
        return flag;
    }
    /**
     * 找回密码
     *
     * @param account
     * @param captcha
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/retrievePassword")
    @ResponseBody
    public Boolean retrievePassword(@RequestParam(value = "account", required = false) String account,
                                                @RequestParam(value = "captcha", required = false) String captcha,
                                                @RequestParam(value = "password", required = false) String password)
    {
        try
        {
            if (org.apache.commons.lang3.StringUtils.isEmpty(account))
            {
                throw new BizException(ErrorCode.PARAM_NULL.getCode(), "请输入账号!");
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(captcha))
            {
                throw new BizException(ErrorCode.PARAM_NULL.getCode(), "请输入验证码!");
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(password))
            {
                throw new BizException(ErrorCode.PARAM_NULL.getCode(), "请输入密码!");
            }
            ExpertInfo expertInfo = (ExpertInfo)expertInfoService.findOne("expert_phone",account);
            if (expertInfo == null)
            {
                ExceptionUtil.throwException(ErrorCode.ACCOUNT_ERROR);
            }
            if (!checkCaptcha(account, captcha))
            {
                throw new BizException(ErrorCode.PARAM_NULL.getCode(), "验证码有误!");
            }

            //根据账号id查询账号
            ExpertInfo updatePass = new ExpertInfo();
            updatePass.setPassword(password);
            try
            {
                //更新账号密码
                Boolean flag = expertInfoService.update(updatePass)>0;
                if (!flag)
                {
                    throw new BizException(ErrorCode.PARAM_NULL.getCode(), "密码重设失败");
                }
            }
            catch (Exception e)
            {
                throw new BizException(ErrorCode.PARAM_NULL.getCode(), "密码重设失败");
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {

        }
        return true;
    }

    /**
     * 图形验证码太模糊的时候,查询图形验证码接口
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/getRegisterImageCaptcha")
    @ResponseBody
    public String getRegisterImageCaptcha(String account)
    {
        String key = ExpertAdminConst.USER_IMAGE_CAPTCHA_KEY + account;
        Object value = redis.get(key);
        if (value == null)
        {
            ExceptionUtil.throwException(ErrorCode.CHECK_SMSCODE_NOT_EXIST);
        }
        return value.toString();
    }

    /**
     * 判断验证码是否正确
     *
     * @param account
     * @param captcha
     * @return
     */
    private boolean checkCaptcha(String account, String captcha)
    {
        boolean equals = false;
        String key = ExpertAdminConst.USER_CAPTCHA_KEY + account;
        if (redis.get(key) == null)
        {
            ExceptionUtil.throwException(ErrorCode.CHECK_SMSCODE_NOT_EXIST);
        }
        String cap = redis.get(key).toString();
        if (captcha.equals(cap))
        {
            redis.del(key);
            equals = true;
        }
        return equals;
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
