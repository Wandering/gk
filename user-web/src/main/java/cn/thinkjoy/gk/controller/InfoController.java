package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.common.IForecase;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Forecast;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.enumerate.ERRORCODE;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.service.IUserAccountService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.service.IUserInfoService;
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
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/info")
public class InfoController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private IForecase forecase;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserInfoExService userInfoExService;

    @Autowired
    private IUserAccountExService userAccountExService;

    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 查询显示个人信息
     * @return
     */
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo() {
        String id=getAccoutId();
        UserInfo userInfo=userInfoExService.findUserInfoById(Long.valueOf(id));
        try {
            Forecast forecast=(Forecast)forecase.getLastoFrecast(userInfo.getId().toString());
            userInfo.setAchievement(forecast.getAchievement().toString());
            userInfo.setType(forecast.getType());
            userInfo.setUniversityName(forecast.getUniversityName());
            userInfo.setTypeId(forecast.getTypeId());
        }catch (Exception e){

            LOGGER.debug("获取成绩信息失败！");
        }finally {
            userInfo.setIsForecaset(forecase.isFrecast());
        }
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
    @RequestMapping(value = "updateUserInfo")
    @ResponseBody
    public String updateUserInfo(UserInfo userInfo) {
        try {
            UserAccountPojo userAccountPojo = getUserAccountPojo();
//            UserInfo userInfo=userInfoExService.findUserInfoById(Long.valueOf(id));

            Long birthdayDate = userInfo.getBirthdayDate();
            if (null != birthdayDate) {
                userInfo.setBirthdayDate(birthdayDate * 1000);
            }


            boolean flag = false;

            String icon = userInfo.getIcon();

            if (!StringUtils.isEmpty(icon)) {
//                ssUserInfo.setIcon(icon);
                userAccountPojo.setIcon(icon);
                flag = true;
            }

            String name = userInfo.getName();

            if (StringUtils.isEmpty(userInfo.getBirthdayDate().toString())) {
                throw new BizException("error","出生日期不能为空");
            }

            if (!StringUtils.isEmpty(name)) {
//                ssUserInfo.setName(name);
                userAccountPojo.setName(name);
                flag = true;
            }

//            if (flag) {
//                userInfoApiService.updateUserInfo(ssUserInfo);
//            }

            userInfo.setId(userAccountPojo.getId());

            userInfoService.update(userInfo);

            String token = DESUtil.getEightByteMultypleStr(String.valueOf(userAccountPojo.getId()), userAccountPojo.getAccount());
            setUserAccountPojo(userAccountPojo, DESUtil.encrypt(token, DESUtil.key));

//            userInfoExService.updateUserInfoById(userInfo);
        } catch (NoSuchMethodException e){

        }
        catch (BizException e) {
            throw e;
        }
        catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return "success";
    }

    /**
     * 修改密码时验证旧密码
     * @param oldPassword
     * @return
     */
    @RequestMapping(value = "confirmPassword")
    @ResponseBody
    public String confirmPassword(@RequestParam(value = "oldPassword",required = true)String oldPassword){
        String id=getAccoutId();
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
    @RequestMapping(value = "modifyPassword")
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
            String id=getAccoutId();
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
