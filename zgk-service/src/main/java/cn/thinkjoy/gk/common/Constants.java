package cn.thinkjoy.gk.common;

/**
 * Created by yangguorong on 16/9/23.
 *
 * 常量类
 */
public class Constants {

    /**
     * 7门课程数组
     */
    public static final String [] sub_arr = {"物理","化学","历史","生物","通用技术","思想政治","地理"};

    /**
     * 薪资排行榜条数
     */
    public static final int salary_size = 8;

    /**
     *申请专家审核通过
     */
    public static final int EXPERT_APPLY_STATUS_Y = 1;
    /**
     *申请专家审核不通过
     */
    public static final int EXPERT_APPLY_STATUS_N = 2;


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
    /**
     * blank
     */
    public static final String EXPERT_ORDER_BLANK = " ";

    /**
     *时间不可用
     */
    public static final int EXPERT_TIME_N = 2;
    /**
     *时间可用
     */
    public static final int EXPERT_TIME_Y= 1;


    /******************  专家视频一对一相关常量  start  *****************/

    public static final String APP_KEY = "7620d1e00a3d45d2866f66c1178d1641";
    public static final String APP_SERCERT = "6e5c827dd8974b3babffe8bde0300de9";

    /**
     * 创建频道地址
     */
    public static final String CREATE_CHANNEL_URL = "https://vcloud.163.com/app/channel/create";

    /**
     * 删除频道地址
     */
    public static final String DELETE_CHANNEL_URL = "https://vcloud.163.com/app/channel/delete";

    /**
     * 获取频道状态地址
     */
    public static final String GET_CHANNEL_STATE_URL = "https://vcloud.163.com/app/channelstats";

    /******************  专家视频一对一相关常量  end  *****************/

    /**
     * 默认成绩分析类型状态
     */
    public static final int DEFULT_SCORE_RECORD_STATUS = 1;
}
