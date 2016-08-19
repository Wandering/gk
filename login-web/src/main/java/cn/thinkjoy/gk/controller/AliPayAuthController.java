package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.alipay.AlipayConfig;
import cn.thinkjoy.gk.alipay.AlipaySubmit;
import cn.thinkjoy.gk.common.TimeUtil;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUserAccountExService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liusven on 16/7/28.
 */
@Controller
@RequestMapping("/alipayAuth")
@Scope("prototype")
public class AliPayAuthController
{
    private String userInfoUrl = "https://openapi.alipay.com/gateway.do";

    private AlipayClient alipayClient =new DefaultAlipayClient(userInfoUrl,
                                        AlipayConfig.APP_ID,AlipayConfig.APP_PRIVATE_KEY,"json",
                                        "GBK",AlipayConfig.ALIPAY_PUBLIC_KEY);

    private String baseAuthUrl = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?";

    @Autowired
    private IUserAccountExService userAccountExService;
    @Autowired
    private IProvinceService provinceService;

    @RequestMapping(value = "/authPage")
    public String authPage() throws Exception
    {
        String redirectUrl= "http%3A%2F%2Fzgkser.zhigaokao.cn%2FalipayAuth%2FgetUserId";
        StringBuffer baseAuthURL = new StringBuffer(baseAuthUrl);
        baseAuthURL.append("app_id=").append(AlipayConfig.APP_ID);
        baseAuthURL.append("&scope=").append("auth_base");
        baseAuthURL.append("&redirect_uri=").append(redirectUrl);
        return "redirect:"+baseAuthURL;
    }

    @RequestMapping(value = "/getAuthToken", produces = "application/json; charset=utf-8")
    public String getAuthToken(@RequestParam(value="auth_code",required=false) String authCode)
    {
        String accessToken = getAccessToken(getOauthTokenResponse(authCode));
        if(null == accessToken)
        {
            throw new BizException("", "access token不能为空!");
        }
        String result = getResult(accessToken);
        String[] resultArray = result.split("@@");
        String userId = resultArray[0];
        String areaId = null;
        if(resultArray.length>1)
        {
            areaId = resultArray[1];
        }
        return getRedirectUrl(userId, areaId);
    }

    @RequestMapping(value = "/getUserId", produces = "application/json; charset=utf-8")
    public String getUserId(@RequestParam(value="auth_code",required=false) String authCode)
    {
        String aliUserId = getUserId(getOauthTokenResponse(authCode));
        Map<String, Object> userInfoMap = userAccountExService.findUserInfoByAlipayId(aliUserId);
        if(null == userInfoMap || userInfoMap.isEmpty())
        {
            String redirectUrl= "http%3A%2F%2Fzgkser.zhigaokao.cn%2FalipayAuth%2FgetAuthToken";
            StringBuffer userInfoAuthURL = new StringBuffer(baseAuthUrl);
            userInfoAuthURL.append("app_id=").append(AlipayConfig.APP_ID);
            userInfoAuthURL.append("&scope=").append("auth_userinfo");
            userInfoAuthURL.append("&redirect_uri=").append(redirectUrl);
            return "redirect:"+userInfoAuthURL;
        }
        String userId = userInfoMap.get("id") + "";
        String areaId = userInfoMap.get("provinceId") + "";
        return getRedirectUrl(userId, areaId);
    }

    private String getRedirectUrl(String userId, String areaId)
    {
        return "redirect:http://alipay.test.zhigaokao.cn/results-confirm.html?userId="+userId+"&areaId="+areaId;
    }

    private String getResult(String accessToken)
    {
        String userId = "";
        String areaId = "";
        AlipayUserUserinfoShareRequest request = new AlipayUserUserinfoShareRequest();
        try {
            AlipayUserUserinfoShareResponse userinfoShareResponse = alipayClient.execute(request, accessToken);
            String aliUserId = userinfoShareResponse.getAlipayUserId();
            String nickName = userinfoShareResponse.getNickName();
            String avatar = userinfoShareResponse.getAvatar();
            String provinceName = userinfoShareResponse.getProvince();
            UserAccount userAccount = new UserAccount();
            userAccount.setAccount(aliUserId);
            userAccount.setCreateDate(System.currentTimeMillis());
            userAccount.setLastModDate(System.currentTimeMillis());
            userAccount.setUserType(0);
            userAccount.setStatus(0);
            userAccount.setCanTargetSchool(true);
            if(StringUtils.isNotBlank(nickName))
            {
                userAccount.setNickName(nickName);
            }
            if(StringUtils.isNotBlank(avatar))
            {
                userAccount.setAvatar(avatar);
            }
            if(StringUtils.isNotBlank(provinceName))
            {
                Province province = (Province)provinceService.findOne("name", provinceName);
                if(null != province)
                {
                    areaId = String.valueOf(province.getId());
                    userAccount.setAreaId(province.getId());
                    userAccount.setProvinceId(province.getId()+"");
                }
            }
            try{
                boolean flag = userAccountExService.insertUserAccount(userAccount, 0l, 0);
                if (!flag){
                    throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "账户注册失败");
                }
            }catch(Exception e){
                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "账户注册失败");
            }
            userId = userAccount.getId()+"";
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return userId+"@@"+areaId;
    }

    private String getAccessToken(AlipaySystemOauthTokenResponse oauthTokenResponse)
    {
        return oauthTokenResponse.getAccessToken();
    }

    private String getUserId(AlipaySystemOauthTokenResponse oauthTokenResponse)
    {
        return oauthTokenResponse.getUserId();
    }

    private AlipaySystemOauthTokenResponse getOauthTokenResponse(String authCode)
    {
        AlipaySystemOauthTokenResponse oauthTokenResponse;
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            oauthTokenResponse = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }
        return oauthTokenResponse;
    }

    @RequestMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUserInfo(@RequestParam(value="auth_code",required=false) String authCode)
        throws Exception
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("app_id", AlipayConfig.APP_ID);
        paramMap.put("method", "alipay.user.userinfo.share");
        paramMap.put("charset", "gbk");
        paramMap.put("sign_type", "RSA");
        paramMap.put("timestamp", TimeUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
        paramMap.put("version", "1.0");
        paramMap.put("auth_token", getAccessToken(getOauthTokenResponse(authCode)));
        paramMap.put("sign", AlipaySignature.rsaSign(paramMap, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.input_charset));
        return AlipaySubmit.buildRequest("","", paramMap);
    }

    @RequestMapping(value = "/getAuthPage")
    @ResponseBody
    public String getAuthPage()
        throws Exception
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("service", AlipayConfig.service);
        paramMap.put("partner", AlipayConfig.partner);
        paramMap.put("_input_charset", AlipayConfig.input_charset);
        paramMap.put("return_url", AlipayConfig.return_url);
        paramMap.put("target_service", AlipayConfig.target_service);
        return AlipaySubmit.buildRequest(paramMap,"POST","submitButton");
    }
}
