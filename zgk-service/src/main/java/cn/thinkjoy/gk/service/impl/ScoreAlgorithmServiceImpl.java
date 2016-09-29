package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.AreaMaps;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.common.ScoreUtil;
import cn.thinkjoy.gk.common.SortUtilForList;
import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.dao.ISystemParmasDao;
import cn.thinkjoy.gk.entity.CheckBatchMsg;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yangyongping on 16/9/1.
 */
@Service
public class ScoreAlgorithmServiceImpl implements IScoreAlgorithmService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ScoreAlgorithmServiceImpl.class);

    @Resource
    ISystemParmasDao iSystemParmasDao;

    @Resource
    IScoreConverPrecedenceService iScoreConverPrecedenceService;

    @Resource
    ISystemParmasService iSystemParmasService;

    @Resource
    IUniversityInfoService universityInfoService;

    @Resource
    IDataDictService dataDictService;

    @Autowired
    AreaMaps areaMaps;

    @Autowired
    private ScoreUtil scoreUtil;

    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;


    /**
     * 全国版通用算法
     *
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Override
    public Object recommendSchool(float totalScore, long areaId, int majorType, long userId) {

        LOGGER.debug("totalScore:" + totalScore);
        LOGGER.debug("areaId:" + areaId);
        LOGGER.debug("majorType:" + majorType);
        LOGGER.debug("userId:" + userId);


        LOGGER.debug("=======参数整理 Start=======");
        String province = areaMaps.getAreaCode(areaId);
        Integer sap = getLogic(province, majorType);
        Integer score = Integer.parseInt(scoreUtil.floatToStr(totalScore));
        LOGGER.debug("=======参数整理 End=======");

        LOGGER.debug("=======批次及批次控制线信息 Start=======");
        LOGGER.debug("分数||位次:" + sap);
        LOGGER.debug("成绩: " + score);
        LOGGER.debug("科类:" + majorType);
        LOGGER.debug("省份:" + province);


        LOGGER.debug("=======获取推荐学校 Start=======");
        List<Map<String, Object>> resultMaps = getResultListByScore(score, province, majorType, userId, areaId);
        LOGGER.debug("=======获取推荐学校 End========");
        return resultMaps;
    }

    public List<Map<String, Object>> getResultListByScore(Integer score, String province, Integer majorType, long userId, long areaId) {

        String[] batchs = getBatchByScore(province, majorType, score, areaId);
        LOGGER.debug("该学生被定为在(只能填报):" + batchs[1] + "批次");
        LOGGER.debug("该学生被定为在:" + batchs[0] + "批次");
        LOGGER.debug("=======批次及批次控制线信息 End========");

        Map<String, Object> condition = new HashedMap();
        condition.put("userId", userId);
        //这里批次用统一批次
        //整理调取院校的参数
        condition.put("batchs", getBatchs(batchs[0]));
        condition.put("year", getConfigValueInt(province, majorType, ReportUtil.SCORE_ENROLLING_YEAR));
        condition.put("majorType", majorType);
        condition.put("areaId", areaId);

        List<Map<String, Object>> universityInfoEnrollings = getEnrollingByScore(getReportView(batchs, score, province, majorType, userId, areaId));
        LOGGER.debug("=======放置参数 End========");
        if (universityInfoEnrollings != null && universityInfoEnrollings.size() > 0) {
            condition.put("universitys", universityInfoEnrollings);
            //获取排序参数
            String sortBy = getConfigValueString(province, majorType, ReportUtil.SCORE_SORT_BY);
            LOGGER.debug("排序方式:" + sortBy);
            List<UniversityEnrollView> universityEnrollViews = getUniversityEnrollViews(condition, batchs, province, majorType);
            //整理返回值并返回
            return assembleResult(batchs, universityInfoEnrollings, universityEnrollViews,areaId,majorType);
        }
        return new ArrayList<>();
    }

    /**
     * 根据智能填报算法院校获取学校录取信息
     *
     * @param
     * @return
     */
    private List<UniversityEnrollView> getUniversityEnrollViews(Map<String, Object> condition, String[] batchs, String province, Integer majorType) {
        Map<String, Object> univ_exist_map = new HashedMap();
        Map<String, Object> univ_no_exist_map = new HashedMap();
        List<Map<String, Object>> univ_maps = new ArrayList<>();

        //获取能在本批次获取到的学校
        List<Long> univ_ids = universityInfoService.selectUnivInfoIdInBatch(condition);
        //获取能在本批次获取不到的学校
        List<Long> no_univ_ids = getUnivIdList(univ_ids, condition);
        //获取排序方式
        String sortBy = getConfigValueString(province, majorType, ReportUtil.SCORE_SORT_BY);
        LOGGER.debug("排序方式:" + sortBy);
        if (univ_ids.size() > 0) {
            univ_exist_map.putAll(condition);
            univ_exist_map.put("universitys", univ_ids);
            univ_maps.add(univ_exist_map);
        }
        if (no_univ_ids.size() > 0) {
            univ_no_exist_map.putAll(condition);
            univ_no_exist_map.put("universitys", no_univ_ids);
            univ_no_exist_map.put("batchs", getBatchs(getBatchMerge(batchs[0])));
            univ_maps.add(univ_no_exist_map);
        }
        boolean isJoin = (ScoreUtil.JS_AREA_CODE == Long.valueOf(condition.get("areaId").toString()));
        /**
         * 去获取院校数据
         */
        List<UniversityEnrollView> universityEnrollViews = universityInfoService.selectUnivEnrollInfo(univ_maps, isJoin, sortBy);
        return universityEnrollViews;
    }

    private List<Long> getUnivIdList(List<Long> list, Map<String, Object> condition) {
        List<Long> no_univ_ids = new ArrayList<>();
        List<Map<String, Object>> universityInfoEnrollings = (List<Map<String, Object>>) condition.get("universitys");
        for (Map<String, Object> universityInfoEnrolling : universityInfoEnrollings) {
            Long univId = universityInfoEnrolling.get("universityId") == null ? null : Long.valueOf(universityInfoEnrolling.get("universityId").toString());
            if (univId == null) continue;
            if (list.lastIndexOf(univId) < 0) {
                no_univ_ids.add(univId);
            }
        }
        return no_univ_ids;
    }


    /**
     * 根据批次code获取它的名称
     *
     * @param batch
     * @return
     */
    @Deprecated
    private String getBatch(String batch) {
        Map<String, Object> dataMap = new HashedMap();
        dataMap.put("type", ScoreUtil.BATCHTYPE2);
        dataMap.put("dictId", batch);
        Map<String, Object> dict = dataDictService.queryDictByDictId(dataMap);
        return dict.get("name").toString();
    }

    /**
     * 根据批次名称获取
     * @param areaId
     * @param majorType
     * @return
     */
    private Map<String, String> getBatchNames(long areaId,int majorType,Map<String,Object> batchScores) {
        Iterator<String> keys = batchScores.keySet().iterator();
        Map<String, Object> dataMap;
        Map<String, String> rtnMap = new HashedMap();
        while (keys.hasNext()){
            dataMap = new HashedMap();
            dataMap.put("type", ScoreUtil.BATCHTYPE2);
            String key = keys.next();
            dataMap.put("dictId", key);
            Map<String, Object> dict = dataDictService.queryDictByDictId(dataMap);
            if (dict!=null){
                rtnMap.put(key,dict.get("name").toString());
            }
        }
        return rtnMap;
    }

    /**
     * 处理批次合并问题
     *
     * @param batch
     * @return
     */
    private String getBatchMerge(String batch) {
        if (batch.length() > 1) {
            batch = batch.substring(0, 1);
        }
        String batchMerge = "";
        switch (batch) {
            case "1":
                batchMerge = "2";
                break;
            case "2":
                batchMerge = "4";
                break;
            case "4":
                batchMerge = "8";
                break;
            case "8":
                batchMerge = "8";
                break;
            default:
                batchMerge = "8";
                break;
        }

        return batchMerge;
    }

    ;

    /**
     * 根据批次获取可能出现的批次
     * (应对类似本科1批A,本科一批B合并或者本科一批拆分为1批AB段的问题)
     * 例子:
     * 11——>1,11,12,13,14
     * 1——>1,11,12,13,14
     *
     * @param batch
     * @return
     */
    private String[] getBatchs(String batch) {
        if (batch.length() > 1) {
            batch = batch.substring(0, 1);
        }
        //组织11,12,13,14,1
        String[] strings = new String[5];
        for (int i = 1; i <= 4; i++) {
            strings[i - 1] = batch + "" + i;
            System.out.println(batch + "" + i);
        }
        strings[4] = batch;
        return strings;
    }

    /**
     * 江苏算法
     *
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    @Deprecated
    @Override
    public Object recommendSchoolJS(float totalScore, long areaId, int majorType, long userId) {
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
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(), userId);
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
        Map<String, Object> scores = scoreUtil.getScoresJS(map, majorType);

        List<String> xcRanks = null;
        if (scores.size() == 5) {
            xcRanks = scoreUtil.getLevelList(scores, majorType);
        } else {
            throw new BizException("error", "科目不正确!");
        }

        /**
         * 这里需要去比对院校招生表
         */
        int count = 0;
        int bc = 0;
        do {
            count = scoreAnalysisDAO.countJSUniversity(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, bc, xcRanks);
            //增加步长
            bc += 10;
        } while (count < 20 && bc < 300);
        bc -= 10;
        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryJSUniversityByScore(areaId, (Integer) line1s[2], majorType, lastYear.toString(), difference, line2, totalScore, bc, xcRanks, userId);

        if (resultList == null) {
            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(), userId);
        } else if (resultList.size() == 0) {

            return scoreAnalysisDAO.queryLowstUniversity(areaId, majorType, totalScore, lastYear.toString(), userId);

        }
        return resultList;
    }


    /**
     * 成绩分析获取推荐院校列表
     *
     * @param reportForecastView 参数打包
     * @return
     */
    private List<Map<String, Object>> getEnrollingByScore(ReportForecastView reportForecastView) {


        List<UniversityInfoEnrolling> universityInfoEnrollings = null;

        //算法走向 0线差 1位次
        if (isPre(reportForecastView, ReportUtil.SCORE_ENROLLING_LOGIC)) {
            LOGGER.debug("这是位次法");
            //  位次法
            universityInfoEnrollings = universityInfoService.getUniversityEnrollingsByPrecedence(reportForecastView);
        } else {
            //线差法
            universityInfoEnrollings = universityInfoService.getUniversityEnrollingsByScoreDiff(reportForecastView);
        }
        Map<String, Object> map = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        String[] enrollRandomStr = getEnrollRandom(reportForecastView.getProvince(), ReportUtil.SCORE_ENROLLING_RANDOM, reportForecastView.getCategorie());
        for (int i = 0; i < universityInfoEnrollings.size(); i++) {
            map = new HashedMap();
            Integer enrollRate = getResultEnroll(
                    getEnrollRate(universityInfoEnrollings.get(i)),
                    enrollRandomStr
            );
            map.put("enrollRate", enrollRate);
            map.put("universityName", universityInfoEnrollings.get(i).getUniversityName());
            map.put("universityId", universityInfoEnrollings.get(i).getUniversityId());
            mapList.add(map);
        }

        LOGGER.debug("推荐出院校个数:" + mapList.size());
        return mapList;
    }


    /**
     * 获取录取率
     *
     * @param universityInfoEnrolling
     * @return
     */
    private Integer getEnrollRate(UniversityInfoEnrolling universityInfoEnrolling) {
        BigDecimal bigDecimal = new BigDecimal(100);
        Integer preEnroll = 0;

        if (universityInfoEnrolling != null) {
            preEnroll = bigDecimal.multiply(universityInfoEnrolling.getEnrollRate()).intValue();
        }
        return preEnroll;
    }


    /**
     * 是否走位次法
     *
     * @param reportForecastView
     * @param key
     * @return
     */
    private boolean isPre(ReportForecastView reportForecastView, String key) {
        String parmasKey = ReportUtil.combSystemParmasKey(reportForecastView.getProvince(), key);

        //是否走位次
        return universityInfoService.enrollingLogin(parmasKey, reportForecastView.getCategorie());
    }

    /**
     * 浙江算法
     *
     * @param totalScore
     * @param areaId
     * @return
     */
    @Override
    public Object recommendSchoolZJ(float totalScore, long areaId, long userId) {


        //一定是三门成绩 否则异常
        String[] subjects = scoreUtil.getZJUserScore(userId);

        Integer lastYear = Integer.valueOf(scoreUtil.getYear()) - 1;
        //计算公式为 学生成绩 - 平均分 > = bc  || 平均分 - 学生成绩 < = bc
        //计算专业提取范围
        Map<String, Object> map = new HashedMap();
        scoreUtil.setSubjectItem(subjects, map);
        map.put("areaId", areaId);
        map.put("year", lastYear.toString());
        map.put("totalScore", totalScore);

        /**
         * 每次循环增加步长,在计算出来的分数+-bc,统计有多少个学校
         * 当学校数量超过一定数量(20)的时候结束循环
         */
        int count = 0;
        int bc = 0;
        do {
            bc += 10;
            map.put("bc", bc);
            count = scoreAnalysisDAO.countZJUniversity(map);
            //增加步长

        } while (count < 20 && bc < 300);
        map.put("userId", userId);
        Integer rows = 20;
        rows += scoreAnalysisDAO.countMajorRepeat(map);
        map.put("rows", rows);

        /**
         * 用统计出来的参数去获取最终学校
         */
        //返回前20个院校
        List<Map<String, Object>> resultList = scoreAnalysisDAO.queryZJUniversityByScore(map);

        /**
         *
         *  做返回之前的部分值转换
         *
         */
        return listToTreeList(resultList);
    }


    /**
     * 逻辑走向
     *
     * @param proCode
     * @param cate
     * @return
     */
    private Integer getLogic(String proCode, Integer cate) {
        LOGGER.debug("========算法逻辑走向 start=======");
        LOGGER.debug("province:" + proCode);
        LOGGER.debug("cate:" + cate);
        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(proCode, ReportUtil.SCORE_ENROLLING_LOGIC, cate);
        if (systemParmas == null)
            return 0;
        Integer logic = Integer.valueOf(systemParmas.getConfigValue());
        LOGGER.debug("logic:" + logic);
        LOGGER.debug("========算法逻辑走向 start=======");
        return logic;
    }

    /**
     * 当前算法推算院校数量
     *
     * @return
     */
    private Integer getConfigValueInt(String province, Integer categorie, String configKey) {
        Map map = new HashedMap();
        map.put("configKey", ReportUtil.combSystemParmasKey(province, configKey));
        map.put("majorType", categorie);
        SystemParmas systemParmas = iSystemParmasService.selectModel(map);
        return Integer.valueOf(systemParmas.getConfigValue());
    }

    /**
     * 当前算法推算院校数量
     *
     * @return
     */
    private String getConfigValueString(String province, Integer categorie, String configKey) {
        Map map = new HashedMap();
        map.put("configKey", ReportUtil.combSystemParmasKey(province, configKey));
        map.put("majorType", categorie);
        SystemParmas systemParmas = iSystemParmasService.selectModel(map);
        return systemParmas.getConfigValue();
    }

    /**
     * 当前算法推算院校数量
     *
     * @return
     */
    private String[] getBatchByScore(String province, Integer categorie, Integer score, long areaId) {

        Map<String, Object> map = scoreUtil.getTopBatchLine(areaId, categorie, Float.valueOf(score));
        String batch = map.get("batchBottom").toString();
        String isLowestVo = map.get("isLowestVo").toString();
        String newBatch = scoreUtil.ConverNewBatch(batch);

        return new String[]{batch, newBatch, isLowestVo};
    }

    /**
     * 成绩分析计算最终录取率
     *
     * @return
     */
    public Integer getResultEnroll(Integer scoreDiffEnroll, String[] randomArr) {
        Integer resultEnroll = scoreDiffEnroll;
        if (scoreDiffEnroll > 0) {
            resultEnroll = scoreDiffEnroll - getEnrollRandom(randomArr);
        }
        /**
         *
         * 计算最终录取率时,
         * 当resultEnroll>98&&resultEnroll<2时候,取2&&98 ,
         * 当resultEnroll<=98&&resultEnroll>=2&&resultEnroll>=98&&resultEnroll>2时候,取2<=resultEnroll<=98
         *
         */
        return (resultEnroll > 98 ? 98 : (resultEnroll < 2 ? 2 : resultEnroll));
    }

    /**
     * 获取随机录取率范围
     *
     * @return
     */
    private Integer getEnrollRandom(String[] randomArr) {
        Integer startR = Integer.valueOf(randomArr[0]), endR = Integer.valueOf(randomArr[1]);
        return (int) (startR + Math.random() * endR);
    }

    /**
     * 获取随机录取率范围
     *
     * @param proCode
     * @param key
     * @return
     */
    private String[] getEnrollRandom(String proCode, String key, Integer cate) {
        /**
         * 从数据库拿出录取率的范围KEY = XX_FORECAST_ENROLLING_RANDOM  xx为省份code  如sn,zj
         */
        String string = getConfigValueString(proCode, cate, key);

        if (string == null)
            return null;
        String[] randomArr = ReportUtil.getEnrollRandomArr(string);
        return randomArr;
    }

    /**
     * 获取位次&线差相差阀值
     *
     * @param key
     * @return
     */
    @Deprecated
    private Integer getDiffValue(String proCode, String key, Integer cate) {
        String systemParmas = getConfigValueString(proCode, cate, key);
        return systemParmas == null ? -1 : Integer.valueOf(systemParmas);
    }

    /**
     * 把线性结构转换成树形结构
     *
     * @param resultList
     * @return
     */
    private Map<String, List<Map<String, Object>>> listToTreeList(List<Map<String, Object>> resultList) {
        Map<String, List<Map<String, Object>>> treeMap = new LinkedHashMap<>();
        for (Map<String, Object> map : resultList) {
            String majorName = map.get("majorName").toString();
            /**
             * 按照专业名称去做树形结构
             */
            if (treeMap.containsKey(majorName)) {

                List<Map<String, Object>> l1 = treeMap.get(majorName);
                removeRepeat(l1, map);
            } else {
                List<Map<String, Object>> l1 = new ArrayList<>();
                l1.add(map);
                treeMap.put(majorName, l1);
            }
        }
        return treeMap;
    }

    /**
     * 去除文理重复的记录
     *
     * @param mapList
     * @param map
     */
    private void removeRepeat(List<Map<String, Object>> mapList, Map<String, Object> map) {
        List<Map<String, Object>> mapListCP = new ArrayList<>();
        mapListCP.addAll(mapList);
        //设置标记位,当标记位为true的时候,当前map添加到mapList中
        boolean flag = true;
        for (Map<String, Object> rMap : mapListCP) {
            /**
             * 使用rmap循环遍历mapList是否有相等的院校相等专业相等的记录,
             * 若有 判断专业类别是否是理科,
             * 假如是理科删除原来的文科的,使用理科的
             */
            Integer universityId = (Integer) rMap.get("universityId");
            if (universityId == map.get("universityId")) {
                Integer majorType = (Integer) map.get("majorType");
                if (ScoreUtil.MAJORTYPE_LI == majorType) {
                    mapList.remove(rMap);
                    mapList.add(map);
                    flag = false;
                    break;
                }
            }
        }
        if (flag) mapList.add(map);

    }

    /**
     * 获取用户所有可获取的选测等级
     *
     * @return
     */
    private List<String> getUserRank(long userId, Integer majorType) {
        /**
         *  拿到用户成绩
         */
        //根据用户ID获取用户上一次测评成绩和测评科目
        Map<String, Object> map = scoreAnalysisDAO.queryScoreRecordByUserId(userId);
        /**
         * =================================
         * 这里需要计算江苏省的选考等级
         * 两种情况
         * 1、单科固定 另一门随机
         * 2、任意一门顺序不限 如:AB  物理A 政治B 或者 政治A 物理B
         * =================================
         */
        Map<String, Object> scores = scoreUtil.getScoresJS(map, majorType);
        List<String> xcRanks = null;
        //判断成绩是否是5门,江苏省成绩为5门
        if (scores.size() == 5) {
            //获取用户分数等级
            xcRanks = scoreUtil.getLevelList(scores, majorType);
        } else {
            throw new BizException("error", "请检查当前用户是否是江苏省用户!");
        }
        return xcRanks;
    }


    /**
     * 整理给智能填报端参数,获取院校
     *
     * @param batchs
     * @param score
     * @param province
     * @param majorType
     * @return
     */
    private ReportForecastView getReportView(String[] batchs, Integer score, String province, Integer majorType, long userId, long areaId) {
        LOGGER.debug("=======放置参数 Start========");
        ReportForecastView reportForecastView = new ReportForecastView();
        //这里批次用智能填报批次
        reportForecastView.setBatch(batchs[1]);
        reportForecastView.setScore(score);
        reportForecastView.setProvince(province);
        reportForecastView.setCategorie(majorType);
        reportForecastView.setJoin(false);

        //江苏省独有规则
        if (areaId == ScoreUtil.JS_AREA_CODE) {
            //需要加入用户选测等级要求
            reportForecastView.setXcRanks(getUserRank(userId, majorType));
            reportForecastView.setYear(Integer.valueOf(scoreUtil.getYear()));
            reportForecastView.setJoin(true);

        }

        //判断逻辑走向是不是位次法,如果是位次法,做分数转换位次
        if (isPre(reportForecastView, ReportUtil.SCORE_ENROLLING_LOGIC)) {
            reportForecastView.setPrecedence(universityInfoService.converPreByScoreV2(reportForecastView, ReportUtil.SCORE_ENROLLING_LOGIC));
        }
        reportForecastView.setScoreDiff(universityInfoService.converScoreDiffByScore(reportForecastView));

        //这里按照录取率从小到大排序,线差从小到大排序
        reportForecastView.setOrderBy(ScoreUtil.SCORE_SORT);
        reportForecastView.setLimit(Integer.valueOf(batchs[2]) == ReportUtil.SCORE_VO_TAG ? ReportUtil.SCORE_VO_LIMIT : getConfigValueInt(province, majorType, ReportUtil.SCORE_ENROLLING_LIMIT));
        return reportForecastView;
    }

    /**
     * 通过智能填报端获取到的学校,和当前获取到的学校信息组装学校
     *
     * @param batchs
     * @param universityInfoEnrollings
     * @param universityEnrollViews
     * @return
     */
    private List<Map<String, Object>> assembleResult(String[] batchs, List<Map<String, Object>> universityInfoEnrollings, List<UniversityEnrollView> universityEnrollViews,long areaId,Integer majorType) {

        LOGGER.debug("=======组装返回值 Start========");
        List<Map<String, Object>> resultList = new ArrayList<>();
        //获取批次对应分数
        Map<String,Object> batchScores = scoreUtil.getBatchLine2(areaId,majorType);
        Map<String,String> batchNames = getBatchNames(areaId,majorType,batchScores);


        /**
         * 循环遍历组装返回数据
         */
        for (UniversityEnrollView universityEnrollView : universityEnrollViews) {
            Map<String, Object> map = new HashedMap();
            LOGGER.debug("当前组装学校:" + universityEnrollView.getUniversityName());
            for (Map<String, Object> hMap : universityInfoEnrollings) {
                if (hMap.get("universityName").equals(universityEnrollView.getUniversityName())) {
                    map.put("isCurrArea", universityEnrollView.getIsCurrArea());
                    map.put("universityName", hMap.get("universityName"));
                    map.put("universityId", hMap.get("universityId"));
                    map.put("enrollRate", hMap.get("enrollRate"));
                    map.put("batch", universityEnrollView.getBatchName() == null ? batchNames.get(batchs[0]) +"("+batchScores.get(batchs[0])+")": universityEnrollView.getBatchName()+"("+batchScores.get(batchs[0])+")");
                    map.put("highestScore", universityEnrollView.getHighestScore());
                    map.put("lowestScore", universityEnrollView.getLowestScore());
                    map.put("averageScore", universityEnrollView.getAverageScore());
                    map.put("xcRank", universityEnrollView.getXcRank());
                    map.put("schoolName", universityEnrollView.getUniversityName());
                    map.put("stuNum", universityEnrollView.getPlanEnrolling());
                    Integer isFavorite = universityEnrollView.getIsFavorite() == null ? 0 : universityEnrollView.getIsFavorite();
                    map.put("isFavorite", isFavorite);
                    resultList.add(map);
                }
            }
        }
        LOGGER.debug("=======组装返回值 End========");

        /**
         * sql中的排序不能完成
         * 成绩分析结果录取院校排序功能没有实现——本地院校（录取率由高到低）－>目标院校（录取率由高到低）－>其他院校（录取率由高到低）
         * 排序在这里进行
         */
        SortUtilForList sortUtilForList = new SortUtilForList();
        Collections.sort(resultList, sortUtilForList);
        return resultList;
    }


}
