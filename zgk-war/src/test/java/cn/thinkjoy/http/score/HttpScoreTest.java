package cn.thinkjoy.http.score;

import cn.thinkjoy.common.RequestUtils;
import cn.thinkjoy.gk.common.SubjectEnum;
import junit.framework.TestCase;

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
        String queryMajorBySchoolIdAndAreaId_url=base+"queryMajorBySchoolIdAndAreaId.do";
        String queryGapBySchoolIdAndMajor_url=base+"queryGapBySchoolIdAndMajor.do";
        String queryHistoryScore_url=base+"queryHistoryScore.do";



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
         String url=host + queryScoreRecordByUserId_url + "?userId=221\n";
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
        String url=host +queryInfoByRecordId_url + "?recordId=550";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryAllRecordByUserId(){
        String url=host +queryAllRecordByUserId_url + "?userId=1&debug=true";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryBatchLineByAreaId(){
        String url=host +queryBatchLineByAreaId_url + "?totalScore=60&areaId=330000";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryGapBySchoolIdAndBatch(){
        String url=host +queryGapBySchoolIdAndBatch_url + "?recordId=527&schoolId=1&batch=1&userId=308&debug=true";
        String result = RequestUtils.requestPost(url);
        System.out.println("result = " + result);
    }

    /**
     * ok
     */
    public void testQueryBatchsBySchoolIdAndAreaId(){
        String url=host +queryBatchsBySchoolIdAndAreaId_url + "?areaId=320000&schoolId=2317&majorType=1";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }

    /**
     * ---
     */
    public void testRecommendSchool(){
        long start=System.currentTimeMillis();
        System.out.println("我开始了");
        String url=host +recommendSchool_url + "?totalScore=400&areaId=320000&majorType=2&userId=309";
        System.out.println(url);
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
        long end=System.currentTimeMillis();
        System.out.println(end -start);
    }


    /**
     * ok
     */
    public void testQueryUniversityScore(){
        String url=host +queryUniversityScore_url + "?universityId=1&areaId=330000&majorType=1&batch=1";
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


    /**
     * ok
     */
    public void testQueryMajorBySchoolIdAndAreaId(){

        String url=host +queryMajorBySchoolIdAndAreaId_url + "?userId=217&areaId=330000&schoolId=2";
        System.out.println(url);
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }


    /**
     * ok
     */
    public void testQueryGapBySchoolIdAndMajor(){
        String url=host +queryGapBySchoolIdAndMajor_url + "?userId=2&schoolId=2&majorCode=030101K&recordId=244";
        String result = RequestUtils.requestPost(url);
        System.out.println("result = " + result);
    }


    /**
     *
     */
    public void testQueryHistoryScore(){
        String url=host +queryHistoryScore_url + "?userId=2&rows=3";
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }
}
