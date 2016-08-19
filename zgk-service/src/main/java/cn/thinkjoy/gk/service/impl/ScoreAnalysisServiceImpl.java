package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ScoreUtil;
import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.dao.IZGK3in7DAO;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yangyongping on 16/7/27.
 */
@Service
public class ScoreAnalysisServiceImpl implements IScoreAnalysisService {

    private static int JS_AREA_CODE=320000;
    private static int ZJ_AREA_CODE=330000;
    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;

    @Autowired
    private IZGK3in7DAO zgk3in7DAO;

    @Autowired
    private ScoreUtil scoreUtil;

    Logger logger = Logger.getLogger(ScoreAnalysisServiceImpl.class);

    @Override
    public Map<String, Object> queryScoreRecordByUserId(long userId) {
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        if (map == null) {
            return new HashedMap();
        }
        long areaId=Long.valueOf(map.get("areaId").toString());
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("areaName", map.get("areaName"));
        Integer majorType = (Integer) map.get("majorType");
        resultMap.put("majorType", majorType);
        resultMap.put("schoolName", map.get("schoolName"));

        getScores(areaId,majorType,map,resultMap);


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
            //判断

            if (areaId == JS_AREA_CODE) {
                //江苏判定
                String key = (String) iterator.next();
                String value = (String) scores.get(key);
                if("yw".equals(key)||"sx".equals(key)||"wy".equals(key)) {
                    String[] values = value.split("-");
                    totalScore += Float.valueOf(values[0]);
                    insertScores.put(key + "Score", values[0]);
                    insertScores.put(key + "ScoreTotal", values[1]);
                }else {
                    insertScores.put(key + "Score",scoreUtil.tagToScore(value));
                }
            } else {
                String key = (String) iterator.next();
                String value = (String) scores.get(key);
                String[] values = value.split("-");
                totalScore += Float.valueOf(values[0]);
                insertScores.put(key + "Score", values[0]);
                insertScores.put(key + "ScoreTotal", values[1]);
            }
        }
//        if (insertScores.size() != 12 && insertScores.size() != 14) {
//            throw new BizException("error", "提交科目不完整!");
//        }

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

        Map<String,Object> scores = getScores2(areaId,majorType,map,resultMap);


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

        List<String> labels = scoreUtil.getUserLabel(scores,areaId);
        resultMap.put("labels", labels);
        /**
         * ================
         * 浙江没有一分一段,所以浙江跳过
         * ================
         */
        if(areaId!=ZJ_AREA_CODE){

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
                if(areaId!=ZJ_AREA_CODE) {
                    resultMap.put("upLine", scoreUtil.getTopBatchLine(areaId, majorType, totalScore));
                }
                Map<String,Object> scores = getScores2(areaId,majorType,map,resultMap);
                if(areaId==JS_AREA_CODE){
                    String[] xcRanks = getScoreLevel(scores,majorType);
                    if(xcRanks[1].compareTo(xcRanks[3])>0){
                        resultMap.put("xcRank",xcRanks[1]+xcRanks[3]);
                    }else {
                        resultMap.put("xcRank",xcRanks[3]+xcRanks[1]);
                    }

                }
                String areaTableName=null;
                if(areaId!=ZJ_AREA_CODE) {
                    areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
                }
                //分析科目强弱
                Object[] subjects = scoreUtil.getScoreWeak(scores,areaId);
                resultMap.put("strong", subjects[0]);
                resultMap.put("weak", subjects[1]);

                /**
                 * ================
                 * 浙江没有一分一段,所以浙江跳过
                 * ================
                 */
                if(areaId!=ZJ_AREA_CODE) {

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
    public List<Map<String, Object>> queryUniversityByScore(long areaId, int batch, int majorType, String year, Float difference, Float line, Float totalScore, int bc,long userId) {
        return scoreAnalysisDAO.queryUniversityByScore(areaId, batch, majorType, year, difference, line, totalScore, bc,userId);
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
    public List<Map<String, Object>> queryLowstUniversity(long areaId, int majorType, Float totalScore, String year,long userId) {
        return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, year,userId);
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

    /**
     * 通用算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Override
    public Object recommendSchool(float totalScore, long areaId, int majorType,long userId) {
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
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
        }
        int batch = (int) line1s[2];
        //获得分差1  考生分-16年分数线
        float difference = totalScore - (Float) line1s[0];
        //确定点钱分数对应次年批次分数

        Float line2 = scoreUtil.getLastBatchAndScore(areaId, majorType, batch, lastYear.toString());

        //获得分差2  院校15年分-15年分数线 (15年分数线)

        //计算公式为 lowestScore - line -  difference > = bc  || lowestScore - line -  difference > = -bc


        int count = 0;
        int bc = 10;
        do {
            count = scoreAnalysisDAO.countUniversity(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, bc);
            //增加步长
            bc += 10;
        } while (count < 20 && bc < 300);
        bc -= 10;
        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryUniversityByScore(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, totalScore, bc,userId);

        return resultList;
    }

    /**
     * 浙江算法
     * @param totalScore
     * @param areaId
     * @return
     */
    @Override
    public Object recommendSchoolZJ(float totalScore, long areaId,long userId) {


        //一定是三门成绩 否则异常
        String[] subjects =getZJUserScore(userId);

        Integer lastYear = Integer.valueOf(scoreUtil.getYear()) - 1;
        //计算公式为 学生成绩 - 平均分 > = bc  || 平均分 - 学生成绩 < = bc
        //计算专业提取范围
        Map<String,Object> map = new HashedMap();
        map.put("subjectItemList", combineAlgorithm(subjects));
        map.put("areaId",areaId);
        map.put("year",lastYear.toString());
        map.put("totalScore",totalScore);

        int count = 0;
        int bc = 10;
        do {
            map.put("bc",bc);
            count = scoreAnalysisDAO.countZJUniversity(map);
            //增加步长
            bc += 10;
        } while (count < 20 && bc < 300);
        bc -= 10;
        map.put("bc",bc);
        map.put("userId",userId);

        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryZJUniversityByScore(map);

        return resultList;
    }


    /**
     * 江苏算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Override
    public Object recommendSchoolJS(float totalScore, long areaId, int majorType,long userId) {
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
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
        }
        int batch = (int) line1s[2];
        //获得分差1  考生分-16年分数线
        float difference = totalScore - (Float) line1s[0];
        //确定点钱分数对应次年批次分数

        Float line2 = scoreUtil.getLastBatchAndScore(areaId, majorType, batch, lastYear.toString());

        //获得分差2  院校15年分-15年分数线 (15年分数线)

        //计算公式为 lowestScore - line -  difference > = bc  || lowestScore - line -  difference > = -bc


        /**
         * =================================
         * 这里需要计算江苏省的选考等级
         * 两种情况
         * 1、单科固定 另一门随机
         * 2、任意一门顺序不限 如:AB  物理A 政治B 或者 政治A 物理B
         * =================================
         */

        //根据用户ID获取用户上一次测评成绩和测评科目
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        Map<String,Object> scores = scoreUtil.getScoresJS(map, majorType);

        List<String> xcRanks=null;
        if(scores.size()==5){
            xcRanks=getLevelList(scores,majorType);
        }else{
            throw new BizException("error","科目不正确!");
        }

        /**
         * 这里需要去比对院校招生表
         */
        int count = 0;
        int bc = 0;
        do {
            count = scoreAnalysisDAO.countJSUniversity(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, bc,xcRanks);
            //增加步长
            bc += 10;
        } while (count < 20 && bc < 300);
        bc -= 10;
        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryJSUniversityByScore(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, totalScore, bc,xcRanks,userId);
        return resultList;
    }




    @Override
    public Object queryGapBySchoolIdAndBatch(long recordId,
                                             Long schoolId,
                                             Integer batch,
                                             long userId) {
        Map<String, Object> map = scoreAnalysisDAO.queryInfoByRecordId(recordId);
        if(map==null){
            throw new BizException("error","请先去进行成绩测评!");
        }
        long areaId = Long.valueOf(map.get("areaId").toString());
        //假如院校没有传入 默认为使用上次院校
        if (schoolId != null && batch != null) {
            Map<String, Object> insertMap = new HashedMap();
            insertMap.put("userId", userId);
            insertMap.put("areaId",areaId);
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
        int majorType = (int) map.get("majorType");


        /**
         * 分数等级,江苏专用
         */
        String level1=null;
        String level2=null;
        String universityLevel =null;
        if(areaId==JS_AREA_CODE){
            Map<String,Object> jsScore = scoreUtil.getScoresJS2(map,majorType);
            /**
             * ================
             * 江苏成绩必须是5们 否则报错
             * ================
             */
            if(jsScore.size()==5){
                //删除语数外剩余可选两门课目
                String[] jsSubs = getScoreLevel(jsScore,majorType);
                if(scoreUtil.tagToScore(jsSubs[1])>scoreUtil.tagToScore(jsSubs[3])){
                    level1=jsSubs[1]+jsSubs[3];
                }else {
                    level1=jsSubs[3]+jsSubs[1];
                }
                level2=jsSubs[0]+jsSubs[1]+",另一门"+jsSubs[3];
            }else {
                throw new BizException("error","江苏成绩必须是5门!");
            }


        }

        Float totalScore = (Float) map.get("totalScore");
        String areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
        String year = (Integer.valueOf(scoreUtil.getYear()) - 1) + "";
        String name = scoreAnalysisDAO.querySchoolNameById(schoolId);
        String batchName = scoreAnalysisDAO.queryBatchNameById(batch);
        Map<String, Object> schoolLineMap = scoreAnalysisDAO.queryUnivsersityLowestScore(schoolId, areaId, batch, majorType, year);
        Float schoolLine = null;
        String schoolLineYear = null;

        //查询当前学校的选测科目等级
        if(areaId==JS_AREA_CODE) {
            Map<String, Object> map1 = new HashedMap();
            map1.put("year", year);
            map1.put("areaId", areaId);
            map1.put("universityId", schoolId);
            universityLevel = scoreAnalysisDAO.queryUniversitySubLevel(map1);

        }


        if (schoolLineMap != null&&schoolLineMap.size()>0) {
            schoolLine = Float.valueOf(schoolLineMap.get("lowestScore").toString());
            schoolLineYear = schoolLineMap.get("year").toString();
        } else {
            throw new BizException("error", "当前学校在当前批次无数据");
        }
        Map<String, Object> resultMap = new HashedMap();
        if(areaId==JS_AREA_CODE){
            boolean flag = compareToLevel(universityLevel,level2,level1);
            resultMap.put("levelTag", flag);//当flag==true 说明没达标
            if(flag){
                resultMap.put("schoollevel", universityLevel);
            }
        }
        if (totalScore > schoolLine) {
            Integer stuNum = scoreAnalysisDAO.queryStuNumToLine(schoolLine, totalScore, areaTableName);

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
        }else {
            Integer stuNum = scoreAnalysisDAO.queryStuNumToLine(totalScore, schoolLine, areaTableName);
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
        }


        return resultMap;

    }

    /**
     * 浙江专用
     * @param recordId
     * @param schoolId
     * @param majorCode
     * @param userId
     * @return
     */
    @Override
    public Object queryGapBySchoolIdAndMajor(long recordId,
                                             Long schoolId,
                                             String majorCode,
                                             long userId) {

        Map<String, Object> map = scoreAnalysisDAO.queryInfoByRecordId(recordId);
        long areaId = Long.valueOf(map.get("areaId").toString());
        if(areaId!=ZJ_AREA_CODE){
            throw new BizException("error","当前用户不属于浙江用户!");
        }
        //假如院校没有传入 默认为使用上次院校
        if (schoolId != null && majorCode != null) {
            Map<String, Object> insertMap = new HashedMap();
            insertMap.put("userId", userId);
            insertMap.put("areaId",areaId);
            insertMap.put("universityId", schoolId);
            insertMap.put("majorCode", majorCode);
            insertMap.put("cdate", System.currentTimeMillis());
            scoreAnalysisDAO.insertTarget(insertMap);
        } else {
            //获取上次测评院校和批次
            Map<String, Object> targetMap = scoreAnalysisDAO.queryLastTarget(userId);
            schoolId = Long.valueOf(targetMap.get("universityId").toString());
            majorCode = targetMap.get("majorCode").toString();
        }
        //浙江一般是浙江省
        Float totalScore = (Float) map.get("totalScore");
//        没有一分一段
        String year = (Integer.valueOf(scoreUtil.getYear()) - 1) + "";
        String name = scoreAnalysisDAO.querySchoolNameById(schoolId);


        Map<String, Object> majorLineMap = scoreAnalysisDAO.queryMajorLowestScore(schoolId, areaId, majorCode, year);
        Float majorLine = null;
        String majorName = null;
        if (majorLineMap != null&&majorLineMap.size()>0) {
            majorLine = Float.valueOf(majorLineMap.get("averageScore").toString());
            majorName = majorLineMap.get("majorName").toString();
        } else {
            throw new BizException("error", "当前学校在"+year+"年无数据");
        }


        Map<String, Object> schoolLineMap = scoreAnalysisDAO.queryUnivsersityLowestScore(schoolId, areaId,null,null, year);
        Float schoolLine = null;
        String schoolLineYear = null;
        if (schoolLineMap != null&&schoolLineMap.size()>0) {
            schoolLine = Float.valueOf(schoolLineMap.get("lowestScore").toString());
            schoolLineYear = schoolLineMap.get("year").toString();
        } else {
            throw new BizException("error", "当前学校在"+year+"年无数据");
        }
        Map<String, Object> resultMap = new HashedMap();

        resultMap.put("schoolId", schoolId);
        resultMap.put("schoolName", name);
        resultMap.put("totalScore", totalScore);
        resultMap.put("majorLine", majorLine);
        resultMap.put("majorName", majorName);
        resultMap.put("addScore", totalScore - schoolLine);
        resultMap.put("schoolLine", schoolLine);
        resultMap.put("year", schoolLineYear);

        return resultMap;
    }

    @Override
    public List<String> queryHistoryScore(long userId,Integer rows){
        return scoreAnalysisDAO.queryHistoryScore(userId,rows);
    }

    @Override
    public List<Map<String,Object>> queryMajorBySchoolIdAndAreaId(long areaId,long universityId,long userId){
        Map<String,Object> map = new HashedMap();
        map.put("areaId",areaId);
        map.put("universityId",universityId);
        map.put("year",Integer.valueOf(scoreUtil.getYear())-1);
        //一定是三门成绩 否则异常
        String[] subjects =getZJUserScore(userId);
        //计算专业提取范围
        map.put("subjectItemList", combineAlgorithm(subjects));
        return scoreAnalysisDAO.queryMajorBySchoolIdAndAreaId(map);
    }


    private Map<String,Object> getScores(long areaId,Integer majorType,Map<String,Object> map,Map<String,Object> resultMap){
        Map<String, Object> scores =null;
        if(areaId==JS_AREA_CODE){
            //  江苏
            scores = scoreUtil.getScoresJS(map, majorType);
            resultMap.put("scores", scores);
        }else {
            //  其他 包括浙江
            scores = scoreUtil.getScores(map, majorType);
            resultMap.put("scores", scores);
        }
        return scores;
    }

    private Map<String,Object> getScores2(long areaId,Integer majorType,Map<String,Object> map,Map<String,Object> resultMap){
        Map<String, Object> scores =null;
        if(areaId==JS_AREA_CODE){
            //  江苏
            scores = scoreUtil.getScoresJS2(map, majorType);
            resultMap.put("scores", scores);
        }else {
            //  其他 包括浙江
            scores = scoreUtil.getScores(map, majorType);
            resultMap.put("scores", scores);
        }
        return scores;
    }

    private List<Map<String,Object>> combineAlgorithm(String[] str){

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));


        List<Map<String,Object>> mapList=null;
        Map<String,Object> map=null;
        Map<String,Object> subjectItemMap=null;
        List<Map<String,Object>> lists=new ArrayList<>();
        for (int i = 1; i <= nBit; i++) {
            mapList=new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    map=new HashMap<>();
                    map.put("selectSubject",str[j]);
                    mapList.add(map);
                }
            }
            subjectItemMap=new HashMap<>();
            subjectItemMap.put("subjectItem",mapList);
            lists.add(subjectItemMap);
        }
        return lists;
    }
    private List<String> getLevelList(Map<String,Object> map,Integer majorType){

        String [] subs = getScoreLevel(map,majorType);


        return getScoreLevel(subs[1],subs[3],subs[0]);
    }

    private String[] getScoreLevel(Map<String,Object> scores,Integer majorType){

        Iterator<String> keys = scores.keySet().iterator();
        //江苏一定是两门额外科目  否则抛异常
        Map<String,Object> map1=new LinkedHashMap<>();

        while (keys.hasNext()) {
            String key = keys.next();
            if ((!"语文".equals(key)) && (!"数学".equals(key)) && (!"外语".equals(key))) {
                String value = scores.get(key).toString();
                map1.put(key,value);
            }
        }

        String sub = "历史";
        if(majorType == 2){
            sub = "物理";
        }
        Map<String,Object> map2=new HashedMap();
        map2.putAll(map1);
        String value1= scoreUtil.scoreToTag(Float.valueOf(map2.get(sub).toString().split("-")[0]));
        map2.remove(sub);
        String key=map2.keySet().iterator().next();
        String value2 = scoreUtil.scoreToTag(Float.valueOf(map2.get(key).toString().split("-")[0]));
        return new String[]{sub,value1,key,value2};
    }

    private List<String> getScoreLevel(String v1,String v2,String sub){
        Set<String> levels = new HashSet<>();
        List<String> v1s=getScoreLevels(v1);
        List<String> v2s=getScoreLevels(v2);
        //遍历所有组合
        for(String v3:v1s){
            for(String v4:v1s) {
                if(scoreUtil.tagToScore(v3)-scoreUtil.tagToScore(v4)>0F) {
                    levels.add(v3 + v4);
                }else {
                    levels.add(v4 + v3);
                }
                levels.add(sub + v3 + ",另一门" + v4);
            }
        }
        List<String> list = new ArrayList<>();
        list.addAll(levels);
        return list;
    }

    private List<String> getScoreLevels(String v1){
        List<String> list= new ArrayList<>();
        //遍历所有组合
        switch (v1){
            case "A+":
                list.add("A+");
            case "A":
                list.add("A");
            case "B+":
                list.add("B+");
            case "B":
                list.add("B");
            case "C":
                list.add("C");
            case "D":
                list.add("D");
        }
        return list;
    }

    private String[] getZJUserScore(long userId){
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        Map<String,Object> scores = scoreUtil.getScores(map, null);
        Iterator<String> keys = scores.keySet().iterator();
        //一定是三门成绩 否则异常
        String[] subjects =null;
        try {
            //提取科目
            subjects = new String[3];
            int i = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                if (!("语文".equals(key)) && (!"数学".equals(key)) && (!"外语".equals(key))) {
                    subjects[i++] = key;
                }
            }
        }catch (IndexOutOfBoundsException e){
            //数组越界的错误
            throw new BizException("error","当前科目不正确!");
        }
        return subjects;

    }

    private boolean compareToLevel(String v1,String v2,String v3){
        //如果选测等级是一门。。另一门的形式  直接比较
        if(v1.indexOf("另一门")>0){
            return v1.compareTo(v2)>0;
        }else {
            return v1.compareTo(v3)>0;
        }
    }

}
