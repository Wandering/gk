package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpInterfaceForecastTest extends TestCase{
//        String host="http://10.254.130.33:9080";
        String host="http://localhost:8080";
        String getPerformanceDetail_url="/forecast/getPerformanceDetail.do";
        String getLastoFrecast_url="/forecast/getLastoFrecast.do";
        String getFormerYearsAdmission_url="/forecast/getFormerYearsAdmission.do";

        public void testGetPerformanceDetail(){
                String url=host + getPerformanceDetail_url + "";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("33"));
        }

        public void testGetLastoFrecast(){
                String url=host + getLastoFrecast_url + "";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("33"));
        }

        public void testGetFormerYearsAdmission(){
                String url=host + getFormerYearsAdmission_url + "?universityid=&batch=";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("33"));
        }


}
