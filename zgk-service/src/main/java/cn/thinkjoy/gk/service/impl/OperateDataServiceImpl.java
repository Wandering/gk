package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IOperateDataDAO;
import cn.thinkjoy.gk.service.IOperateDataService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yangguorong on 17/1/9.
 */
@Service("OperateDataServiceImpl")
public class OperateDataServiceImpl implements IOperateDataService{

    @Autowired
    private IOperateDataDAO operateDataDAO;

    @Override
    public List<Map<String, String>> getAliData(String startTime,String endTime) {

        // 获取点击图标总用户数
        Map<String,String> clickIconMap = convertMap(operateDataDAO.getClickIconCount(startTime,endTime));

        // 获取完善信息总用户数
        Map<String,String> perfectInfoMap = convertMap(operateDataDAO.getPerfectInfoCount(startTime,endTime));

        // 获取测评总用户数
        Map<String,String> evaluationMap = convertMap(operateDataDAO.getEvaluationCount(startTime,endTime));

        // 获取录取预测总用户数
        Map<String,String> enrollingPredictMap = convertMap(operateDataDAO.getEnrollingPredictCount(startTime,endTime));

        List<Map<String, String>> returnList = Lists.newArrayList();

        for (Map.Entry<String, String> entry : clickIconMap.entrySet()) {
            TreeMap<String,String> map = Maps.newTreeMap();
            map.put("日期",entry.getKey());
            map.put("点击图标总用户",entry.getValue());
            map.put("完善信息总用户",perfectInfoMap.get(entry.getKey()) == null?"0":String.valueOf(perfectInfoMap.get(entry.getKey())));
            map.put("测评总用户",evaluationMap.get(entry.getKey()) == null?"0":String.valueOf(evaluationMap.get(entry.getKey())));
            map.put("录取预测总用户",enrollingPredictMap.get(entry.getKey()) == null?"0":String.valueOf(enrollingPredictMap.get(entry.getKey())));
            returnList.add(map);
        }

        return returnList;
    }

    /**
     * 将list转为map
     *
     * @param list
     * @return
     */
    private Map<String,String> convertMap(List<Map<String,String>> list){

        Map<String,String> returnMap = Maps.newHashMap();

        if(list == null || list.size() == 0){
            return returnMap;
        }

        for(Map<String,String> map : list){
            returnMap.put(map.get("tim"),map.get("total"));
        }

        return returnMap;
    }

}
