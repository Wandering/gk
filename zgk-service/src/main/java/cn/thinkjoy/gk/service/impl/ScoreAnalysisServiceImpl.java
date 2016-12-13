package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.AreaMaps;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.common.ScoreUtil;
import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.dao.ISystemParmasDao;
import cn.thinkjoy.gk.entity.CheckBatchMsg;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import cn.thinkjoy.gk.service.IScoreConverPrecedenceService;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    ISystemParmasDao iSystemParmasDao;
    @Resource
    IScoreConverPrecedenceService iScoreConverPrecedenceService;
    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityInfoService universityInfoService;
    @Autowired
    AreaMaps areaMaps;


    @Autowired
    private ScoreUtil scoreUtil;

    private static final org.slf4j.Logger LOGGER= LoggerFactory.getLogger(ScoreAnalysisServiceImpl.class);

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
        resultMap.put("recordId", map.get("id"));

        getScores(areaId,majorType,map,resultMap);


        return resultMap;
    }

    /**
     * 判断用户是否是第一次进来
     *
     * @param userId
     * @return
     */
    @Override
    public Integer queryUserIsFirst(long userId) {

        //        三种状态
        String gradeInfo = scoreAnalysisDAO.queryUserGradeInfo(userId);
        Integer count = scoreAnalysisDAO.queryScoreCount(userId);
//        0:未保存个人信息，未测评
//        1:保存个人信息，未测评
//        2:保存了个人信息，做了测评
        if(StringUtils.isEmpty(gradeInfo) && count==0){
            return 0;
        }else if(StringUtils.isNotEmpty(gradeInfo) && count==0){
            return 1;
        }else {
            return 2;
        }
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
        if (totalScore>800 || totalScore<0){
            throw new BizException("error","成绩分析成绩不能超过800分");
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
        if (totalScore>800 || totalScore<0){
            throw new BizException("error","成绩分析成绩不能超过800分");
        }
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
            LOGGER.info("用户是第一次测评");
            resultMap.put("difference", "off");
        }
        if(areaId==ZJ_AREA_CODE){
            resultMap.put("scoreRank", scoreUtil.getScoreRankZJ(areaId, totalScore));
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
                        LOGGER.info("该省没有总人数!");
                        allStuNum = temp;
                    }

                    resultMap.put("stuNum", stuNum);
                    String[] nums = String.valueOf(100 - ((Float.valueOf(proviceRank) / Float.valueOf(allStuNum)) * 100)).split("\\.");
                    String proviceRankPro = Math.abs(Float.valueOf(nums[0] + "." + nums[1].substring(0, nums[1].length()>1?2:nums[1].length()))) + "%";
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
                LOGGER.info("当前省份为" + map.get("areaName"));
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
                    Map<String,Object> scoreLineMap = scoreUtil.getTopBatchLine(areaId, majorType, totalScore);
                    resultMap.put("upLine",scoreLineMap.get("scoreTop"));
                    resultMap.put("upLineName", scoreLineMap.get("nameTop"));
                    resultMap.put("upLineYear", scoreLineMap.get("year"));
                }
                Map<String,Object> scores = getScores2(areaId,majorType,map,resultMap);
                if(areaId==JS_AREA_CODE){
                    String[] xcRanks = scoreUtil.getScoreLevel(scores,majorType);
                    if(scoreUtil.getTagNum(xcRanks[1])<scoreUtil.getTagNum(xcRanks[3])){
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
                    }else if (scoreAnalysisDAO.isExistScore(totalScore, areaTableName)) {
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
            if(targetMap==null){
                throw new BizException("error","当前用户没有经过第一次测评!");
            }
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
                String[] jsSubs = scoreUtil.getScoreLevel(jsScore,majorType);
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
        resultMap.put("schoolId", schoolId);
        resultMap.put("batchName", batchName);
        resultMap.put("schoolName", name);
        resultMap.put("totalScore", totalScore);
        resultMap.put("batch", batch);
        resultMap.put("batchLine", scoreUtil.getBatchScore(batch, areaId, majorType));
        resultMap.put("schoolLine", schoolLine);
        resultMap.put("year", schoolLineYear);
        Float addScore=totalScore - schoolLine;
        resultMap.put("addScore", addScore);
        Integer stuNum =null;
        if (addScore>=0) {
            stuNum = scoreAnalysisDAO.queryStuNumToLine(schoolLine, totalScore, areaTableName);
            resultMap.put("stuNum", stuNum==null?null:-stuNum);
        }else {
            stuNum = scoreAnalysisDAO.queryStuNumToLine(totalScore, schoolLine, areaTableName);
            resultMap.put("stuNum", stuNum);
        }
        resultMap.put("existStuNum", stuNum==null?1:2);
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
            if(targetMap==null){
                throw new BizException("error","当前用户没有经过第一次测评!");
            }
            schoolId = Long.valueOf(targetMap.get("universityId").toString());
            majorCode = targetMap.get("majorCode").toString();
        }
        //浙江一般是浙江省
        Float totalScore = (Float) map.get("totalScore");
//        没有一分一段
        String year = (Integer.valueOf(scoreUtil.getYear()) - 1) + "";
        String name = scoreAnalysisDAO.querySchoolNameById(schoolId);


        List<Map<String, Object>> majorLineMaps = scoreAnalysisDAO.queryMajorLowestScore(schoolId, areaId, majorCode, year);
        Float majorLine = null;
        String majorName = null;
        if (majorLineMaps != null&&majorLineMaps.size()>0) {
            Map<String, Object> majorLineMap=null;
            if(majorLineMaps.size()>1){
                for(Map<String, Object> rMap : majorLineMaps){
                    if("2".equals(rMap.get("majorType").toString())) {
                        majorLineMap = rMap;
                        break;
                    }
                }
                if(majorLineMap==null){
                    majorLineMap = majorLineMaps.get(0);
                }
            }else {
                majorLineMap = majorLineMaps.get(0);
            }

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
    public List<Map<String,Object>> queryHistoryScore(long userId,Integer rows,Long areaId){
        List<Map<String, Object>> maps = scoreAnalysisDAO.queryHistoryScore(userId, rows, areaId);
        List<Map<String, Object>> rtnMaps = new ArrayList<>();



        if(areaId==JS_AREA_CODE) {
            if(maps!=null && !maps.isEmpty()) {
                for (Map<String,Object> map : maps){
                    Map<String,Object> map1 = new HashedMap();

                    map1.putAll(map);
                    String rank1=null;
                    String rank2=null;
                    String rank=null;
                    Iterator<String> iterator = map1.keySet().iterator();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        if(key.indexOf("Score")>0 && key.indexOf("totalScore")<0){
                            map.remove(key);
                            if(map1.get(key)!=null) {
                                if(StringUtils.isEmpty(rank1))
                                    rank1 = scoreUtil.scoreToTag(Float.valueOf(map1.get(key).toString()));
                                else
                                    rank2 = scoreUtil.scoreToTag(Float.valueOf(map1.get(key).toString()));
                            }else {
                                continue;
                            }
                        }
                    }

                    if(scoreUtil.getTagNum(rank1)<scoreUtil.getTagNum(rank2)){
                        rank = rank1+rank2;
                    }else {
                        rank = rank2+rank1;
                    }
                    map.put("xcRank",rank);
                }
            }
        }else {
            rtnMaps=maps;
        }
        return maps;
    }

    @Override
    public List<Map<String,Object>> queryMajorBySchoolIdAndAreaId(long areaId,long universityId,long userId){
        Map<String,Object> map = new HashedMap();
        map.put("areaId",areaId);
        map.put("universityId",universityId);
        map.put("year",Integer.valueOf(scoreUtil.getYear())-1);
        //一定是三门成绩 否则异常
        String[] subjects =scoreUtil.getZJUserScore(userId);
        //计算专业提取范围
        map.put("subjectItem",subjects);
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




    private boolean compareToLevel(String v1,String v2,String v3){
        //如果选测等级是一门。。另一门的形式  直接比较

        if(v1.indexOf("另一门")>0){
            //数字较小比较大
            return scoreUtil.getTagNum(v1)>=scoreUtil.getTagNum(v2);
        }else {
            return scoreUtil.getTagNum(v1)>=scoreUtil.getTagNum(v3);
        }
    }



}
