package cn.thinkjoy.gk.login.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.protocol.ERRORCODE;
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
            //TODO 验证验证码

            //保存用户
            UserAccount userAccount = new UserAccount();
            userAccount.setAccount(account);
            userAccount.setPassword(MD5Util.MD5Encode(password));
            userAccount.setCreateDate(System.currentTimeMillis());
            userAccount.setLastModDate(System.currentTimeMillis());
            try{
                boolean flag=userAccountExService.insertUserAccount(userAccount);
                if (!flag){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
                }
            }catch(Exception e){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return "registerSuccess";
    }


}
