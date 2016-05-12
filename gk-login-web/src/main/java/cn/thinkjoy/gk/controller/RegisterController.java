package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.HttpClientUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.constant.RedisConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ModeUtil;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zuohao on 15/9/22.
 * 注册
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/register")
public class RegisterController extends ZGKBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private IUserAccountExService userAccountExService;
    @Autowired
    private IProvinceService provinceService;

    //高考学堂注册接口
    private String gkxtRegistUrl = "http://xuetang.zhigaokao.cn/userapi/reg?mobile=%s&password=%s";
//    @Autowired
//    private ICityService cityService;
//    @Autowired
//    private ICountyService countyService;

    /**
     * 注册账号
     * @param account
     * @param captcha
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/account")
    @ResponseBody
    public Map<String, Object> registerAccount(@RequestParam(value="account",required = false) String account,
                                  @RequestParam(value="captcha",required = false) String captcha,
                                  @RequestParam(value="password",required = false) String password,
                                  @RequestParam(value="provinceId",required = false) String provinceId,
                                  @RequestParam(value="cityId",required = false) String cityId,
                                  @RequestParam(value="countyId",required = false) String countyId,
                                  @RequestParam(value="grade",required = true) int grade,
                                  @RequestParam(value="basePassword",required = false) String basePassword,
                                  @RequestParam(value = "sharerId",required = false) Long sharerId,
                                  @RequestParam(value = "sharerType",required = false) Integer sharerType)
            throws Exception{
        long areaId= getAreaId();
        Map<String, Object> resultMap = new HashMap<>();
        try{
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            if (StringUtils.isEmpty(provinceId)||"00".equals(provinceId)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择省份!");
            }
            List<Province> provinceList =provinceService.findList("id", provinceId);
            if(provinceList.size()==0)
            {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择正确省份!");
            }
//            if (StringUtils.isEmpty(cityId)||"00".equals(cityId)) {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择城市!");
//            }
//            List<Province> cityIdList =cityService.findList("id", cityId);
//            if(cityIdList.size()==0)
//            {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择正确城市!");
//            }
//            if (StringUtils.isEmpty(countyId)||"00".equals(countyId)) {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择区域!");
//            }
//            List<Province> countyList =countyService.findList("id", countyId);
//            if(countyList.size()==0)
//            {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请选择正确区域!");
//            }
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
            userAccount.setPassword(password);
            userAccount.setCreateDate(System.currentTimeMillis());
            userAccount.setLastModDate(System.currentTimeMillis());
            userAccount.setUserType(0);
            userAccount.setStatus(0);
            userAccount.setAreaId(areaId);
            userAccount.setCanTargetSchool(true);
            userAccount.setProvinceId(provinceId);
            userAccount.setCityId(cityId);
            userAccount.setCountyId(countyId);
            userAccount.setGrade(grade);
            try{
                if(null == sharerType)
                {
                    sharerType = 0;
                }
                if(null == sharerId)
                {
                    sharerId = 0l;
                }
                boolean flag=userAccountExService.insertUserAccount(userAccount,sharerId,sharerType);
                if (!flag){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
                }
            }catch(Exception e){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),"账户注册失败");
            }
            gkxtRegistUrl = String.format(gkxtRegistUrl, account, basePassword);
            //注册高考学堂
            String registResult = HttpClientUtil.getContents(gkxtRegistUrl);

            if(registResult.indexOf("\"ret\":\"200\"")==-1)
            {
                LOGGER.error("帐号"+account+", 注册高考学堂失败.....");
            }else
            {
                LOGGER.debug("帐号"+account+"注册高考学堂成功!");
            }

            userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);

            long id = userAccountBean.getId();

            String token = DESUtil.getEightByteMultypleStr(String.valueOf(id), account);
            setUserAccountPojo(userAccountBean, DESUtil.encrypt(token, DESUtil.key));
            resultMap.put("token", DESUtil.encrypt(token, DESUtil.key));
            userAccountBean.setPassword(null);
            userAccountBean.setId(null);
            resultMap.put("userInfo", userAccountBean);
        }catch (Exception e){
            throw e;
        }finally {

        }
        return resultMap;
    }
    /**
     * 找回密码
     * @param account
     * @param captcha
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/retrievePassword" )
    @ResponseBody
    public Map<String, Object>  retrievePassword(@RequestParam(value="account",required = false) String account,
                                   @RequestParam(value="captcha",required = false) String captcha,
                                   @RequestParam(value="password",required = false) String password)
            throws Exception{
        long areaId= getAreaId();
        Map<String, Object> resultMap = new HashMap<>();
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
            userAccount.setPassword(password);
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
            long id = userAccountBean.getId();

            String token = DESUtil.getEightByteMultypleStr(String.valueOf(id), account);
            setUserAccountPojo(userAccountBean, DESUtil.encrypt(token, DESUtil.key));
            resultMap.put("token", DESUtil.encrypt(token, DESUtil.key));
            userAccountBean.setPassword(null);
            userAccountBean.setId(null);
            resultMap.put("userInfo", userAccountBean);
        }catch (Exception e){
            throw e;
        }finally {

        }
        return resultMap;
    }

    /**
     * 注册时验证账号是否已经存在，type=0
     * 找回密码时验证账号是否不存在，type=1
     * @param account
     * @return
     */
    @RequestMapping(value = "/confirmAccount")
    @ResponseBody
    public String confirmAccount(@RequestParam(value = "account",required = true) String account,
                                 @RequestParam(value = "type", required = true) int type) throws Exception{
        try {
            if (StringUtils.isEmpty(account)) {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入账号!");
            }
            long areaId= getAreaId();
            UserAccountPojo userAccountBean = userAccountExService.findUserAccountPojoByPhone(account);
            if (type==0){
                if (userAccountBean!=null){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号已经注册!");
                }
            }else if (type==1){
                if (userAccountBean==null){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "该账号尚未注册!");
                }
            }else {
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "类型错误!");
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
        String key = RedisConst.USER_CAPTCHA_KEY+account;
        if (RedisUtil.getInstance().get(key)==null){
            ModeUtil.throwException(ERRORCODE.CHECK_SMSCODE_NOT_EXIST);
        }
        String cap=RedisUtil.getInstance().get(key).toString();
        if (captcha.equals(cap)){
            RedisUtil.getInstance().del(key);
            equals=true;
        }
        return equals;
    }

    /**
     * 短信收不到验证码的时候,查询手机验证码接口
     * @param account
     * @return
     */
    @RequestMapping(value = "/getRegisterCaptcha")
    @ResponseBody
    public String getRegisterCaptcha(String account)
    {
        String key = RedisConst.USER_CAPTCHA_KEY + account;
        Object value = RedisUtil.getInstance().get(key);
        if (value == null){
            ModeUtil.throwException(ERRORCODE.CHECK_SMSCODE_NOT_EXIST);
        }
        return value.toString();
    }

}