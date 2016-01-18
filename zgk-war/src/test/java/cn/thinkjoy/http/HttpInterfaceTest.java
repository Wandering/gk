package cn.thinkjoy.http;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpInterfaceTest extends TestCase{
        String host="http://localhost:8087";
        String getGkAdmissionLineList_url="/admissionline/getGkAdmissionLineList.do";
        String getScheduleList_url="/schedule/getScheduleList.do";
        String getScheduleInfo_url="/schedule/getScheduleInfo.do";
        String getPolicyList_url="/policy/getPolicyList.do";
        String getPolicyInfo_url="/policy/getPolicyInfo.do";
        String getGkVideoInfo_url="/video/getGkVideoInfo.do";
        String getGkVideoList_url="/video/getGkVideoList.do";
        String getGkHotInfo_url="/gkhot/getGkHotInfo.do";
        String getGkHotList_url="/gkhot/getGkHotList.do";
        public void testGetGkAdmissionLineList(){
                String url=host + getGkAdmissionLineList_url + "?queryparam=&year=&name=&areaId=&property=33&batch=&type=2&page=&rows=";
            String result = RequestUtils.requestGet(url);
//            assertTrue(result.contains("西北农林科技大学"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
            assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
        }

        public void testGetScheduleList(){
                String url=host + getScheduleList_url + "?rows=3";
                String result = RequestUtils.requestGet(url);
            assertTrue(result.contains("2016"));
            assertTrue(result.contains("01"));
            assertTrue(result.contains("12"));
            assertTrue(result.contains("11"));
            assertTrue(result.contains("2015"));
//            assertTrue(result.contains("一批本科"));
//            assertTrue(result.contains("985/211"));
//            assertTrue(result.contains("教育部"));
//                assertTrue(result.contains("33"));
//            assertTrue(result.contains("文史"));
        }

        public void testGetScheduleInfo(){
                String url=host + getScheduleInfo_url + "?id=6";
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
        String url=host + getPolicyInfo_url + "?id=44";
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

    public void testGetGkHotList(){
        String url=host + getGkHotList_url + "?type=&page=&rows=";
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
}
