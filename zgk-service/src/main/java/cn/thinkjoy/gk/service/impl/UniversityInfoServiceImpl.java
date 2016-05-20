package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportEnum;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.entity.ScoreConverPrecedence;
import cn.thinkjoy.gk.entity.ScoreMaxMin;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IReportResultService;
import cn.thinkjoy.gk.service.IScoreConverPrecedenceService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        boolean isScore = super.isScoreSupplementary(universityInfoParmasView);

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

        Map scoreMap = new HashMap();
        scoreMap.put("tableName", ReportUtil.getOneScoreTableName(parmasView.getProvince(), parmasView.getCategorie()));
        ScoreMaxMin scoreMaxMin = iScoreConverPrecedenceService.selectMaxScore(scoreMap);

        //超过最大及最小分数   按最大、最小分数走
        if (score >= scoreMaxMin.getMaxScore())
            score = scoreMaxMin.getMaxScore();
        if (score <= scoreMaxMin.getMinScore())
            score = scoreMaxMin.getMinScore();

        scoreMap.put("score", score);
        //根据分数 查找对应位次
        ScoreConverPrecedence converPrecedence = iScoreConverPrecedenceService.selectPrecedenceByScore(scoreMap);

        parmasView.setPrecedence(converPrecedence.getAvgPre());

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
    public List<String> getUniversityMajors(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityMajors(map);
    }

}
