package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ScoreUtil;
import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.service.ISpecialScoreAnalysisService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yangyongping on 16/8/16.
 */
public class SpecialScoreAnalysisServiceImpl implements ISpecialScoreAnalysisService {




    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;
    @Autowired
    private ScoreUtil scoreUtil;
    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     *
     * @param userId
     * @return
     */
    @Override
    public Object queryScoreRecordByUserId(long userId) {
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        if (map == null) {
            return new HashedMap();
        }

        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("areaName", map.get("areaName"));
        Integer majorType = (Integer) map.get("majorType");
        resultMap.put("majorType", majorType);
        resultMap.put("schoolName", map.get("schoolName"));
        /**
         * 获取江苏成绩
         */
        Map<String, Object> scores = scoreUtil.getJSScores(map, majorType);
        resultMap.put("scores", scores);

        return resultMap;
    }

    /**
     * 添加用户成绩
     * @param userId
     * @param areaId
     * @param majorType
     * @param scores
     * @return
     */
    @Override
    public Map<String, Object> insertScoreRecord(long userId,
                                                 long areaId,
                                                 Integer majorType,
                                                 Map<String, Object> scores) {
        //获取成绩
        Map<String, Object> insertMap = new HashedMap();
        insertMap.put("userId", userId);
        insertMap.put("areaId", areaId);
        insertMap.put("majorType", majorType);
        insertMap.put("cdate", System.currentTimeMillis());
        Map<String, Object> insertScores = new HashedMap();

        Iterator iterator = scores.keySet().iterator();
        Float totalScore = 0f;
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = (String) scores.get(key);
            //某些特殊处理
            if(true){
                //假如Key计算不在成绩统计范围内
                //跳过统计总分转换成对应的分数但不计入总分
                if("".equals(key)){
                    //转换相应等级为分数
                    String[] values = value.split("-");
                    totalScore += Float.valueOf(values[0]);
                    insertScores.put(key + "Score", scoreUtil.tagToScore(values[0]));
                    continue;
                }
            }

            String[] values = value.split("-");
            totalScore += Float.valueOf(values[0]);
            insertScores.put(key + "Score", values[0]);
            insertScores.put(key + "ScoreTotal", values[1]);
        }

        insertMap.put("scores", insertScores);
        insertMap.put("totalScore", totalScore);
        scoreAnalysisDAO.insertScoreRecord(insertMap);
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("recordId", insertMap.get("recordId"));
        return resultMap;
    }
}
