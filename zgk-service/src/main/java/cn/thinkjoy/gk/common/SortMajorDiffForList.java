package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/14.
 */

public class SortMajorDiffForList implements Comparator<MajorDiffCompareRtn> {

    /**
     * 多维度map排序
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(MajorDiffCompareRtn o1, MajorDiffCompareRtn o2) {
        Integer isCurrArea_o1=o1.getIsCurrArea();
        Integer isCurrArea_o2=o2.getIsCurrArea();

        Integer rank_o1= o1.getRank();
        Integer rank_o2= o2.getRank();

        Integer result=isCurrArea_o2.compareTo(isCurrArea_o1);
        if(result==0)
            compareToRank(rank_o1,rank_o2);
        return  result;
    }

    private int compareToRank(Integer o1,Integer o2){
        if(o2==null && o1==null) return 0;
        if(o2==null && o1!=null) return 1;
        if(o2!=null && o1==null) return -1;
        return o2.compareTo(o1);
    }

}