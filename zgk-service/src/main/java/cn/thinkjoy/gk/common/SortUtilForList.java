package cn.thinkjoy.gk.common;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/14.
 */

public class SortUtilForList implements Comparator<Map<String,Object>> {

    /**
     * 多维度map排序
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Map<String,Object> o1, Map<String,Object> o2) {
        Integer enrollRate_o1=o1.get("enrollRate").toString() == null?null:Integer.parseInt(o1.get("enrollRate").toString());
        Integer enrollRate_o2=o2.get("enrollRate").toString() == null?null:Integer.parseInt(o2.get("enrollRate").toString());

        Integer isCurrArea_o1=o1.get("isCurrArea").toString() == null?null:Integer.parseInt(o1.get("isCurrArea").toString());
        Integer isCurrArea_o2=o2.get("isCurrArea").toString() == null?null:Integer.parseInt(o2.get("isCurrArea").toString());

        Integer isFavorite_o1=o1.get("isFavorite").toString() == null?null:Integer.parseInt(o1.get("isCurrArea").toString());
        Integer isFavorite_o2=o2.get("isFavorite").toString() == null?null:Integer.parseInt(o2.get("isCurrArea").toString());

        if (isCurrArea_o1!=isCurrArea_o2) {
            return compareWithCurrArea(isCurrArea_o1,isCurrArea_o2);
        } else if (isFavorite_o1!=isFavorite_o2) {
            return compareWithFavorite(isFavorite_o1,isFavorite_o2);
        } else if (enrollRate_o1!=enrollRate_o2) {
            return compareWithEnrollRate(enrollRate_o1,enrollRate_o2);
        }
        return 0;
    }

    /**
     * 通过学生的学号进行排序
     * @param id1
     * @param id2
     * @return
     */
    private int compareWithEnrollRate(int id1, int id2) {
        if (id1 > id2){
            return -1;
        }
        return 1;
    }

    /**
     * 通过学生的分数进行排序
     * @param score1
     * @param score2
     * @return
     */
    private int compareWithCurrArea(int score1, int score2) {
        if (score1 > score2){
            return -1;
        }
        return 1;
    }

    /**
     * 通过学生的分数进行排序
     * @param score1
     * @param score2
     * @return
     */
    private int compareWithFavorite(int score1, int score2) {
        if (score1 > score2){
            return -1;
        }
        return 1;
    }
}