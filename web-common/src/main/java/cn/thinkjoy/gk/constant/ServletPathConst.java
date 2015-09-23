package cn.thinkjoy.gk.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clei on 15/8/8.
 */
public class ServletPathConst {

    public static Map<String,String> MAPPING_URLS = new HashMap<String,String>();

    static{
        MAPPING_URLS.put("/doLogin.do","");
        MAPPING_URLS.put("/login.do","");
        MAPPING_URLS.put("/register.do","");
        MAPPING_URLS.put("/captcha.do","");
        MAPPING_URLS.put("/auth/hjyAuth.do","");
        MAPPING_URLS.put("/hjyAuth.do","");
        MAPPING_URLS.put("/jyrrtAuth.do","");
        MAPPING_URLS.put("/yjyrrtAuth.do","");
        MAPPING_URLS.put("/czbAuth.do","");
        MAPPING_URLS.put("/etgAuth.do","");
    }
}
