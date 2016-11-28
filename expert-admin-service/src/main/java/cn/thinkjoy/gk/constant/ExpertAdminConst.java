package cn.thinkjoy.gk.constant;

public class ExpertAdminConst {
    /**
     * 用户cookieKey
     */
    public static final String USER_COOKIE_KEY = "expert_user_info";
    /**
     * 用户cookie过期时间设置 4小时
     */
    public static final Integer USER_COOKIE_TIMEOUT = 4*60*60;
    /**
     * 统一编码格式
     */
    public static final String CHARSET = "UTF-8";




    public static final String CAPTCHA_AUTH_TIME_KEY = "gk_captcha_auth_time_";

    public static final String USER_CAPTCHA_KEY = "gk_user_captcha_";

    public static final String ZGK = "zgk";
    /**
     * 不拦截的URL
     */
    public static final String [] NoFilter_Pages = {"/expert/admin/login*.*"};

    public static final String LOGIN_PATH = "/expert/admin/login.do";

    public static final String USER_IMAGE_CAPTCHA_KEY = "gk_captcha_auth_time_";

    public static final String REPORT_URL = "http://www.apesk.com/mensa/common_report_getid/holland2_report_getid.asp?id=";

    /**
     *订单未预约
     */
    public static final int EXPERT_ORDER_STATUS_N = 0;

    /**
     *预约成功
     */
    public static final int EXPERT_ORDER_STATUS_Y1 = 1;

    /**
     *服务中
     */
    public static final int EXPERT_ORDER_STATUS_Y2 = 2;
    /**
     *服务结束
     */
    public static final int EXPERT_ORDER_STATUS_Y3 = 3;
    /**
     *服务结束+已评价
     */
    public static final int EXPERT_ORDER_STATUS_Y4 = 4;

    public final static String formatString = "yyyy-MM-dd HH:mm";

    public final static Integer CHECK_TRUE = 1;

}
