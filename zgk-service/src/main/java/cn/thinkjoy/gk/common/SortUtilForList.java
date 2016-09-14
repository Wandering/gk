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

        Integer isFavorite_o1=o1.get("isFavorite").toString() == null?null:Integer.parseInt(o1.get("isFavorite").toString());
        Integer isFavorite_o2=o2.get("isFavorite").toString() == null?null:Integer.parseInt(o2.get("isFavorite").toString());

        Integer result=isCurrArea_o2.compareTo(isCurrArea_o1);
        if(result==0)
            result=isFavorite_o2.compareTo(isFavorite_o1);
        if(result==0)
            result=enrollRate_o2.compareTo(enrollRate_o1);
        return  result;
    }

}