package cn.thinkjoy.http.gk3in7;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class Http7in3Test extends TestCase {
    //        String host="http://10.254.130.33:9080";
//        String host="http://zjtest.zhigaokao.cn";
//        String host="http://localhost:8080";
    String host = "http://localhost:8080";

    /**
     * 通过专业查政策
     */
    String getUnversityByArea = "/majorBySubject/getUnversityByArea.do";
    String getMajorByUnversityId = "/majorBySubject/getMajorByUnversityId.do";
    String getSubjectByMajor = "/majorBySubject/getSubjectByMajor.do";
    public void testGetUnversityByArea() {
        String url = host + getUnversityByArea + RequestUtils.genParam("areaId=110000","unversityName=北京");
        String result = RequestUtils.requestGet(url);
    }

    public void testGetMajorByUnversityId() {
        String url = host + getMajorByUnversityId + RequestUtils.genParam("universityId=11418","majorName=金融学");
        String result = RequestUtils.requestGet(url);
    }

    public void testGetSubjectByMajor() {
        String url = host + getSubjectByMajor + RequestUtils.genParam("majorId=784");
        String result = RequestUtils.requestGet(url);
    }
    /**
     * 通过课程查政策
     */
    String querySubjectByMajor = "/subjectByMajor/querySubjectByMajor.do";
    public void testQuerySubjectByMajor() {
        String url = host + querySubjectByMajor + RequestUtils.genParam("subjectItem=物理","subjectItem=化学","areaId=110000,330000","unversityName=北京");
        String result = RequestUtils.requestGet(url);
    }

    /**
     * 通过课程查政策
     */
    String insertFavorites = "/favorites/insertFavorites.do";
    public void testInsertFavorites() {
        String url = host + insertFavorites + RequestUtils.genParam("majorId=784","type=1","areaId=110000,330000");
        String result = RequestUtils.requestGet(url);

        url = host + insertFavorites + RequestUtils.genParam("majorId=784","type=2","subjects=110000");
        result = RequestUtils.requestGet(url);
    }

}
