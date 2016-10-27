package cn.thinkjoy.gk.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineAlgorithm {

    public static void main(String[] args) {

        String str[] = {};

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));

        Map<String,Object> selectSubjectMap=null;

        List<Map<String,Object>> mapList=null;

        List<List<Map<String,Object>>> lists=new ArrayList<>();
        for (int i = 1; i <= nBit; i++) {
            mapList=new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    selectSubjectMap=new HashMap<>();
                    selectSubjectMap.put("selectSubject",str[j]);
                    mapList.add(selectSubjectMap);
                }
            }
            lists.add(mapList);
        }
//        System.out.println(lists);
    }



}
