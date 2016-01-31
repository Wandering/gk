package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpVipTest extends TestCase{
//        String host="http://10.254.130.33:9080";
//        String host="http://zjtest.zhigaokao.cn";
        String host="http://localhost:8080";
        String upgradeVipByCard_url="/vip/upgradeVipByCard.do";

    /**
     * 升级VIP
     */
     public void testGetPerformanceDetail(){
            String url=host + upgradeVipByCard_url + "?cardNumber=0311240868&password=286940616&token=Na5QqNTNBAbNjFm%2FryG3YQ%3D%3D&userKey=zj\n";
            String result = RequestUtils.requestPost(url);
            assertTrue(result.contains("33"));
            System.out.println("result = " + result);

     }


}
