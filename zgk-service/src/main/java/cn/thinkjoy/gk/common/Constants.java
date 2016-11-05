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
}
