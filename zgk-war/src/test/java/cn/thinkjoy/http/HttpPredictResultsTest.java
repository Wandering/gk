package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpPredictResultsTest extends TestCase{
//        String host="http://10.254.130.33:9080";
//        String host="http://10.136.56.195:8080";
//        String host="http://zjtest.zhigaokao.cn";
        String host="http://localhost:8080";
        String tallyPredictProbability="/predict/tallyPredictProbability.do";



     public void testGetPerformanceDetail(){
         String url=host + tallyPredictProbability + "?token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj&req=ajax&type=2&score=520&universityName=%E5%A4%A9%E6%B4%A5%E5%A4%A7%E5%AD%A6";
         String result = RequestUtils.requestPost(url);
     }


}
