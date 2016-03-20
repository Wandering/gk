package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.pojo.SelfReportResultView;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.*;

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
     * 往年院校年份
     */
    public static final String UNIVERSITY_ENROLL_YEAR_KEY="UNIVERSITY_ENROLL_YEAR";

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
     * 批次表后缀
     */
    public static final String PRECEDENCE_KEY_SYMBOL="w";

    /**
     * 组装表名   - 线差表
     * @param provinceCode
     * @param categorie
     * @param batch
     * @return
     */
    public static String getTableName(String provinceCode,Integer categorie,Integer batch,boolean isPrecedence) {
        if (isPrecedence)
            return provinceCode + ROLE_KEY_SPLIT_SYMBOL + categorie + ROLE_KEY_SPLIT_SYMBOL + batch + ROLE_KEY_SPLIT_SYMBOL + PRECEDENCE_KEY_SYMBOL;
        else
            return provinceCode + ROLE_KEY_SPLIT_SYMBOL + categorie + ROLE_KEY_SPLIT_SYMBOL + batch;
    }

    /**
     * 组装位次表名
     * @param provinceCode
     * @param categorie
     * @param batch
     * @param symbol
     * @return
     */
    public static String getTableName(String provinceCode,Integer categorie,Integer batch,String symbol) {
        return provinceCode + ROLE_KEY_SPLIT_SYMBOL + categorie + ROLE_KEY_SPLIT_SYMBOL + batch + ROLE_KEY_SPLIT_SYMBOL + PRECEDENCE_KEY_SYMBOL;
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

    /**
     * 获取位次
     * @return
     */
    public static Integer getPrecedence() {

        Integer result = 5;

        return result;
    }
    public static void main(String[] arg) throws IOException {
        String s="[{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":1},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":2},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":3},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":4},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":5},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"\",\"averageScore\":\"\",\"enrollRate\":\"\",\"name\":\"\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[],\"property\":\"\",\"subjection\":\"\",\"type\":\"\",\"typeName\":\"\"},\"sequence\":6}]";
        ObjectMapper mapper = new ObjectMapper();
        List<SelfReportResultView> selfReportResultViews = mapper.readValue(s, new TypeReference<List<SelfReportResultView>>(){});
         System.out.print(selfReportResultViews);
//         selfReportResultViews;
//        String[] arr= s.split(VOLUNTEER_KEY_SPLIT_SYMBOL);
////        System.out.print( arr.length);
//
//        Integer[] num=new Integer[]{900,1000,1500,2000,3000,10000,13000};
//
//
//         Integer a= binarysearchKey(num,10000);
    }


    /**
     * 查找最接近目标值的数，并返回
     * @param array
     * @param targetNum
     * @return
     */
    public static Integer binarysearchKey(Integer[] array, int targetNum) {
        Map<Integer, Integer> resultMap = new TreeMap<Integer, Integer>(
                new Comparator<Integer>() {
                    public int compare(Integer obj1, Integer obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                }
        );
        Integer result = 0;
        Arrays.sort(array);
        Integer starNum = array[0],
                endNum = array[(array.length - 1)];
        Integer[] xResult = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            if (targetNum <= starNum)
                return starNum;
            if (targetNum >= endNum)
                return endNum;
            Integer num1 = array[i];
            Integer x1 = Math.abs(targetNum - num1);
            xResult[i] = x1;
            resultMap.put(x1, array[i]);
        }

        for (Map.Entry<Integer, Integer> entry : resultMap.entrySet()) {
            result = entry.getValue();
            break;
        }
        return result;
    }

}
