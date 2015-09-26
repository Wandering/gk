package cn.thinkjoy.gk.controller.info;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import com.jlusoft.microschool.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zuohao on 15/9/23.
 */
@Controller
@RequestMapping("/info")
public class InfoController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private IUserInfoExService userInfoExService;

    @Autowired
    private IUserAccountExService userAccountExService;

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
     * 更改个人信息
     * TODO 头像未处理
     * @return
     */
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateUserInfo(@RequestParam(value="name",required = true) String name,
                                 @RequestParam(value="countyId",required = true) long countyId,
                                 @RequestParam(value="schoolName",required = true) String schoolName,
                                 @RequestParam(value="sex",required = true) int sex,
                                 @RequestParam(value="subjectType",required = true) int subjectType,
                                 @RequestParam(value="mail",required = true) String mail,
                                 @RequestParam(value="icon",required = true) String icon,
                                 @RequestParam(value="qq",required = true) String qq
                                 ){
        try {
            UserAccountPojo userAccountPojo=getUserAccountPojo();
            UserInfo userInfo=userInfoExService.findUserInfoById(userAccountPojo.getId());
            userInfo.setName(name);
            userInfo.setCountyId(countyId);
            userInfo.setSchoolName(schoolName);
            userInfo.setSex(sex);
            userInfo.setSubjectType(subjectType);
            userInfo.setMail(mail);
            userInfo.setQq(qq);
            userInfo.setIcon(icon);
            userInfoExService.updateUserInfoById(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 修改密码时验证旧密码
     * @param account
     * @param oldPassword
     * @return
     */
    @RequestMapping(value = "confirmPassword",method = RequestMethod.POST)
    @ResponseBody
    public String confirmPassword(@RequestParam(value="account",required = true) String account,
                                  @RequestParam(value = "oldPassword",required = true)String oldPassword){
        UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
        if (userAccountBean==null){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号信息有误!");
        }
        if (!MD5Util.MD5Encode(userAccountBean.getPassword()).equals(MD5Util.MD5Encode(oldPassword))){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "当前密码有误，请重试!");
        }
        return "success";
    }

    /**
     * 保存新密码
     * @param account
     * @param oldPassword
     * @param password
     * @return
     */
    @RequestMapping(value = "modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public String modifyPassword(@RequestParam(value="account",required = false) String account,
                                 @RequestParam(value = "oldPassword",required = true)String oldPassword,
                                 @RequestParam(value="password",required = false) String password){
        try{
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入密码!");
            }
            UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
            if (userAccountBean==null){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号信息有误!");
            }
            if (!MD5Util.MD5Encode(userAccountBean.getPassword()).equals(MD5Util.MD5Encode(oldPassword))){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "当前密码有误，请重试!");
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
            e.printStackTrace();
        }finally {

        }
        return "success";
    }

}
