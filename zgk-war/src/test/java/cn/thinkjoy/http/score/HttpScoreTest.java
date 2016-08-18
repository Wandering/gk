package cn.thinkjoy.http.score;

import cn.thinkjoy.common.RequestUtils;
import cn.thinkjoy.gk.common.SubjectEnum;
import junit.framework.TestCase;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by admin on 2016/1/14.
 */
public class HttpScoreTest extends TestCase{
//        String host="http://10.254.130.33:9080";
//        String host="http://10.136.56.195:8080";
//        String host="http://zjtest.zhigaokao.cn";
//        String host="http://localhost:8082";
        String host="http://localhost:8080";
//        String host="http://zgkser.zhigaokao.cn/";
//        String host="http://zj.dev.zhigaokao.cn";
//        String host="http://zj.test.zhigaokao.cn";
//        String host="http://10.136.67.121:8080";

        String base="/score/";


        String queryScoreRecordByUserId_url=base+"queryScoreRecordByUserId.do";
        String insertScoreRecord_url=base+"insertScoreRecord.do";
        String queryInfoByRecordId_url=base+"queryInfoByRecordId.do";
        String queryAllRecordByUserId_url=base+"queryAllRecordByUserId.do";
        String queryBatchLineByAreaId_url=base+"queryBatchLineByAreaId.do";
        String recommendSchool_url=base+"recommendSchool.do";
        String queryBatchsBySchoolIdAndAreaId_url=base+"queryBatchsBySchoolIdAndAreaId.do";
        String queryGapBySchoolIdAndBatch_url=base+"queryGapBySchoolIdAndBatch.do";
        String insertUserInfo_url=base+"insertUserInfo.do";
        String queryHighSchoolByCountyId_url=base+"queryHighSchoolByCountyId.do";
        String queryUserInfo_url=base+"queryUserInfo.do";
        String queryUniversityScore_url=base+"queryUniversityScore.do";
        String querySubjectByGrade_url=base+"querySubjectByGrade.do";



//    public void testInsertUserInfo(){
//        String url=host + insertUserInfo_url + "?userId=1&provinceId=610000&cityId=610100&countyId=610102" +
//                "&schoolCode=6101024000&schoolName="+ URLEncoder.encode("西安二十九中")+"&gradeInfo="+URLEncoder.encode("高三")+"&classInfo="+URLEncoder.encode("一班")+"" +
//                "&source=1\n";
//        String result = RequestUtils.requestPost(url);
//        System.out.println("result = " + result);
//
//    }
    public void testQueryHighSchoolByCountyId(){
        String url=host + queryHighSchoolByCountyId_url + "?countyId=110101&schoolName=\n";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);

    }

    /**
     * ---
     */
    public void testQueryUserInfo(){
        String url=host + queryUserInfo_url + "?userId=1\n";
        System.out.println();
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);

    }






//
//    public void testInsertScoreRecord(){
//        String url=host + insertScoreRecord_url + "?userId=1&source=1&areaId=&majorType=&scores\n";
//        String result = RequestUtils.requestPost(url);
//        System.out.println("result = " + result);
//
//    }

    /**
     * ok
     */
     public void testQueryScoreRecordByUserId(){
         String url=host + queryScoreRecordByUserId_url + "?userId=1\n";
         System.out.println(url);
         String result = RequestUtils.requestGet(url);
         System.out.println("result = " + result);


     }

    /**
     * ok
     */
    public void test2(){
        SubjectEnum.valueOf("sx").getSub();
    }

    /**
     * ok
     */
    public void testQueryInfoByRecordId(){
        String url=host +queryInfoByRecordId_url + "?recordId=244";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryAllRecordByUserId(){
        String url=host +queryAllRecordByUserId_url + "?userId=1";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryBatchLineByAreaId(){
        String url=host +queryBatchLineByAreaId_url + "?totalScore=60&areaId=320000";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryGapBySchoolIdAndBatch(){
        String url=host +queryGapBySchoolIdAndBatch_url + "?recordId=478&schoolId=24606&batch=1&userId=24606&debug=true";
        String result = RequestUtils.requestPost(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryBatchsBySchoolIdAndAreaId(){
        String url=host +queryBatchsBySchoolIdAndAreaId_url + "?areaId=530000&schoolId=2317&majorType=1";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ---
     */
    public void testRecommendSchool(){
        String url=host +recommendSchool_url + "?totalScore=400&areaId=330000&userId=1";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }


    /**
     * ok
     */
    public void testQueryUniversityScore(){
        String url=host +queryUniversityScore_url + "?universityId=1&areaId=610000&majorType=1&batch=2";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQuerySubjectByGrade(){
        String url=host +querySubjectByGrade_url + "?userId=13582&subject=语文";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }
}
