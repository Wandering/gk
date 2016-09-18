package cn.thinkjoy.http.score;

import cn.thinkjoy.common.RequestUtils;
import cn.thinkjoy.gk.common.SubjectEnum;
import junit.framework.TestCase;
import net.esoar.modules.utils.ThreadUtils;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
//            String host="http://fj.zhigaokao.cn/";

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
        String url=host +queryGapBySchoolIdAndBatch_url + "?recordId=1030&schoolId=1155&batch=8&userId=227&debug=true";
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
        String url=host +recommendSchool_url + "?totalScore=15&areaId=110000&majorType=1&userId=1";
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

        String url=host +queryMajorBySchoolIdAndAreaId_url + "?userId=216&areaId=330000&schoolId=94";
        System.out.println(url);
        String result = RequestUtils.requestGet(url);
        System.out.println("result = " + result);
    }


    /**
     * ok
     */
    public void testQueryGapBySchoolIdAndMajor(){
        String url=host +queryGapBySchoolIdAndMajor_url + "?userId=216&schoolId=94&majorCode=80202&recordId=759";
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
    /**
     *
     */
    public void testRecommendSchool1(){

        Object[] strings=new Object[]{
                220000,
                230000,
                310000,
                320000,
                330000,
                340000,
                350000,
                360000,
                370000,
                410000,
                420000,
                430000,
                440000,
                450000,
                460000,
                500000,
                510000,
                520000,
                530000,
                540000,
                610000,
                620000,
                630000,
                640000,
                650000,
                710000,
                810000,
                820000

        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();

                for(Object areaId : strings) {

                    executorService.submit(new Run1(areaId.toString()));


                }

                while (!executorService.isShutdown()){

                }
        System.out.println("end");

    }

    class  Run1 implements  Runnable{
        private Object areaId;
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            StringBuffer buffer = new StringBuffer();
            for(int j =1;j<=2;j++)

                for (int i = 1; i <= 700; i++) {
                    String url = "http://zj.test.zhigaokao.cn/score/recommendSchool.do?totalScore=" + i + "&areaId="+areaId+"&majorType="+j+"&userId=221\n";
                    String result = requestGet(url);

                    if (result.indexOf("1000001") > 0 || result.indexOf("[]")>0) {
                        buffer.append(i + "-"+areaId+"-"+j+",");
                    }
                }
            System.out.println(buffer.toString());
        }

        public  Run1(String areaId) {
            this.areaId=areaId;
        }

        public String requestGet(String url){
            String result = "";
            try {
                URL url1= new URL(url);

                HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String lines;
                StringBuffer sb = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sb.append(lines);
                }
                result = sb.toString();
                reader.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        System.out.println("Response data:" + result);
            return result;
        }



    }

    /**
     * ---
     */
    public void testBatchs(){
        String batch="11";
        getBatchs(batch);
    }

    private String[] getBatchs(String batch){
        if(batch.length()>1){
            batch=batch.substring(0,1);
        }
        //组织11,12,13,14,1
        String[] strings = new String[5];
        for(int i =1;i<=4;i++) {
            strings[i-1]=batch+""+i;
            System.out.println(batch+""+i);
        }
        strings[4]=batch;
        return strings;
    }
}

