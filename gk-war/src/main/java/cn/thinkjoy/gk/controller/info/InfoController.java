package cn.thinkjoy.gk.controller.info;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.service.IUserAccountService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.service.IUserInfoService;
import cn.thinkjoy.ss.api.IQuestionService;
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
 * Created by zuohao on 15/9/23.
 */
@Controller
@Scope("prototype")
@RequestMapping("/info")
public class InfoController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserInfoExService userInfoExService;

    @Autowired
    private IUserAccountExService userAccountExService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private cn.thinkjoy.ss.api.IUserInfoService userInfoApiService;

    /**
     * 查询显示个人信息
     * @return
     */
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo() {
        String id=getCookieValue();
        UserInfo userInfo=userInfoExService.findUserInfoById(Long.valueOf(id));
        return userInfo;
    }

    /**
     * 查询账号信息
     * @return
     */
    @RequestMapping(value = "getUserAccount",method = RequestMethod.GET)
    @ResponseBody
    public UserAccountPojo getUserAccount() {
        return getUserAccountPojo();
    }

    /**
     * 更改个人信息
     * @return
     */
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateUserInfo(UserInfo userInfo){
        try {
            UserAccountPojo userAccountPojo = getUserAccountPojo();
//            UserInfo userInfo=userInfoExService.findUserInfoById(Long.valueOf(id));

            Long birthdayDate = userInfo.getBirthdayDate();
            if(null!=birthdayDate){
                userInfo.setBirthdayDate(birthdayDate*1000);
            }

            boolean flag = false;

            cn.thinkjoy.ss.domain.UserInfo ssUserInfo = new cn.thinkjoy.ss.domain.UserInfo();

            String icon = userInfo.getIcon();

            if(!StringUtils.isEmpty(icon)){
                ssUserInfo.setIcon(icon);
                userAccountPojo.setIcon(icon);
                flag = true;
            }

            String name = userInfo.getName();

            if(!StringUtils.isEmpty(name)){
                ssUserInfo.setName(name);
                userAccountPojo.setName(name);
                flag = true;
            }

            if(flag){
                userInfoApiService.updateUserInfo(ssUserInfo);
            }

            userInfo.setId(userAccountPojo.getId());

            userInfoService.update(userInfo);

            setUserAccountPojo(userAccountPojo);

//            userInfoExService.updateUserInfoById(userInfo);
        }catch (Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return "success";
    }

    /**
     * 修改密码时验证旧密码
     * @param oldPassword
     * @return
     */
    @RequestMapping(value = "confirmPassword",method = RequestMethod.POST)
    @ResponseBody
    public String confirmPassword(@RequestParam(value = "oldPassword",required = true)String oldPassword){
        String id=getCookieValue();
        UserAccount userAccount = userAccountExService.findUserAccountById(Long.valueOf(id));
        if (userAccount==null){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号信息有误!");
        }
        if (!userAccount.getPassword().equals(MD5Util.MD5Encode(oldPassword))){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "当前密码有误，请重试!");
        }
        return "success";
    }

    /**
     * 保存新密码
     * @param oldPassword
     * @param password
     * @return
     */
    @RequestMapping(value = "modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public String modifyPassword(@RequestParam(value = "oldPassword",required = true)String oldPassword,
                                 @RequestParam(value="password",required = false) String password){
        try{

            if (StringUtils.isEmpty(password)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
            }
            if (StringUtils.isEmpty(oldPassword)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
            }

            //根据账号id查询账号
            String id=getCookieValue();
            UserAccount userAccount = userAccountExService.findUserAccountById(Long.valueOf(id));
            if (userAccount==null){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号信息有误!");
            }
            if (!userAccount.getPassword().equals(MD5Util.MD5Encode(oldPassword))){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "当前密码有误，请重试!");
            }
            if (userAccount.getPassword().equals(MD5Util.MD5Encode(password))){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "新密码不能与旧密码相同!");
            }
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

}
