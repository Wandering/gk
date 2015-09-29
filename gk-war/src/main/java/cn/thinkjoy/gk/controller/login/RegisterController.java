package cn.thinkjoy.gk.controller.login;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.RedisConst;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.RedisUtil;
import com.jlusoft.microschool.core.utils.MD5Util;
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


/**
 * Created by zuohao on 15/9/22.
 * 注册
 */
@Controller
@Scope("prototype")
@RequestMapping("/register")
public class RegisterController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private IUserAccountExService userAccountExService;

    /**
     * 注册账号
     * @param account
     * @param captcha
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/account",method = RequestMethod.POST)
    @ResponseBody
    public String registerAccount(@RequestParam(value="account",required = false) String account,
                           @RequestParam(value="captcha",required = false) String captcha,
                           @RequestParam(value="password",required = false) String password)
                                throws Exception{

        try{
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            if (StringUtils.isEmpty(captcha)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入验证码!");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
            }
            UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
            if (userAccountBean!=null){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号已被注册!");
            }
            if (!checkCaptcha(account,captcha)){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "验证码有误!");
            }

            //保存用户
            UserAccount userAccount = new UserAccount();
            userAccount.setAccount(account);
            userAccount.setPassword(MD5Util.MD5Encode(password));
            userAccount.setCreateDate(System.currentTimeMillis());
            userAccount.setLastModDate(System.currentTimeMillis());
            userAccount.setUserType(0);
            userAccount.setStatus(0);
            try{
                boolean flag=userAccountExService.insertUserAccount(userAccount);
                if (!flag){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
                }
            }catch(Exception e){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
            }
        }catch (Exception e){
            throw e;
        }finally {

        }
        return "registerSuccess";
    }

    /**
     * 找回密码
     * @param account
     * @param captcha
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/retrievePassword" ,method = RequestMethod.POST)
    @ResponseBody
    public String retrievePassword(@RequestParam(value="account",required = false) String account,
                                   @RequestParam(value="captcha",required = false) String captcha,
                                   @RequestParam(value="password",required = false) String password)
            throws Exception{
        try{
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            if (StringUtils.isEmpty(captcha)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入验证码!");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
            }
            UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
            if (userAccountBean==null){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号尚未注册!");
            }
            if (!checkCaptcha(account,captcha)){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "验证码有误!");
            }

            //根据账号id查询账号
            UserAccount userAccount = userAccountExService.findUserAccountById(userAccountBean.getId());
            userAccount.setPassword(MD5Util.MD5Encode(password));
            userAccount.setLastModDate(System.currentTimeMillis());
            try{
                //更新账号密码
                boolean flag=userAccountExService.updateUserAccount(userAccount);
                if (!flag){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"密码重设失败");
                }
            }catch(Exception e){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"密码重设失败");
            }
        }catch (Exception e){
            throw e;
        }finally {

        }
        return "success";
    }

    /**
     * 注册时验证账号是否已经存在，type=0
     * 找回密码时验证账号是否不存在，type=1
     * @param account
     * @return
     */
    @RequestMapping(value = "/confirmAccount",method = RequestMethod.POST)
    @ResponseBody
    public String confirmAccount(@RequestParam(value = "account",required = true) String account,
                                 @RequestParam(value = "type", required = true) int type){
        try {
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
            if (userAccountBean!=null) {
                if (type == 0) {
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号已经注册!");
                } else if (type == 1) {
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号尚未注册!");
                } else {
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "类型错误!");
                }
            }
        }catch (Exception e){
            throw e;
        }
        return "success";
    }

    /**
     * 判断验证码是否正确
     * @param account
     * @param captcha
     * @return
     */
    private boolean checkCaptcha(String account,String captcha){
        boolean equals=false;
        String userCaptchaKey = RedisConst.USER_CAPTCHA_KEY+account;
        if (RedisUtil.getInstance().get(userCaptchaKey)==null){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "验证码过期或不存在，请重新获取!");
        }
        String cap=RedisUtil.getInstance().get(userCaptchaKey).toString();
        if (captcha.equals(cap)){
            RedisUtil.getInstance().del(userCaptchaKey);
            equals=true;
        }
        return equals;
    }


}
