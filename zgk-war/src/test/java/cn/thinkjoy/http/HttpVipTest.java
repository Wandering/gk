package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpVipTest extends TestCase{
//        String host="http://10.254.130.33:9080";
//        String host="http://10.136.56.195:8080";
//        String host="http://zjtest.zhigaokao.cn";
        String host="http://localhost:8086";
        String upgradeVipByCard_url="/vip/upgradeVipByCard.do";
        String updateUserInfo_url="/info/updateUserInfo.do";
        String tallyPredictProbability_url="/predict/tallyPredictProbability.do";

    /**
     * 升级VIP
     */
     public void testGetPerformanceDetail(){
         String url=host + updateUserInfo_url + "?cardNumber=0311240868&password=286940616&token=Na5QqNTNBAbNjFm%2FryG3YQ%3D%3D&userKey=zj\n";
         String result = RequestUtils.requestPost(url);
            assertTrue(result.contains("33"));
            System.out.println("result = " + result);

     }

    public void testUpdateUserInfo(){
        String url=host + updateUserInfo_url + "?name=gk&schoolName=宝鸡文理学院&birthdayDate=1094400&mail=&icon=" +
                "&qq=&token=SJjPPT5sDZEzAFvH5A4pjg%3D%3D&userKey=zj";
        String result = RequestUtils.requestPost(url);
        assertTrue(result.contains("33"));
        System.out.println("result = " + result);

    }

    public void testTallyPredictProbability(){
        String url=host + tallyPredictProbability_url + "?universityName=西安翻译学院&score=500&type=1&token=SJjPPT5sDZEzAFvH5A4pjg%3D%3D&&userKey=sn&debug=true";
        String result = RequestUtils.requestPost(url);
        assertTrue(result.contains("33"));
        System.out.println("result = " + result);
    }
}
