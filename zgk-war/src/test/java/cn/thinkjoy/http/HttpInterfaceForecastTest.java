package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpInterfaceForecastTest extends TestCase{
//        String host="http://10.254.130.33:9080";
//        String host="http://zjtest.zhigaokao.cn";
//        String host="http://localhost:8080";
        String host="http://localhost:8088";
        String getPerformanceDetail_url="/forecast/getPerformanceDetail.do";
        String getLastoFrecast_url="/forecast/getLastoFrecast.do";
        String getFormerYearsAdmission_url="/forecast/getFormerYearsAdmission.do";
        String addFrecast_url="/forecast/addFrecast.do";

        public void testGetPerformanceDetail(){
                String url=host + getPerformanceDetail_url + "";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("33"));
        }

        public void testGetLastoFrecast(){
                String url=host + getLastoFrecast_url + "";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("测试测试测试"));
        }

        public void testGetFormerYearsAdmission(){
                String url=host + getFormerYearsAdmission_url + "?universityid=47";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("averageScore"));
        }
        public void testAddFrecast(){
            String url=host + addFrecast_url + "?typeId=1&universityName=北京大学&achievement=1&lowestScore=1&averageScore=1&token=sdU1hVSXgr8zAFvH5A4pjg%3D%3D";
            String result = RequestUtils.requestPost(url);
            assertTrue(result.contains("true"));
        }


}
