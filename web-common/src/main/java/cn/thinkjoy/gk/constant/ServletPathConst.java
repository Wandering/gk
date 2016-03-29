package cn.thinkjoy.gk.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clei on 15/8/8.
 */
public class ServletPathConst {

    public static List<String> MAPPING_URLS = new ArrayList<String>();

    public static List<String> JSP_URLS = new ArrayList<String>();

    static{
        MAPPING_URLS.add("/question/insert.do");
        MAPPING_URLS.add("/answer/findMyQuestion.do");
        MAPPING_URLS.add("/vip/upgradeVipByCard.do");
        MAPPING_URLS.add("/vip/getAccount.do");
        MAPPING_URLS.add("/appraisal/lstest.do");
        MAPPING_URLS.add("/appraisal/findRanking.do");
        MAPPING_URLS.add("/appraisal/schoolTest.do");
        MAPPING_URLS.add("/before/collegeRecommend/getCollegeList.do");
        MAPPING_URLS.add("/appointment/getAppointment.do");
        MAPPING_URLS.add("/appointment/addAppointment.do");
        MAPPING_URLS.add("/appointment/getAppointmentDetail.do");
        MAPPING_URLS.add("/info/updateUserInfo.do");
        MAPPING_URLS.add("/info/getUserInfo.do");
        MAPPING_URLS.add("/info/getUserAccount.do");
        MAPPING_URLS.add("/info/modifyPassword");
        MAPPING_URLS.add("/info/confirmPassword");
        MAPPING_URLS.add("/orders/createOrders.do");
        MAPPING_URLS.add("/product/findProductPage.do");
        MAPPING_URLS.add("/product/findProduct.do");
        MAPPING_URLS.add("/guide/batch.do");
        MAPPING_URLS.add("/guide/school.do");
        MAPPING_URLS.add("/guide/report.do");
        MAPPING_URLS.add("/exam/updateUserExam.do");
        MAPPING_URLS.add("/exam/findUserExam.do");

        MAPPING_URLS.add("/predict/predictProbability.do");
        MAPPING_URLS.add("/predict/predictSchoolList.do");
        MAPPING_URLS.add("/apesk/queryApeskUrl.do");
        //杨永平--start
        MAPPING_URLS.add("/video/getGkVideoInfo.do");
        MAPPING_URLS.add("/forecast/getPerformanceDetail.do");
        MAPPING_URLS.add("/forecast/getLastoFrecast.do");
        MAPPING_URLS.add("/forecast/getLastoFrecast.do");
        MAPPING_URLS.add("/forecast/getFormerYearsAdmission.do");
        MAPPING_URLS.add("/forecast/addFrecast.do");
        //杨永平--end

        MAPPING_URLS.add("/userCollection/saveUserCollect.do");
        MAPPING_URLS.add("/userCollection/getUserCollectList.do");
        MAPPING_URLS.add("/userCollection/deleteUserCollect.do");
        MAPPING_URLS.add("/userCollection/isUniversityCollect.do");
        //智能填报
        MAPPING_URLS.add("/report/get/batch.do");
        MAPPING_URLS.add("/report/get/specialty.do");
        MAPPING_URLS.add("/report/get/info.do");
        MAPPING_URLS.add("/report/main.do");
        MAPPING_URLS.add("/report/save.do");

//        JSP_URLS.put("/guide/guide.jsp","");
        JSP_URLS.add("/user/personal-info.jsp");
        JSP_URLS.add("/user/modify-psd.jsp");
        JSP_URLS.add("/user/app-center.jsp");
        JSP_URLS.add("/user/online-answer.jsp");
        JSP_URLS.add("/user/expert-service.jsp");
        JSP_URLS.add("/question/ask.jsp?path=online");
        JSP_URLS.add("/forward.do");
        JSP_URLS.add("/user/online-answer.jsp?method=ask");
        JSP_URLS.add("/user/vip-service.jsp");
//        JSP_URLS.put("/question/question_detile.jsp","");


    }
}
