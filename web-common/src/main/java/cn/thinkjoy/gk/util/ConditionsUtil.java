package cn.thinkjoy.gk.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zuohao on 16/1/8.
 */
public class ConditionsUtil {

    public static void setCondition(Map<String,Object> condition,String field,String op,String data){
        Map<String,Object> map= Maps.newHashMap();
        map.put("field",field);
        map.put("op",op);
        map.put("data",data);
        condition.put(field,map);
//        return condition;
    }
}
