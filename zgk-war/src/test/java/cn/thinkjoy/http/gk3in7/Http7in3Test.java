package cn.thinkjoy.http.gk3in7;

import cn.thinkjoy.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */
public class Http7in3Test extends TestCase {
    //        String host="http://10.254.130.33:9080";
//        String host="http://zjtest.zhigaokao.cn";
//        String host="http://10.136.13.245:8086";
    String host = "http://localhost:8080";

    /**
     * 通过专业查政策
     */
    String getUniversityByArea = "/subjectByMajor/getUniversityByArea.do";
    String getMajorByUniversityId = "/subjectByMajor/getMajorByUniversityId.do";
    String getSubjectByMajor = "/subjectByMajor/getSubjectByMajor.do";
    public void testGetUniversityByArea() {
        String url = host + getUniversityByArea + RequestUtils.genParam("page=2","rows=10","areaId=110000","universityName=北京");
        String result = RequestUtils.requestGet(url);
    }

    public void testGetMajorByUniversityId() {
        String url = host + getMajorByUniversityId + RequestUtils.genParam("universityId=11418","majorName=金融学");
        String result = RequestUtils.requestGet(url);
    }

    public void testGetSubjectByMajor() {
        String url = host + getSubjectByMajor + RequestUtils.genParam("majorId=784");
        String result = RequestUtils.requestGet(url);
    }
    /**
     * 通过课程查政策
     */
    String querySubjectByMajor = "/majorBySubject/queryMajorBySubject.do";
    public void testQuerySubjectByMajor() {
        String url = host + querySubjectByMajor + RequestUtils.genParam("page=4","rows=10","subjects=物理","subjects=化学","subjects=生物","areaId=110000,330000","universityName=北京城市学院","userKey=sn");
        String result = RequestUtils.requestGet(url);
    }


    String insertFavorites = "/favorites/insertFavorites.do";
    String getFavoritesByMajor = "/favorites/getFavoritesByMajor.do";
    String getFavoritesBySubjectKey = "/favorites/getFavoritesBySubjectKey.do";
    String getFavoritesBySubject = "/favorites/getFavoritesBySubject.do";
    String removeFavorites = "/favorites/removeFavorites.do";
    String removeBySubjects = "/favorites/removeBySubjects.do";
    /**
     * 添加收藏
     */
    public void testInsertFavorites() {
        String url = host + insertFavorites + RequestUtils.genParam("majorId=784","type=1","&token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestPost(url);

        url = host + insertFavorites + RequestUtils.genParam("majorId=784","type=2","subjects=历史","subjects=物理","&token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        result = RequestUtils.requestPost(url);
    }

    /**
     * 收藏  -   通过专业获取收藏
     */
    public void testGetFavoritesByMajor() {
        String url = host + getFavoritesByMajor + RequestUtils.genParam("token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestGet(url);
    }

    /**
     * 获取专业组合key
     */
    public void testGetFavoritesBySubjectKey() {
        String url = host + getFavoritesBySubjectKey + RequestUtils.genParam("token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestGet(url);
    }

    /**
     * 通过专业获取收藏
     */
    public void testGetFavoritesBySubject() {
        String url = host + getFavoritesBySubject + RequestUtils.genParam("page=1","rows=10","subjects=%e5%8e%86%e5%8f%b2+-+%e7%89%a9%e7%90%86","token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestGet(url);
    }

    /**
     * 删除收藏
     */
    public void testRemoveFavorites() {
        String url = host + removeFavorites + RequestUtils.genParam("id=16","token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestPost(url);
    }

    /**
     * 删除通过专业组合
     */
    public void testRemoveBySubjects() {
            String url = host + removeBySubjects + RequestUtils.genParam("subjects=%e5%8e%86%e5%8f%b2+-+%e7%89%a9%e7%90%86","&token=GM%2BX0KMwbKG9fj19q5XN8Q%3D%3D&userKey=tj");
        String result = RequestUtils.requestPost(url);
    }
}
