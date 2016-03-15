package cn.thinkjoy.gk.common;

import java.util.ArrayList;

/**
 * Created by douzy on 16/3/14.
 */
public class ReportUtil {
    /**
     * 批次控制线 后缀标识
     */
    public static final String BATCHLINEKEY = "CONTROL_LINES";

    /**
     * 录取率筛选规则
     */
    public static final String THRESHOLD_ENROLL_KEY="THRESHOLD_ENROLL";

    /**
     * 利用率筛选规则
     */
    public static final String THRESHOLD_USED_KEY="THRESHOLD_USED";
    /**
     * 录取率梯度筛选规则
     */
    public static final String VOLUNTEER_ENROLL_KEY="VOLUNTEER_ENROLL";
    /**
     * 利用率梯度筛选规则
     */
    public static final String VOLUNTEER_USED_KEY="VOLUNTEER_USED";

    /**
     * 规则值拆分符
     */
    public static final String ROLE_VALUE_SPLIT_SYMBOL="\\-";

    /**
     * 规则KEY拆分符
     */
    public static final String ROLE_KEY_SPLIT_SYMBOL="_";

    /**
     * 梯度规则分隔符
     */
    public static final String VOLUNTEER_KEY_SPLIT_SYMBOL="\\|";
    /**
     * 组装表名
     * @param provinceCode
     * @param categorie
     * @param score
     * @return
     */
    public static String getTableName(String provinceCode,Integer categorie,Integer score) {

        return provinceCode + "_" + score + "_" + categorie;
    }

    /**
     * 获取指定梯度规则
     * @param configValue  梯度规则串
     * * @param sequence   获取第几级梯度
     * @return
     */
    public static ArrayList<Integer> getVolunteer(String configValue,Integer sequence) {
        ArrayList<Integer> resultVolunteer = new ArrayList<>();
        if (sequence <= getVolunteerCount(configValue)) {
            String[] volunteerArr = configValue.split(VOLUNTEER_KEY_SPLIT_SYMBOL);
            String[] seqArr = volunteerArr[(sequence-1)].split(ROLE_VALUE_SPLIT_SYMBOL);
            for (String str : seqArr) {
                resultVolunteer.add(Integer.valueOf(str));
            }
        }
        return resultVolunteer;
    }

    /**
     * 获取梯度总数  --总共分几级梯度
     * @return
     */
    public static Integer getVolunteerCount(String configValue) {
        return (configValue.split(VOLUNTEER_KEY_SPLIT_SYMBOL)).length;
    }

    public static void main(String[] arg)
    {
        String s="30-50|30-50|60-80|60-80|80-100|80-100";
        String[] arr= s.split(VOLUNTEER_KEY_SPLIT_SYMBOL);
        System.out.print( arr.length);
    }
}
