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
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IScoreAlgorithmService;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/1.
 */
@Service
public class ScoreAlgorithmServiceImpl implements IScoreAlgorithmService{

//======================================================================================================================
    private static final org.slf4j.Logger LOGGER= LoggerFactory.getLogger(ScoreAlgorithmServiceImpl.class);

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

    private static String MAJORTYPE_WEN = "1";
    private static String MAJORTYPE_LI = "2";


    @Autowired
    private ScoreUtil scoreUtil;
    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;


    /**
     * 通用算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Override
    public Object recommendSchool(float totalScore, long areaId, int majorType,long userId) {

        LOGGER.info("totalScore:"+totalScore);
        LOGGER.info("areaId:"+areaId);
        LOGGER.info("majorType:"+majorType);
        LOGGER.info("userId:"+userId);


        LOGGER.info("=======参数整理 Start=======");
        String province = areaMaps.getAreaCode(areaId);
        Integer sap=getLogic(province,majorType);
        Integer score = Integer.parseInt(scoreUtil.floatToStr(totalScore));
        LOGGER.info("=======参数整理 End=======");

        LOGGER.info("=======批次及批次控制线信息 Start=======");
        LOGGER.info("分数||位次:" + sap);
        LOGGER.info("成绩: " + score);
        LOGGER.info("科类:" + majorType);
        LOGGER.info("省份:" + province);
        String[] batchs = getBatchByScore(province,majorType,score,areaId);
        LOGGER.info("该学生被定为在(只能填报):"+batchs[1]+"批次");
        LOGGER.info("该学生被定为在:"+batchs[0]+"批次");
        LOGGER.info("=======批次及批次控制线信息 End========");


        LOGGER.info("=======获取推荐学校 Start=======");
        List<Map<String,Object>> resultMaps = getResultListByScore(batchs,score,province,majorType,userId,areaId);
        return resultMaps;
    }

    public List<Map<String,Object>> getResultListByScore(String[] batchs,Integer score,String province,Integer majorType,long userId,long areaId){
        Map<String,Object> condition = new HashedMap();
        condition.put("userId",userId);
        //这里批次用统一批次
        condition.put("batch",batchs[0]);
        condition.put("year",getConfigValueInt(province,majorType, ReportUtil.SCORE_ENROLLING_YEAR));
        condition.put("majorType",majorType);
        condition.put("areaId",areaId);



        LOGGER.info("=======放置参数 Start========");
        ReportForecastView reportForecastView = new ReportForecastView();
        //这里批次用智能填报批次
        reportForecastView.setBatch(batchs[1]);
        reportForecastView.setScore(score);
        reportForecastView.setProvince(province);
        reportForecastView.setCategorie(majorType);
        if(isPre(reportForecastView,ReportUtil.SCORE_ENROLLING_LOGIC)) {
            reportForecastView.setPrecedence(universityInfoService.converPreByScore(reportForecastView, ReportUtil.SCORE_ENROLLING_LOGIC));
        }
        reportForecastView.setScoreDiff(universityInfoService.converScoreDiffByScore(reportForecastView));
        reportForecastView.setJoin(false);
        reportForecastView.setOrderBy("enrollRate desc");
        reportForecastView.setLimit(getConfigValueInt(province,majorType,ReportUtil.SCORE_ENROLLING_LIMIT));

        List<Map<String,Object>> universityInfoEnrollings = getEnrollingByScore(reportForecastView);
        LOGGER.info("=======放置参数 End========");

        condition.put("universitys",universityInfoEnrollings);
        String sortBy = getConfigValueString(province,majorType,ReportUtil.SCORE_SORT_BY);
        LOGGER.info("排序方式:"+sortBy);
        List<UniversityEnrollView> universityEnrollViews =universityInfoService.selectUnivEnrollInfo(condition,sortBy);
        LOGGER.info("=======获取推荐学校 End========");
        LOGGER.info("=======组装返回值 Start========");
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Map<String,Object> map:universityInfoEnrollings){
            LOGGER.info("当前组装学校:"+map.get("universityName"));
            for(UniversityEnrollView universityEnrollView:universityEnrollViews){
                if(map.get("universityName").equals(universityEnrollView.getUniversityName())){
                    map.put("batch",universityEnrollView.getBatchName());
                    map.put("highestScore",universityEnrollView.getHighestScore());
                    map.put("lowestScore",universityEnrollView.getLowestScore());
                    map.put("averageScore",universityEnrollView.getAverageScore());
                    map.put("xcRank",universityEnrollView.getXcRank());
                    map.put("schoolName",universityEnrollView.getUniversityName());
                    map.put("stuNum",universityEnrollView.getPlanEnrolling());
                    Integer isFavorite = universityEnrollView.getIsFavorite()==null?0:universityEnrollView.getIsFavorite();
                    map.put("isFavorite",isFavorite);
                    resultList.add(map);
                };

            }
        }
        LOGGER.info("=======组装返回值 End========");


        return resultList;

    }


    /**
     * 江苏算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Deprecated
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
            // 假如不足高职专科批次(分数超低)

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
            xcRanks=scoreUtil.getLevelList(scores,majorType);
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

        if(resultList==null){
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
        }else if(resultList.size()==0) {

            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);

        }
        return resultList;
    }


    /**
     * 成绩分析获取推荐院校列表
     * @param reportForecastView 参数打包
     * @return
     */
    private List<Map<String,Object>> getEnrollingByScore(ReportForecastView reportForecastView) {


        List<UniversityInfoEnrolling> universityInfoEnrollings=null;

        //算法走向 0线差 1位次
        if (isPre(reportForecastView,ReportUtil.SCORE_ENROLLING_LOGIC)) {
            LOGGER.info("这是位次法");
            //  位次法
            universityInfoEnrollings = universityInfoService.getUniversityEnrollingsByPrecedence(reportForecastView);
        }else {
            //线差法
            universityInfoEnrollings=universityInfoService.getUniversityEnrollingsByScoreDiff(reportForecastView);
        }
        Map<String,Object> map=null;
        List<Map<String,Object>> mapList=new ArrayList<>();
        String[] enrollRandomStr =  getEnrollRandom(reportForecastView.getProvince(),ReportUtil.FORECAST_ENROLLING_RANDOM,reportForecastView.getCategorie());
        for(int i=0;i<universityInfoEnrollings.size();i++){
            map = new HashedMap();
            Integer enrollRate = getResultEnroll(
                    getEnrollRate(universityInfoEnrollings.get(i)),
                    enrollRandomStr
            );
            map.put("enrollRate",enrollRate);
            map.put("universityName",universityInfoEnrollings.get(i).getUniversityName());
            map.put("universityId",universityInfoEnrollings.get(i).getUniversityId());
            mapList.add(map);
        }

        LOGGER.info("推荐出院校个数:"+mapList.size());
        return mapList;
    }


    /**
     * 获取录取率
     * @param universityInfoEnrolling
     * @return
     */
    private Integer getEnrollRate(UniversityInfoEnrolling universityInfoEnrolling){
        BigDecimal bigDecimal=new BigDecimal(100);
        Integer preEnroll=0;

        if(universityInfoEnrolling!=null) {
            preEnroll = bigDecimal.multiply(universityInfoEnrolling.getEnrollRate()).intValue();
        }
        return preEnroll;
    }


    /**
     * 是否走位次法
     * @param reportForecastView
     * @param key
     * @return
     */
    private boolean isPre(ReportForecastView reportForecastView,String key){
        String parmasKey = ReportUtil.combSystemParmasKey(reportForecastView.getProvince(), key);

        //是否走位次
        return universityInfoService.enrollingLogin(parmasKey,reportForecastView.getCategorie());
    }






//    /**
//     * 通用算法
//     * @param totalScore
//     * @param areaId
//     * @param majorType
//     * @return
//     */
//    @Override
//    public Object recommendSchool(float totalScore, long areaId, int majorType,long userId) {
//        Integer lastYear = Integer.valueOf(scoreUtil.getYear()) - 1;
//
//        //确定当前分数对应当年批次分数
////        long areaId,int majorType,Float totalScore,String year
//        Object[] line1s = null;
//        try {
//            line1s = scoreUtil.getBatchAndScore(areaId, majorType, totalScore, scoreUtil.getYear());
//        } catch (Exception e) {
//            throw new BizException("error", "当前省份" + scoreUtil.getYear() + "年分数线为空!");
//        }
//        if (line1s[2] == 5) {
//            //todo 假如不足高职专科批次(分数超低)
//
//            //推荐10所高职院校
//            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
//        }
//        int batch = (int) line1s[2];
//        //获得分差1  考生分-16年分数线
//        float difference = totalScore - (Float) line1s[0];
//        //确定点钱分数对应次年批次分数

//        Float line2 = scoreUtil.getLastBatchAndScore(areaId, majorType, batch, lastYear.toString());
//
//        //获得分差2  院校15年分-15年分数线 (15年分数线)
//
//        //计算公式为 lowestScore - line -  difference > = bc  || lowestScore - line -  difference > = -bc
//
//
//        int count = 0;
//        int bc = 10;
//        do {
//            count = scoreAnalysisDAO.countUniversity(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, bc);
//            //增加步长
//            bc += 10;
//        } while (count < 20 && bc < 300);
//        bc -= 10;
//        //返回前20个院校
//        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryUniversityByScore(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, totalScore, bc,userId);
//
//        if(resultList==null){
//            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
//        }else if(resultList.size()==0) {
//
//            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(),userId);
//
//        }
//        return resultList;
//    }
    /**
     * 浙江算法
     * @param totalScore
     * @param areaId
     * @return
     */
    @Override
    public Object recommendSchoolZJ(float totalScore, long areaId,long userId) {


        //一定是三门成绩 否则异常
        String[] subjects =scoreUtil.getZJUserScore(userId);

        Integer lastYear = Integer.valueOf(scoreUtil.getYear()) - 1;
        //计算公式为 学生成绩 - 平均分 > = bc  || 平均分 - 学生成绩 < = bc
        //计算专业提取范围
        Map<String,Object> map = new HashedMap();
        scoreUtil.setSubjectItem(subjects,map);
        map.put("areaId",areaId);
        map.put("year",lastYear.toString());
        map.put("totalScore",totalScore);

        int count = 0;
        int bc = 0;
        do {
            bc += 10;
            map.put("bc",bc);
            count = scoreAnalysisDAO.countZJUniversity(map);
            //增加步长

        } while (count < 20 && bc < 300);
        map.put("userId",userId);
        Integer rows = 20;
        rows+=scoreAnalysisDAO.countMajorRepeat(map);
        map.put("rows",rows);


        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryZJUniversityByScore(map);

        return listToTreeList(resultList);
    }


    /**
     * 逻辑走向
     * @param proCode
     * @param cate
     * @return
     */
    private Integer getLogic(String proCode,Integer cate) {
        LOGGER.info("========算法逻辑走向 start=======");
        LOGGER.info("province:" + proCode);
        LOGGER.info("cate:" + cate);
        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(proCode, ReportUtil.SCORE_ENROLLING_LOGIC, cate);
        if (systemParmas == null)
            return 0;
        Integer logic = Integer.valueOf(systemParmas.getConfigValue());
        LOGGER.info("logic:" + logic);
        LOGGER.info("========算法逻辑走向 start=======");
        return logic;
    }

    /**
     * 当前算法推算院校数量
     * @return
     */
    private Integer getConfigValueInt(String province,Integer categorie,String configKey){
        Map map=new HashedMap();
        map.put("configKey",ReportUtil.combSystemParmasKey(province,configKey));
        map.put("majorType",categorie);
        SystemParmas systemParmas=iSystemParmasService.selectModel(map);
        return Integer.valueOf(systemParmas.getConfigValue());
    }

    /**
     * 当前算法推算院校数量
     * @return
     */
    private String getConfigValueString(String province,Integer categorie,String configKey){
        Map map=new HashedMap();
        map.put("configKey",ReportUtil.combSystemParmasKey(province,configKey));
        map.put("majorType",categorie);
        SystemParmas systemParmas=iSystemParmasService.selectModel(map);
        return systemParmas.getConfigValue();
    }
    /**
     * 当前算法推算院校数量
     * @return
     */
    private String[] getBatchByScore(String province,Integer categorie,Integer score,long areaId){

        Map<String,Object> map = scoreUtil.getTopBatchLine(areaId,categorie,Float.valueOf(score));
        String batch = map.get("batchBottom").toString();
        String newBatch = scoreUtil.ConverNewBatch(batch);

        //
        return new String[]{batch,newBatch};
    }

    /**
     * 成绩分析计算最终录取率
     * @return
     */
    public Integer getResultEnroll(Integer scoreDiffEnroll,String[] randomArr) {
        Integer resultEnroll = scoreDiffEnroll;
        if (scoreDiffEnroll > 0) {
            resultEnroll=scoreDiffEnroll - getEnrollRandom(randomArr);
        }
        return ((resultEnroll == 100 ? 98 : resultEnroll)==0?2 : resultEnroll);
    }
    /**
     * 获取随机录取率范围
     * @return
     */
    private Integer getEnrollRandom(String[] randomArr) {
//        SystemParmas systemParmas = getSystemParmasModelByKey(proCode, key,cate);
//
//        if (systemParmas == null)
//            return null;
//        String enrollRandom = systemParmas.getConfigValue();
//        String[] randomArr = ReportUtil.getEnrollRandomArr(enrollRandom);

        Integer startR = Integer.valueOf(randomArr[0]), endR = Integer.valueOf(randomArr[1]);
        return (int) (startR + Math.random() * endR);
    }

    /**
     * 获取随机录取率范围
     * @param proCode
     * @param key
     * @return
     */
    private String[] getEnrollRandom(String proCode,String key,Integer cate) {
        String string = getConfigValueString(proCode,cate,key);

        if (string == null)
            return null;
        String[] randomArr = ReportUtil.getEnrollRandomArr(string);

        Integer startR = Integer.valueOf(randomArr[0]), endR = Integer.valueOf(randomArr[1]);
        return randomArr;
    }

    /**
     * 获取位次&线差相差阀值
     * @param key
     * @return
     */
    @Deprecated
    private Integer getDiffValue(String proCode,String key,Integer cate) {
        String systemParmas = getConfigValueString(proCode,cate,key);
        return systemParmas == null ? -1 : Integer.valueOf(systemParmas);
    }

    private Map<String, List<Map<String,Object>>> listToTreeList(List<Map<String, Object>> resultList){
        Map<String, List<Map<String,Object>>> treeMap = new LinkedHashMap<>();
        for(Map<String, Object> map : resultList){
            String majorName = map.get("majorName").toString();
            if(treeMap.containsKey(majorName)){

                List<Map<String,Object>> l1=treeMap.get(majorName);
                removeRepeat(l1,map);
            }else {
                List<Map<String,Object>> l1=new ArrayList<>();
                l1.add(map);
                treeMap.put(majorName,l1);
            }
        }
        return treeMap;
    }

    private void removeRepeat(List<Map<String,Object>> mapList,Map<String,Object> map){
        List<Map<String,Object>> mapListCP =new ArrayList<>();
        mapListCP.addAll(mapList);
        for(Map<String, Object> rMap : mapListCP){
            String universityId = rMap.get("universityId").toString();
            if(universityId.equals(map.get("universityId").toString())){
                String majorType = map.get("majorType").toString();
                if(MAJORTYPE_LI.equals(majorType)){
                    mapList.remove(rMap);
                    mapList.add(map);
                    break;
                }
            }else {
                mapList.add(map);
            }
        }

    }

    //======================================================================================================================
}
