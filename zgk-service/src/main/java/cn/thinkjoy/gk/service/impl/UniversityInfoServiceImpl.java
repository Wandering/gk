package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportEnum;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IReportResultService;
import cn.thinkjoy.gk.service.IScoreConverPrecedenceService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 院校清单 --位次规则
 * Created by douzy on 16/3/14.
 */
@Service
public class UniversityInfoServiceImpl extends BaseUniversityInfoServiceImpl implements IUniversityInfoService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityInfoServiceImpl.class);
    @Resource
    IReportResultService iReportResultService;
    @Resource
    IScoreConverPrecedenceService iScoreConverPrecedenceService;

    @Override
    public Integer selectPlanEnrolling(Map map) {
        return iUniversityInfoDao.selectPlanEnrolling(map);
    }

    /**
     * 版本控制           ------V1&V2版本
     * @param
     * @param
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoViewByVersion(UniversityInfoParmasView universityInfoParmasView) {
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();

        String tbName = ReportUtil.getTableName(universityInfoParmasView.getProvince(), universityInfoParmasView.getCategorie(),
                universityInfoParmasView.getBatch(), (universityInfoParmasView.getPrecedence() > 0 ? true : false));

        Map map = new HashMap<>();
        map.put("tableName", tbName);
        LOGGER.info("tableName:" + tbName);
        map.put("province", universityInfoParmasView.getProvince());//key
        map.put("majorType", universityInfoParmasView.getCategorie());


        switch (universityInfoParmasView.getVersion()) {
            case 1:               //线差及位次法
                if (universityInfoParmasView.getPrecedence() > 0) {
                    Integer result = iReportResultService.getPrecedence(tbName, universityInfoParmasView.getPrecedence());
                    map.put("precedence", result);

                } else {
                    LOGGER.info("==线差计算 Start==");
                    //根据分数及控制线 计算线差
                    Integer lineDiff = super.getLineDiff(universityInfoParmasView.getBatch(), universityInfoParmasView.getScore(),
                            universityInfoParmasView.getCategorie(), universityInfoParmasView.getProvince());
                    LOGGER.info("线差为:" + lineDiff);
                    LOGGER.info("==线差计算 End==");
                    map.put("scoreDiff", lineDiff);
                }
                universityInfoViews = super.selectUniversityInfo(map);
                break;
            case 2:    //排名法
                universityInfoViews = getUniversityByPrecedence(map, tbName, universityInfoParmasView);
                break;
        }
        return universityInfoViews;
    }

    /**
     * 分数转换位次
     * @param reportParm
     * @return
     */
    @Override
    public Integer converPreByScore(ReportForecastView reportParm,String key  ) {
        //一分一段 查找分数对应位次
        Integer prevPre= iScoreConverPrecedenceService.converPrecedenceByScore(reportParm.getScore(), reportParm.getProvince(), reportParm.getCategorie(), reportParm.getBatch());
        //找位次临近值
        String tableName= ReportUtil.getTableName(reportParm.getProvince(), reportParm.getCategorie(), reportParm.getBatch(), isPre(reportParm,key));

        return iReportResultService.getPrecedence(tableName,prevPre);
    }

    /**
     * 分数转换线差
     * @param parmasView
     * @return
     */
    @Override
    public Integer converScoreDiffByScore(ReportForecastView parmasView){
        return super.getLineDiff(parmasView.getBatch(), parmasView.getScore(), parmasView.getCategorie(), parmasView.getProvince());
    }
    /**
     * 位次法
     * @param map
     * @param tbName
     * @param universityInfoParmasView
     * @return
     */
    private  List<UniversityInfoView> getUniversityByPrecedence(Map map,String tbName,UniversityInfoParmasView universityInfoParmasView) {
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();
        if (universityInfoParmasView.getPrecedence() > 0) {
            Integer result = iReportResultService.getPrecedence(tbName, universityInfoParmasView.getPrecedence());
            map.put("precedenceParmas", result);
        }
        map.put("precedence", universityInfoParmasView.getPrecedence()); //user precedence
        map.put("first", universityInfoParmasView.getFirst());
        map.put("batch", universityInfoParmasView.getBatch());

        //判定算法走向
        boolean isScore = super.isScoreSupplementary(universityInfoParmasView,"4");


        if (isScore) // true 走分数补充发    false 走位次法
            universityInfoViews = super.selectUniversityInfoByScore(map);
        else
            universityInfoViews = super.selectUniversityInfoByRanking(map);
        return universityInfoViews;
    }

    /**
     * 分数转化位次后 位次法
     * @param map
     * @param tbName
     * @param parmasView
     * @return
     */
    private  List<UniversityInfoView> getUniversityByScoreConver(Map map,String tbName,UniversityInfoParmasView parmasView) {
        LOGGER.info("======================分数转化  Start====================");
        Integer score = parmasView.getScore();
        Integer avgPre=iScoreConverPrecedenceService.converPrecedenceByScore(score, parmasView.getProvince(), parmasView.getCategorie(),parmasView.getBatch());
//        Map scoreMap = new HashMap();
//        scoreMap.put("tableName", ReportUtil.getOneScoreTableName(parmasView.getProvince(), parmasView.getCategorie(),parmasView.getBatch()));
//        ScoreMaxMin scoreMaxMin = iScoreConverPrecedenceService.selectMaxScore(scoreMap);
//
//        //超过最大及最小分数   按最大、最小分数走
//        if (score >= scoreMaxMin.getMaxScore())
//            score = scoreMaxMin.getMaxScore();
//        if (score <= scoreMaxMin.getMinScore())
//            score = scoreMaxMin.getMinScore();
//
//        scoreMap.put("score", score);
//        //根据分数 查找对应位次
//        ScoreConverPrecedence converPrecedence = iScoreConverPrecedenceService.selectPrecedenceByScore(scoreMap);

        parmasView.setPrecedence(avgPre);

        LOGGER.info("======================分数转化  End====================");
        return this.getUniversityByPrecedence(map, tbName, parmasView);
    }

    /**
     * 线差法
     * @param map
     * @param tbName
     * @param universityInfoParmasView
     * @return
     */
    private  List<UniversityInfoView> getUniversityByLineDiff(Map map,String tbName,UniversityInfoParmasView universityInfoParmasView) {
        LOGGER.info("======================线差法  Start====================");
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();
        LOGGER.info("==线差计算 Start==");
        //根据分数及控制线 计算线差
        Integer lineDiff = super.getLineDiff(universityInfoParmasView.getBatch(), universityInfoParmasView.getScore(),
                universityInfoParmasView.getCategorie(), universityInfoParmasView.getProvince());

        map.put("scoreDiff", lineDiff);
        map.put("first", universityInfoParmasView.getFirst());
        map.put("batch", universityInfoParmasView.getBatch());
        map.put("score",universityInfoParmasView.getScore());

        universityInfoViews = selectUniversityInfoByLineDiff(map);


        LOGGER.info("线差为:" + lineDiff);
        LOGGER.info("==线差计算 End==");
        LOGGER.info("======================线差法  End====================");
        return universityInfoViews;
    }
    /**
     * 院校清单筛选  -- 根据逻辑走向      V3版本    线差法&位次法&分数转位次
     * ps:默认走位次法.
     * @param
     * @param
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoViewByLogic(UniversityInfoParmasView universityInfoParmasView) {
        LOGGER.info("======================算法 V3 Start====================");
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();

        ReportEnum.LogicTrend logicTrend = ReportEnum.LogicTrend.getLogic(universityInfoParmasView.getLogic());

        String tbName = ReportUtil.getTableName(universityInfoParmasView.getProvince(), universityInfoParmasView.getCategorie(),
                universityInfoParmasView.getBatch(), (logicTrend.equals(ReportEnum.LogicTrend.LINEDIFF) ? false : true));

        Map map = new HashMap<>();
        map.put("tableName", tbName);
        LOGGER.info("tableName:" + tbName);
        map.put("province", universityInfoParmasView.getProvince());//key
        map.put("majorType", universityInfoParmasView.getCategorie());
        map.put("areaId",universityInfoParmasView.getAreaId());
        LOGGER.info("省份:" + universityInfoParmasView.getProvince());
        LOGGER.info("科类:" + universityInfoParmasView.getCategorie());

        switch (logicTrend) {
            case PRECEDENCE://位次
                LOGGER.info("逻辑走向:位次法");
                universityInfoViews = getUniversityByPrecedence(map, tbName, universityInfoParmasView);
                break;
            case SCORECONVER://分数转换位次
                LOGGER.info("逻辑走向:分数转化");
                universityInfoViews = getUniversityByScoreConver(map, tbName, universityInfoParmasView);
                break;
            case LINEDIFF://线差
                LOGGER.info("逻辑走向:线差");
                universityInfoViews = getUniversityByLineDiff(map, tbName, universityInfoParmasView);
                break;
            default:
                LOGGER.info("逻辑走向:默认-位次法");
                universityInfoViews = getUniversityByPrecedence(map, tbName, universityInfoParmasView);
        }
        LOGGER.info("======================算法 V3 End====================");
        return universityInfoViews;
    }

    private boolean isPre(ReportForecastView reportForecastView,String key){
        String parmasKey = ReportUtil.combSystemParmasKey(reportForecastView.getProvince(), key);

        //是否走位次
        return enrollingLogin(parmasKey,reportForecastView.getCategorie());
    }

    /**
     * 录取难易预测
     * @param reportForecastView 参数打包
     * @return
     */
    @Override
    public String getEnrollingByForecast(ReportForecastView reportForecastView) {

//        String parmasKey = ReportUtil.combSystemParmasKey(reportForecastView.getProvince(), ReportUtil.FORECAST_ENROLLING_LOGIC);
//
//        //是否走位次
//        boolean isPre = enrollingLogin(parmasKey,reportForecastView.getCategorie());

        Integer preEnroll = 0, scoreDiffEnroll = 0, resultEnroll = 0;

        scoreDiffEnroll = getScoreDiffEnrolling(reportForecastView);
        if (isPre(reportForecastView,ReportUtil.FORECAST_ENROLLING_LOGIC))
            preEnroll = getPreEnrolling(reportForecastView);

        String[] configkeyArr = {ReportUtil.FORECAST_ENROLLING_DIFF, ReportUtil.FORECAST_ENROLLING_RANDOM};

        resultEnroll = getResultEnroll(reportForecastView,preEnroll, scoreDiffEnroll,configkeyArr);

        return resultEnroll.toString();
    }

    /**
     * 计算最终录取率
     * @return
     */
    private Integer getResultEnroll(ReportForecastView forecastView ,Integer preEnroll,Integer scoreDiffEnroll,String[] configKeyArr) {
        Integer resultEnroll = scoreDiffEnroll;
        if (preEnroll > 0) {
            String proCode = forecastView.getProvince(), diffConKey = configKeyArr[0], randomConkey = configKeyArr[1];
            Integer cate=forecastView.getCategorie();
            Integer diffV = getDiffValue(proCode, diffConKey,cate);
            resultEnroll = (preEnroll - scoreDiffEnroll) > diffV ? preEnroll - getEnrollRandom(proCode, randomConkey,cate) : scoreDiffEnroll;
        }
        return (resultEnroll == 100 ? 98 : resultEnroll);
    }

    /**
     * 获取随机录取率范围
     * @param proCode
     * @param key
     * @return
     */
    private Integer getEnrollRandom(String proCode,String key,Integer cate) {
        SystemParmas systemParmas = getSystemParmasModelByKey(proCode, key,cate);

        if (systemParmas == null)
            return null;
        String enrollRandom = systemParmas.getConfigValue();
        String[] randomArr = ReportUtil.getEnrollRandomArr(enrollRandom);

        Integer startR = Integer.valueOf(randomArr[0]), endR = Integer.valueOf(randomArr[1]);
        return (int) (startR + Math.random() * endR);
    }
    /**
     * 获取位次&线差相差阀值
     * @param key
     * @return
     */
    private Integer getDiffValue(String proCode,String key,Integer cate) {
        SystemParmas systemParmas = getSystemParmasModelByKey(proCode,key,cate);
        return systemParmas == null ? -1 : Integer.valueOf(systemParmas.getConfigValue());
    }
    private SystemParmas getSystemParmasModelByKey(String proCode,String key,Integer cate) {
        String parmasKey = ReportUtil.combSystemParmasKey(proCode, key);
        Map map = new HashMap();
        map.put("configKey", parmasKey);
        map.put("majorType",cate);
        return iSystemParmasService.selectModel(map);
    }
    /**
     * 线差录取率
     * @param reportForecastView
     * @return
     */
    private Integer getScoreDiffEnrolling(ReportForecastView reportForecastView){
        BigDecimal bigDecimal=new BigDecimal(100);
        Integer preEnroll=0;
        //位次获得值
        List<UniversityInfoEnrolling> universityInfoEnrollings= getUniversityEnrollingsByScoreDiff(reportForecastView);

        if(universityInfoEnrollings!=null) {
            UniversityInfoEnrolling universityInfoEnrolling = universityInfoEnrollings.get(0);
            preEnroll = bigDecimal.multiply(universityInfoEnrolling.getEnrollRate()).intValue();
        }
        return preEnroll;
    }
    /**
     * 位次录取率
     * @param reportForecastView
     * @return
     */
    private Integer getPreEnrolling(ReportForecastView reportForecastView){
        BigDecimal bigDecimal=new BigDecimal(100);
        Integer preEnroll=0;
        //位次获得值
        List<UniversityInfoEnrolling> universityInfoEnrollings=getUniversityEnrollingsByPrecedence(reportForecastView);

        if(universityInfoEnrollings!=null) {
            UniversityInfoEnrolling universityInfoEnrolling = universityInfoEnrollings.get(0);
            preEnroll = bigDecimal.multiply(universityInfoEnrolling.getEnrollRate()).intValue();
        }
        return preEnroll;
    }
    /**
     * 难易预测 位次
     * @param reportParm
     * @return
     */
    @Override
    public List<UniversityInfoEnrolling> getUniversityEnrollingsByPrecedence(ReportForecastView reportParm) {
        Map parmasMap = new HashMap();
        parmasMap.put("tableName", ReportUtil.getTableName(reportParm.getProvince(), reportParm.getCategorie(), reportParm.getBatch(), true));
        parmasMap.put("universityId", reportParm.getUid());
        parmasMap.put("precedence", reportParm.getPrecedence());
        parmasMap.put("isJoin", reportParm.isJoin());
        List<UniversityInfoEnrolling> universityInfoEnrollings = iUniversityInfoDao.selectUniversityEnrolling(parmasMap);
        return universityInfoEnrollings;
    }

    /**
     * 难易预测 线差
     * @param reportParm
     * @return
     */
    @Override
    public List<UniversityInfoEnrolling> getUniversityEnrollingsByScoreDiff(ReportForecastView reportParm) {
        Map parmasMap = new HashMap();
        parmasMap.put("tableName", ReportUtil.getTableName(reportParm.getProvince(), reportParm.getCategorie(), reportParm.getBatch(), false));
        parmasMap.put("universityId", reportParm.getUid());
        parmasMap.put("scoreDiff", reportParm.getScoreDiff());
        parmasMap.put("isJoin", reportParm.isJoin());
        List<UniversityInfoEnrolling> universityInfoEnrollings = iUniversityInfoDao.selectUniversityEnrolling(parmasMap);
        return universityInfoEnrollings;
    }
    @Override
    public List<Map<String, Object>> getUniversityEnrollingConditions(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityEnrollingConditions(map);
    }

    @Override
    public List<Map<String, Object>> getMajorEnrollingConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorEnrollingConditions(map);
    }

    @Override
    public List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorPlanConditions(map);
    }

    @Override
    public List<Map<String, Object>> getUniversityMajors(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityMajors(map);
    }

    @Override
    public List<Map<String, Object>> getUniversityspecialMajors(Object universityId) {
        return iUniversityInfoDao.getUniversityspecialMajors(universityId);
    }

    @Override
    public List<Map<String, Object>> getUniversityEnrollingInfo(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityEnrollingInfo(map);
    }

    @Override
    public List getUniversityMajorListByUniversityId(Map<String, Object> condition,int offset,int rows,String orderBy,String sortBy,Map<String, Object> selectorpage) {
        return iUniversityInfoDao.getUniversityMajorListByUniversityId(condition,offset,rows,orderBy,sortBy,selectorpage);
    }

    @Override
    public List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map) {
        return iUniversityInfoDao.getBatchByYearAndArea(map);
    }
    public  List<UniversityEnrollView> selectUnivEnrollInfo(Map<String, Object> condition,
                                                            String sortBy){
        return iUniversityInfoDao.selectUnivEnrollInfo(condition,sortBy);
    }
}
