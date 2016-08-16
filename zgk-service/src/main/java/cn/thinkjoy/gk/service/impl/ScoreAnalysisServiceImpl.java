package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ScoreUtil;
import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/27.
 */
@Service
public class ScoreAnalysisServiceImpl implements IScoreAnalysisService {


    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;
    @Autowired
    private ScoreUtil scoreUtil;

    Logger logger = Logger.getLogger(ScoreAnalysisServiceImpl.class);

    @Override
    public Map<String, Object> queryScoreRecordByUserId(long userId) {
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        if (map == null) {
            return new HashedMap();
        }

        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("areaName", map.get("areaName"));
        Integer majorType = (Integer) map.get("majorType");
        resultMap.put("majorType", majorType);
        resultMap.put("schoolName", map.get("schoolName"));
        Map<String, Object> scores = scoreUtil.getScores(map, majorType);
        resultMap.put("scores", scores);
        return resultMap;
    }

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
            String[] values = value.split("-");
            totalScore += Float.valueOf(values[0]);
            insertScores.put(key + "Score", values[0]);
            insertScores.put(key + "ScoreTotal", values[1]);
        }
        if (insertScores.size() != 12 && insertScores.size() != 14) {
            throw new BizException("error", "提交科目不完整!");
        }

        insertMap.put("scores", insertScores);
        insertMap.put("totalScore", totalScore);
        scoreAnalysisDAO.insertScoreRecord(insertMap);
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("recordId", insertMap.get("recordId"));
        return resultMap;
    }

    @Override
    public Map<String, Object> queryInfoByRecordId(long recordId) {
        Map<String, Object> map = scoreAnalysisDAO.queryInfoByRecordId(recordId);
        if (map == null) {
            return new HashedMap();
        }
        Map<String, Object> resultMap = new HashedMap();

        Float totalScore = (Float) map.get("totalScore");
        Long areaId = Long.valueOf(map.get("areaId").toString());

        resultMap.put("totalScore", totalScore);
        resultMap.put("areaName", map.get("areaName"));
        Integer majorType = (Integer) map.get("majorType");
        resultMap.put("majorType", majorType);
        ;
        Map<String, Object> scores = scoreUtil.getScores(map, majorType);
        resultMap.put("scores", scores);


        // 获取用户上次成绩
        //第一次判定
        Float lastScore = null;
        lastScore = scoreAnalysisDAO.queryLastScore(Long.valueOf(map.get("userId").toString()), recordId);
        if (lastScore != null) {
            //非第一次用户  有上一次成绩
            resultMap.put("difference", scoreUtil.floatToStr(totalScore - lastScore));
        } else {
            //第一次用户没有上一次成绩
            logger.info("用户是第一次测评");
            resultMap.put("difference", "off");
        }

        // 推荐标签

        List<String> labels = scoreUtil.getUserLabel(scoreUtil.getScores(map, majorType));
        resultMap.put("labels", labels);

        String areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
        //文或者理科总人数
        Integer allStuNum = scoreAnalysisDAO.queryAllAreaStuNum(areaTableName);
        try {
            //极端情况
            if (scoreAnalysisDAO.isExistMaxScore(totalScore, areaTableName)) {
                //当前分数超过了一分一段表的最大值 或者  达到很高的值
                resultMap.put("proviceRank", -1);
                resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));

            } else if (scoreAnalysisDAO.isExistScore(totalScore, areaTableName)) {
                //            正常情况
                //需要超过多少人
                //一分超过多少人
                Integer stuNum = scoreAnalysisDAO.queryStuNum(totalScore, areaTableName);
                //全省排名
                Integer proviceRank = scoreAnalysisDAO.queryProviceRank(totalScore, areaTableName);
                Integer temp = allStuNum;
                //重新定义全省人数
                allStuNum = scoreAnalysisDAO.queryPeoNumByAreaAndType(areaId, majorType);
                if (allStuNum == null || allStuNum == 0) {
                    logger.info("该省没有总人数!");
                    allStuNum = temp;
                }

                resultMap.put("stuNum", stuNum);
                String[] nums = String.valueOf(100 - ((Float.valueOf(proviceRank) / Float.valueOf(allStuNum)) * 100)).split("\\.");
                String proviceRankPro = nums[0] + "." + nums[1].substring(0, 2) + "%";
                resultMap.put("proviceRankPro", proviceRankPro);
                resultMap.put("proviceRank", proviceRank);

                resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));
            } else {
                //           分数不在一分一段中的情况
                //文或者理科总人数
                resultMap.put("proviceRank", -allStuNum);
                resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));
            }
        } catch (Exception e) {
            logger.info("当前省份为" + map.get("areaName"));
            throw new BizException("error", "暂不支持" + map.get("areaName") + "!");
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> queryAllRecordByUserId(long userId) {
        List<Map<String, Object>> list = new ArrayList<>();

        List<Map<String, Object>> queryList = scoreAnalysisDAO.queryAllRecordByUserId(userId);
        if (queryList != null && (!queryList.isEmpty())) {
            for (Map<String, Object> map : queryList) {
                Map<String, Object> resultMap = new HashedMap();
                Float totalScore = (Float) map.get("totalScore");
                Long areaId = Long.valueOf(map.get("areaId").toString());
                resultMap.put("recordId", map.get("recordId"));
                resultMap.put("totalScore", totalScore);
                resultMap.put("areaName", map.get("areaName"));
                Integer majorType = (Integer) map.get("majorType");
                resultMap.put("majorType", majorType);
                resultMap.put("cdate", map.get("cdate"));
                resultMap.put("upLine", scoreUtil.getTopBatchLine(areaId, majorType, totalScore));
                Map<String, Object> scores = scoreUtil.getScores(map, majorType);

                resultMap.put("scores", scores);
                String areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
                //分析科目强弱
                Object[] subjects = scoreUtil.getScoreWeak(scores);
                resultMap.put("strong", subjects[0]);
                resultMap.put("weak", subjects[1]);


                //极端情况
                if (scoreAnalysisDAO.isExistMaxScore(totalScore, areaTableName)) {
                    //当前分数超过了一分一段表的最大值 或者  达到很高的值
                    resultMap.put("proviceRank", -1);
                    resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));
                }
                if (scoreAnalysisDAO.isExistScore(totalScore, areaTableName)) {
                    //需要超过多少人
                    Integer stuNum = scoreAnalysisDAO.queryStuNum(totalScore, areaTableName);
                    Float totalScore1 = totalScore;
                    while (stuNum == null) {
                        stuNum = scoreAnalysisDAO.queryStuNum(totalScore1--, areaTableName);
                    }
                    Integer proviceRank = scoreAnalysisDAO.queryProviceRank(totalScore1, areaTableName);
                    resultMap.put("stuNum", stuNum);
                    resultMap.put("proviceRank", proviceRank);
                } else {
                    //TODO           分数不在一分一段中的情况
                    //文或者理科总人数
                    int allStuNum = scoreAnalysisDAO.queryAllAreaStuNum(areaTableName);
                    resultMap.put("proviceRank", -allStuNum);
                }


                list.add(resultMap);
            }
        }
        return list;
    }

    @Override
    public Integer queryStuNum(Object totalScore, String areaTableName) {
        return scoreAnalysisDAO.queryStuNum(totalScore, areaTableName);
    }

    @Override
    public Integer queryAllAreaStuNum(String areaTableName) {
        return scoreAnalysisDAO.queryAllAreaStuNum(areaTableName);
    }

    @Override
    public Integer queryStuNumToLine(Object totalScore, Object scoreLine, String areaTableName) {
        return scoreAnalysisDAO.queryStuNumToLine(totalScore, scoreLine, areaTableName);
    }

    @Override
    public String querySchoolNameById(Object id) {
        return scoreAnalysisDAO.querySchoolNameById(id);
    }

    @Override
    public String queryBatchNameById(Object id) {
        return scoreAnalysisDAO.queryBatchNameById(id);
    }

    @Override
    public Integer queryProviceRank(Object totalScore, String areaTableName) {
        return scoreAnalysisDAO.queryProviceRank(totalScore, areaTableName);
    }

    @Override
    public Integer queryProviceRank2(Object totalScore, String areaTableName) {
        return scoreAnalysisDAO.queryProviceRank2(totalScore, areaTableName);
    }

    @Override
    public String queryScoreLine(long areaId, int majorType, String year) {
        return scoreAnalysisDAO.queryScoreLine(areaId, majorType, year);
    }

    @Override
    public String queryAreaKey(long areaId) {
        return scoreAnalysisDAO.queryAreaKey(areaId);
    }

    @Override
    public List<Map<String, Object>> queryUnivsersityBatch(long areaId, long schooleId, String year, Integer majorType) {
        return scoreAnalysisDAO.queryUnivsersityBatch(areaId, schooleId, year, majorType);
    }

    @Override
    public Map<String, Object> queryLastTarget(long userId) {
        return scoreAnalysisDAO.queryLastTarget(userId);
    }

    @Override
    public int insertTarget(Map<String, Object> map) {
        return scoreAnalysisDAO.insertTarget(map);
    }

    @Override
    public Map<String, Object> queryUnivsersityLowestScore(long schoolId, long areaId, int batch, int majorType, String year) {
        return scoreAnalysisDAO.queryUnivsersityLowestScore(schoolId, areaId, batch, majorType, year);
    }

    @Override
    public int countUniversity(long areaId, int batch, int majorType, String year, Float difference, Float line, int bc) {
        return scoreAnalysisDAO.countUniversity(areaId, batch, majorType, year, difference, line, bc);
    }

    @Override
    public List<Map<String, Object>> queryUniversityByScore(long areaId, int batch, int majorType, String year, Float difference, Float line, Float totalScore, int bc) {
        return scoreAnalysisDAO.queryUniversityByScore(areaId, batch, majorType, year, difference, line, totalScore, bc);
    }

    @Override
    public List<Map<String, Object>> queryHighSchoolByCountyId(long countyId, String schoolName) {
        return scoreAnalysisDAO.queryHighSchoolByCountyId(countyId, schoolName);
    }

    @Override
    public int setUserInfo(Map<String, Object> map) {
        return scoreAnalysisDAO.setUserInfo(map);
    }

    @Override
    public Map<String, Object> queryUserInfo(long userId) {
        return scoreAnalysisDAO.queryUserInfo(userId);
    }

    @Override
    public List<Map<String, Object>> queryUniversityScore(long universityId, long areaId, Integer majorType, Integer batch) {
        return scoreAnalysisDAO.queryUniversityScore(universityId, areaId, majorType, batch);
    }

    @Override
    public boolean isExistScore(Object totalScore, String areaTableName) {
        return scoreAnalysisDAO.isExistScore(totalScore, areaTableName);
    }

    @Override
    public boolean isExistMaxScore(Object totalScore, String areaTableName) {
        return scoreAnalysisDAO.isExistMaxScore(totalScore, areaTableName);
    }

    @Override
    public List<Map<String, Object>> queryLowstUniversity(long areaId, int majorType, Float totalScore, String year) {
        return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, year);
    }

    @Override
    public Integer queryUserGrade(long userId) {
        return scoreAnalysisDAO.queryUserGrade(userId);
    }

    @Override
    public Integer queryTotalScoreByAreaId(long areaId) {
        return scoreAnalysisDAO.queryTotalScoreByAreaId(areaId);
    }

    /**
     * 获取用户上一次成绩
     *
     * @param userId
     * @param lastId
     * @return
     */
    @Override
    public Float queryLastScore(long userId, long lastId) {
        return scoreAnalysisDAO.queryLastScore(userId, lastId);
    }

    /**
     * 获取当前省份当年参加高考人数 当不存在的时候为0或null
     *
     * @param areaId
     * @param majorType
     * @return
     */
    @Override
    public Integer queryPeoNumByAreaAndType(long areaId, int majorType) {
        return scoreAnalysisDAO.queryPeoNumByAreaAndType(areaId, majorType);
    }

    /**
     * 获取当前用户标签
     *
     * @param type
     * @param configs
     * @return
     */
    @Override
    public List<String> queryLabelByTypeAndConfig(Integer type, List<Integer> configs) {
        return scoreAnalysisDAO.queryLabelByTypeAndConfig(type, configs);
    }

    public Object recommendSchool(float totalScore, long areaId, int majorType) {
        Integer lastYear = Integer.valueOf(scoreUtil.getYear()) - 1;

        //确定当前分数对应当年批次分数
//        long areaId,int majorType,Float totalScore,String year
        Object[] line1s = null;
        try {
            line1s = scoreUtil.getBatchAndScore(areaId, majorType, totalScore, scoreUtil.getYear());
        } catch (Exception e) {
            throw new BizException("error", "当前省份" + scoreUtil.getYear() + "年分数线为空!");
        }
        if (line1s[2] == 5) {
            //todo 假如不足高职专科批次(分数超低)

            //推荐10所高职院校
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString());
        }
        int batch = (int) line1s[2];
        //获得分差1  考生分-16年分数线
        float difference = totalScore - (Float) line1s[0];
        //确定点钱分数对应次年批次分数


        Float line2 = scoreUtil.getLastBatchAndScore(areaId, majorType, batch, lastYear.toString());

        //获得分差2  院校15年分-15年分数线 (15年分数线)


        //计算公式为 lowestScore - line -  difference > = bc  || lowestScore - line -  difference > = -bc

        int count = 0;
        int bc = 0;
        do {
            count = scoreAnalysisDAO.countUniversity(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, bc);
            //增加步长
            bc += 5;
        } while (count < 20 && bc < 750);

        bc -= 5;
        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryUniversityByScore(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, totalScore, bc);


        return resultList;
    }

    public Object queryGapBySchoolIdAndBatch(long recordId,
                                             Long schoolId,
                                             Integer batch,
                                             long userId) {
        Map<String, Object> map = scoreAnalysisDAO.queryInfoByRecordId(recordId);
        //假如院校没有传入 默认为使用上次院校
        if (schoolId != null && batch != null) {
            Map<String, Object> insertMap = new HashedMap();
            insertMap.put("userId", userId);
            insertMap.put("areaId", map.get("areaId"));
            insertMap.put("universityId", schoolId);
            insertMap.put("batch", batch);
            insertMap.put("cdate", System.currentTimeMillis());
            scoreAnalysisDAO.insertTarget(insertMap);
        } else {
            //获取上次测评院校和批次
            Map<String, Object> targetMap = scoreAnalysisDAO.queryLastTarget(userId);
            schoolId = Long.valueOf(targetMap.get("universityId").toString());
            batch = Integer.valueOf(targetMap.get("batch").toString());
        }

        long areaId = Long.valueOf(map.get("areaId").toString());
        int majorType = (int) map.get("majorType");
        Float totalScore = (Float) map.get("totalScore");
        String areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
        String year = (Integer.valueOf(scoreUtil.getYear()) - 1) + "";
        String name = scoreAnalysisDAO.querySchoolNameById(schoolId);
        String batchName = scoreAnalysisDAO.queryBatchNameById(batch);
        Map<String, Object> schoolLineMap = scoreAnalysisDAO.queryUnivsersityLowestScore(schoolId, areaId, batch, majorType, year);
        Float schoolLine = null;
        String schoolLineYear = null;
        if (schoolLineMap != null) {
            schoolLine = Float.valueOf(schoolLineMap.get("lowestScore").toString());
            schoolLineYear = schoolLineMap.get("year").toString();
        } else {
            throw new BizException("error", "当前学校在当前批次无数据");
        }
        if (totalScore > schoolLine) {
            Integer stuNum = scoreAnalysisDAO.queryStuNumToLine(schoolLine, totalScore, areaTableName);
            Map<String, Object> resultMap = new HashedMap();
            resultMap.put("schoolId", schoolId);
            resultMap.put("batchName", batchName);
            resultMap.put("schoolName", name);
            resultMap.put("totalScore", totalScore);
            resultMap.put("stuNum", -stuNum);
            resultMap.put("batchLine", scoreUtil.getBatchScore(batch, areaId, majorType));
            resultMap.put("schoolLine", schoolLine);
            resultMap.put("batch", batch);
            resultMap.put("year", schoolLineYear);
            return resultMap;
        }
        Integer stuNum = scoreAnalysisDAO.queryStuNumToLine(totalScore, schoolLine, areaTableName);

        Map<String, Object> resultMap = new HashedMap();

        resultMap.put("schoolId", schoolId);
        resultMap.put("schoolName", name);
        resultMap.put("batchName", batchName);
        resultMap.put("totalScore", totalScore);
        resultMap.put("stuNum", stuNum);
        resultMap.put("addScore", totalScore - schoolLine);
        resultMap.put("batchLine", scoreUtil.getBatchScore(batch, areaId, majorType));
        resultMap.put("schoolLine", schoolLine);
        resultMap.put("batch", batch);
        resultMap.put("year", schoolLineYear);

        return resultMap;

    }
}
