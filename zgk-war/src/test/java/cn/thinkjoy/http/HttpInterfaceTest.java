package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpInterfaceTest extends TestCase{
//        String host="http://10.254.130.33:9080";
        String host="http://localhost:8080";
        String getGkAdmissionLineList_url="/admissionline/getGkAdmissionLineList.do";
        String getScheduleList_url="/schedule/getScheduleList.do";
        String getScheduleInfo_url="/schedule/getScheduleInfo.do";
        String getPolicyList_url="/policy/getPolicyList.do";
        String getPolicyInfo_url="/policy/getPolicyInfo.do";
        String getGkVideoInfo_url="/video/getGkVideoInfo.do";
        String hitInc_url="/video/hitInc.do";
        String getGkVideoList_url="/video/getGkVideoList.do";
        String getGkHotInfo_url="/gkhot/getGkHotInfo.do";
        String getGkHotList_url="/gkhot/getGkHotList.do";
        String getGkPhoneList_url="/phone/getGkPhoneList.do";
        String getGkAreaBatchInfo_url="/areabatch/getGkAreaBatchInfo.do";
        String getGkEntryList_url="/entry/getGkEntryList.do";
        String getGkEntryInfo_url="/entry/getGkEntryInfo.do";
        String predictProbability_url="/predict/predictProbability.do";
        String getProfessionalList_url="/professional/getProfessionalList.do" ;
        String getProfessionCategory_url="/professional/getProfessionCategory.do" ;
        public void testGetGkAdmissionLineList(){

            long start=System.currentTimeMillis();
            String url=host + getGkAdmissionLineList_url + "?batch=1&type=2&page=&rows=10&userKey=zj";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("一批本科"));
            url=host + getGkAdmissionLineList_url + "?batch=2&type=1&page=&rows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("理工"));
            url=host + getGkAdmissionLineList_url + "?batch=2&type=2&page=&rows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("文史"));
            System.out.print(System.currentTimeMillis()-start);
        }

        public void testGetScheduleList(){
            String url=host + getScheduleList_url + "?month=9&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("9"));
            url=host + getScheduleList_url + "?month=10&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("10"));
            url=host + getScheduleList_url + "?month=11&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("11"));
            url=host + getScheduleList_url + "?month=12&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("12"));
            url=host + getScheduleList_url + "?month=1&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("1"));
            url=host + getScheduleList_url + "?month=2&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("2"));
            url=host + getScheduleList_url + "?month=3&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("3"));
            url=host + getScheduleList_url + "?month=4&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("4"));
            url=host + getScheduleList_url + "?month=5&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("5"));
            url=host + getScheduleList_url + "?month=6&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("6"));
            url=host + getScheduleList_url + "?month=7&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("7"));
            url=host + getScheduleList_url + "?month=8&rows=12&isIndex=true&scheduleRows=10&userKey=zj";
            result = RequestUtils.requestGet(url);
            assertTrue(result.contains("8"));

        }

        public void testGetScheduleInfo(){
                String url=host + getScheduleInfo_url + "?id=12";
                String result = RequestUtils.requestGet(url);
                assertTrue(result.contains("2015"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
        }

    public void testGetPolicyList(){
        String url=host + getPolicyList_url + "?queryparam=&page=&rows=";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("2015"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetPolicyInfo(){
        String url=host + getPolicyInfo_url + "?id=38";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("摘要"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetGkHotInfo(){
        String url=host + getGkHotInfo_url + "?id=280";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("280"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testHitIncInfo(){
        String url=host + hitInc_url + "?id=1&userKey=zj";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("280"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetGkHotList(){
        String url=host + getGkHotList_url + "&userKey=zj";
        String result = RequestUtils.requestGet(url);
//        assertTrue(result.contains("2016"));
        url=host + getGkHotList_url + "?type=1";
        result = RequestUtils.requestGet(url);
        url=host + getGkHotList_url + "?type=1&page=3&rows=4";
        result = RequestUtils.requestGet(url);
//        assertTrue(result.contains("2016"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetGkVideoList(){
        String url=host + getGkVideoList_url + "?isIgnore=true&page=1&debug=true";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("2016"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }
    public void testGkVideoInfo(){
        String url=host + getGkVideoInfo_url + "?id=95";
        System.out.print(url);
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("2016"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

//    public void testGkVideoInfo(){
//        String url=host + getGkVideoInfo_url + "?id=29";
//        System.out.print(url);
//        String result = RequestUtils.requestGet(url);
//        assertTrue(result.contains("2016"));
////                assertTrue(result.contains("01"));
////                assertTrue(result.contains("12"));
////                assertTrue(result.contains("11"));
////                assertTrue(result.contains("2015"));
////            assertTrue(result.contains("一批本科"));
////            assertTrue(result.contains("985/211"));
////            assertTrue(result.contains("教育部"));
////                assertTrue(result.contains("33"));
////            assertTrue(result.contains("文史"));
//    }
    public void testGkPhoneList(){
        String url=host + getGkPhoneList_url + "";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("2016"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetGkAreaBatchInfo(){
        String url=host + getGkAreaBatchInfo_url + "?areaId=110000";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("110000"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testGetGkEntryList(){
        String url=host + getGkEntryList_url + "?name=词条";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("summary"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }
    public void testGetGkEntryInfo(){
        String url=host + getGkEntryInfo_url + "?id=87";
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("87"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testPredictProbability(){
        String url=host + predictProbability_url + "?universityName=测试&score=&type=";
//        String result = RequestUtils.requestGet(url);
        String result = RequestUtils.requestPost(url);
        assertTrue(result.contains("87"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }

    public void testPredictProbability1(){
        String url=host + getProfessionalList_url + "?debug=true";
//        String result = RequestUtils.requestGet(url);
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("87"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }
    public void testGetProfessionCategory(){
        String url=host + getProfessionCategory_url + "?pid=80&debug=true";
//        String result = RequestUtils.requestGet(url);
        String result = RequestUtils.requestGet(url);
        assertTrue(result.contains("87"));
//                assertTrue(result.contains("01"));
//                assertTrue(result.contains("12"));
//                assertTrue(result.contains("11"));
//                assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
    }
}
