package cn.thinkjoy.gk.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clei on 15/8/8.
 */
public class ServletPathConst {

    public static Map<String,String> MAPPING_URLS = new HashMap<String,String>();

    public static Map<String,String> JSP_URLS = new HashMap<String,String>();

    static{
        MAPPING_URLS.put("/question/insert.do","");
        MAPPING_URLS.put("/answer/findMyQuestion.do","");
        MAPPING_URLS.put("/vip/upgradeVipByCard.do","");
        MAPPING_URLS.put("/vip/getAccount.do","");
        MAPPING_URLS.put("/appraisal/lstest.do","");
        MAPPING_URLS.put("/appraisal/findRanking.do","");
        MAPPING_URLS.put("/appraisal/schoolTest.do","");
        MAPPING_URLS.put("/before/collegeRecommend/getCollegeList.do","");
        MAPPING_URLS.put("/appointment/getAppointment.do","");
        MAPPING_URLS.put("/appointment/addAppointment.do","");
        MAPPING_URLS.put("/appointment/getAppointmentDetail.do","");
        MAPPING_URLS.put("/info/updateUserInfo.do","");
        MAPPING_URLS.put("/info/getUserInfo.do","");
        MAPPING_URLS.put("/info/getUserAccount.do","");
        MAPPING_URLS.put("/info/modifyPassword","");
        MAPPING_URLS.put("/info/confirmPassword","");
        MAPPING_URLS.put("/orders/createOrders.do","");
        MAPPING_URLS.put("/product/findProductPage.do","");
        MAPPING_URLS.put("/product/findProduct.do","");
        MAPPING_URLS.put("/guide/batch.do","");
        MAPPING_URLS.put("/guide/school.do","");
        MAPPING_URLS.put("/guide/report.do","");


        JSP_URLS.put("/guide/guide.jsp","");
    }
}
