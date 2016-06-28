package cn.thinkjoy.gk.common;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.gk.constant.GkxtConstants;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 高考学堂对接工具类
 * Created by liusven on 16/6/23.
 */
public class GkxtUtil {

    /**
     * 单点登录高考学堂
     * @param userId
     * @param name
     * @return
     * @throws Exception
     */
    public static String getLoginToken(long userId,String name) throws Exception {
        String url = DynConfigClientFactory.getClient().getConfig("common", "gkxtTokenUrl");
        String token = "";
        String clientId = GkxtConstants.CLIENTID;
        String secret = GkxtConstants.SECRET;
        String code = GkxtConstants.CODE;
        String salt = GkxtConstants.SALT;
        String sign = getSign(clientId, secret, code, salt);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("clientId", clientId);
        paramMap.put("secret", secret);
        paramMap.put("nickname", name);
        paramMap.put("userId", userId);
        paramMap.put("sign", sign);
        String content = HttpClientUtil.postContents(url, paramMap);
        String result = StringEscapeUtils.unescapeJava(content);
        if(StringUtils.isNotBlank(result))
        {
            System.out.println(result);
            Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
            String reCode = resultMap.get("code") + "";
            String reToken = resultMap.get("token") + "";
            if("2001".equals(reCode))
            {
                token = reToken;
            }
        }
        return token;
    }

    private static String getSign(String clientId, String secret, String code, String salt) {
        String format = "yyyy-M-d";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String currentDay = formater.format(new Date()).toString();
        StringBuffer text = new StringBuffer();
        text.append(clientId);
        text.append(code);
        text.append(secret);
        text.append(salt);
        text.append(currentDay);
        return MD5(text.toString());
    }

    public static  String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}