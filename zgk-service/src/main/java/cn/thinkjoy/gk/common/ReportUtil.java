package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.pojo.SelfReportResultView;
import com.alibaba.dubbo.common.utils.StringUtils;
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
     * 排名法  --- 细分规则 KEY
     */
    public static final String VOLUNTEER_BATCH_PRECEDENCE_KEY="VOLUNTEER_BATCH_PRECEDENCE";
    /**
     * 排名法 ---位次阀值
     */
    public static final String VOLUNTEER_RANKING_VALUE_KEY="RANKING_VALUE";
    /**
     * 分数补充法 开关
     */
    public static final String SCORE_ROLE_KEY="SCORE_ROLE";
    /**
     * 分数补充法 批次设置
     */
    public static final String SCORE_BATCH_ROLE_KEY="SCORE_BATCH_ROLE";
    /**
     * 批次线追加分数
     */
    public static final String CON_LINE_PLUS_VALUE_KEY="CON_LINE_PLUS_VALUE";
    /**
     * 专业限制数 ---用于完整性判断
     */
    public static final String SPECIALITY_NUMVER="SPECIALITY_NUMVER";
    /**
     * 专业调剂限制数 ---用于完整性判断
     */
    public static final String SWAP_NUMBER="SWAP_NUMBER";
    /**
     * 冲稳保垫
     */
    public static final String CLASSIFY_TAG_KEY="CLASSIFY_TAG";

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

    /*****************************************排名法util*****************************************/
    /**
     * 获取排名法  --细分规则key
     * @return
     */
    public static String getPrecedenceRuleKey(String provinceCode) {
        return provinceCode.toUpperCase() + ROLE_KEY_SPLIT_SYMBOL + VOLUNTEER_BATCH_PRECEDENCE_KEY;
    }

    /**
     * 获取当前位次符合的排名规则区间下标
     * @return
     */
    public static Integer getRankingRuleIndex(String rankingRuleStr,Integer precedence) {
        if (precedence <= 0)
            return -1;
        String[] rankingRuleArr = rankingRuleStr.split(VOLUNTEER_KEY_SPLIT_SYMBOL);
        for (int i = 0; i < rankingRuleArr.length; i++) {
            String rankStr = rankingRuleArr[i];
            String[] rankRangeArr = rankStr.split(ROLE_VALUE_SPLIT_SYMBOL);
            Integer rankStar = Integer.valueOf(rankRangeArr[0]);
            Integer rankEnd = Integer.valueOf(rankRangeArr[1]);
            if (precedence > rankStar && precedence <= rankEnd)
                return i;
        }
        return -1;
    }

    /**
     * 根据批次获取  批次配置下标
     * @param batch
     * @param rankingRuleStr
     * @return
     */
    public static Integer  getRankingRuleIndex(Integer batch,String rankingRuleStr) {
        if (batch <= 0)
            return -1;
        String[] rankingRuleArr = rankingRuleStr.split(VOLUNTEER_KEY_SPLIT_SYMBOL);
        for (int i = 0; i < rankingRuleArr.length; i++) {
            String batchStr = rankingRuleArr[i];

            if (Integer.valueOf(batchStr).equals(batch))
                return i;
        }
        return -1;
    }

    /**
     * 拆分规则串 为Array
     * @param str
     * @param symbol
     * @return
     */
    public static  ArrayList<Integer>  strSplit(String str,String symbol) {
        if (StringUtils.isBlank(str))
            return null;
        ArrayList<Integer> roleArr = new ArrayList<>();
        String[] roleStrArr = str.split(symbol);
        for (String roleStr : roleStrArr) {
            roleArr.add(Integer.valueOf(roleStr));
        }
        return roleArr;
    }
    /*****************************************排名法util*****************************************/
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

    public static void main(String[] arg) throws IOException {
        String s="[{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"268\",\"averageScore\":\"0\",\"enrollRate\":\"100%\",\"name\":\"上海金融学院\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"上海市\",\"type\":\"8\",\"typeName\":\"财经\"},\"sequence\":1},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"1415\",\"averageScore\":\"530\",\"enrollRate\":\"100%\",\"name\":\"西北政法大学\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"陕西省\",\"type\":\"9\",\"typeName\":\"政法\"},\"sequence\":2},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"837\",\"averageScore\":\"554\",\"enrollRate\":\"100%\",\"name\":\"湖南财政经济学院\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"湖南省\",\"type\":\"8\",\"typeName\":\"财经\"},\"sequence\":3},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"465\",\"averageScore\":\"0\",\"enrollRate\":\"100%\",\"name\":\"上海工程技术大学\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"上海市\",\"type\":\"2\",\"typeName\":\"工科\"},\"sequence\":4},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"555\",\"averageScore\":\"0\",\"enrollRate\":\"100%\",\"name\":\"三峡大学\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"湖北省\",\"type\":\"1\",\"typeName\":\"综合\"},\"sequence\":5},{\"selfReportUniversityViewList\":{\"enrollingNumber\":\"1633\",\"averageScore\":\"0\",\"enrollRate\":\"100%\",\"name\":\"河北经贸大学\",\"isComplied\":\"0\",\"selfReportMajorViewList\":[{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"},{\"id\":0,\"name\":\"\"}],\"property\":\"无\",\"subjection\":\"河北省\",\"type\":\"8\",\"typeName\":\"财经\"},\"sequence\":6}]";
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


}
