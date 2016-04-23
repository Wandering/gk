package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportEnum;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IUniversityInfoDao;
import cn.thinkjoy.gk.entity.RankingRoleParmas;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.UniversityEnrollingView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IBaseUniversityInfoService;
import cn.thinkjoy.gk.service.IRankingRoleParmasService;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by douzy on 16/3/25.
 */
@Service
public class BaseUniversityInfoServiceImpl implements IBaseUniversityInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUniversityInfoServiceImpl.class);

    @Resource
    IUniversityInfoDao iUniversityInfoDao;
    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityMajorEnrollingService iUniversityMajorEnrollingService;
    @Resource
    IRankingRoleParmasService iRankingRoleParmasService;

    /**
     * 院校清单List【线差法】      --- 有位次 按位次   没有位次  按分数
     *
     * @param map
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfo(Map map) {
        LOGGER.info("=====院校清单--线差法 Start=====");
        //录取率 利用率 筛选
        map = converThreshold(map);
        List<UniversityInfoView> universityInfoViews = iUniversityInfoDao.selectUniversityInfo(map);
        LOGGER.info("录取&利用-筛选后院校总数:" + universityInfoViews.size());

        Integer cate=Integer.valueOf(map.get("majorType").toString());

        List<UniversityInfoView> universityInfoViews1 = gradientSplit(map.get("province").toString(),cate, universityInfoViews);

        LOGGER.info("梯度规则-筛选后院校总数:" + universityInfoViews1.size());
        List<UniversityInfoView> universityInfoViewsResult = setUniversityProper(map.get("province").toString(),cate,universityInfoViews1);
        LOGGER.info("=====院校清单--线差法 End=====");
        return universityInfoViewsResult;
    }

    /**
     * 院校清单List【排名法】---用户位次<8k走此规则|  按院校排名   规则见DB zgk_system_parmas
     * @param map
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoByRanking(Map map) {
        LOGGER.info("=====院校清单--排名法 Start=====");
        //根据用户位次 搜索相应动态参数规则
        List<RankingRoleParmas> rankingRoleParmases= getRankingParmasByPrecedence(map);
        LOGGER.info("动态参数集Size:"+rankingRoleParmases.size());
        //筛选参数集
        List<Map> parmasMapList=getParmasMapByRoleParmas(map, rankingRoleParmases);
        LOGGER.info("db参数集:"+ parmasMapList.size());
        //清单组合
        List<UniversityInfoView> universityInfoViewsResult =getUniversityByRoleMap(parmasMapList);

        Integer cate=Integer.valueOf(map.get("majorType").toString());

        //set部分额外属性
        List<UniversityInfoView> universityInfoViewList= setUniversityProper(map.get("province").toString(),cate,universityInfoViewsResult);

        //按冲稳保垫排序
        Collections.sort(universityInfoViewList);

        LOGGER.info("=====院校清单--排名法 Start=====");
        return universityInfoViewList;
    }

    /**
     * 院校清单List【分数法】 --若用户分数在批次线限制范围 走此规则   ， 不在限制范围 走位次发
     * @param map
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoByScore(Map map) {
        LOGGER.info("=====院校清单--分数补充法 Start=====");
        //根据用户位次 搜索相应动态参数规则
        List<RankingRoleParmas> rankingRoleParmases=getRankingParmasByBatch(map);
        //筛选参数集
        List<Map> parmasMapList=getParmasMapByRoleParmas(map, rankingRoleParmases);
        LOGGER.info("db参数集:"+ parmasMapList.size());
        //清单组合
        List<UniversityInfoView> universityInfoViewsResult =getUniversityByRoleMap(parmasMapList);
        Integer cate=Integer.valueOf(map.get("majorType").toString());

        //set部分额外属性
        List<UniversityInfoView> universityInfoViewList= setUniversityProper(map.get("province").toString(),cate,universityInfoViewsResult);

        //按冲稳保垫排序
        Collections.sort(universityInfoViewList);

        LOGGER.info("=====院校清单--分数补充法 End=====");
        return universityInfoViewList;
    }
    /**
     * 根据批次信息获取排位法动态参数
     * @param map
     * @return
     */
    private List<RankingRoleParmas> getRankingParmasByBatch(Map map) {
        LOGGER.info("=====根据位次信息获取排位法动态参数 Start=====");
        String proCode = map.get("province").toString(), first = map.get("first").toString();
        String cate = map.get("majorType").toString(), batchStr = map.get("batch").toString();
        LOGGER.info("province:" + proCode);
        LOGGER.info("first:" + first);
        LOGGER.info("majorType:" + cate);
        LOGGER.info("batchStr:" + batchStr);

        Integer firstValue = Integer.valueOf(first),  majorType = Integer.valueOf(cate);
        Integer rangeIndex = iSystemParmasService.getRankingRangeIndex(batchStr, proCode, majorType);

        LOGGER.info("rangeIndex:" + rangeIndex);

        if (rangeIndex < 0)
            return null;


        Map searchMap = new HashMap();
        searchMap.put("precedenceIndex", rangeIndex);
        searchMap.put("provinceCode", proCode);
        searchMap.put("whoFirst", firstValue);
        searchMap.put("majorType", cate);
        searchMap.put("isScore", 1);//标示为分数补充法
        List<RankingRoleParmas> rankingRoleParmas = iRankingRoleParmasService.selectRankingRuleParmasList(searchMap);

        LOGGER.info("参数集总数:" + rankingRoleParmas.size());
        LOGGER.info("=====根据位次信息获取排位法动态参数 End=====");
        return rankingRoleParmas;
    }

    /**
     * 是否与分数补充法规则匹配
     * @param parmasView
     * @return
     */
    @Override
    public boolean isScoreSupplementary(UniversityInfoParmasView parmasView) {
        boolean result = false;
        //获取对应配置信息
        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(parmasView.getProvince(), ReportUtil.SCORE_ROLE_KEY, parmasView.getCategorie());
        if (systemParmas == null)
            return false;
        Integer isScore = Integer.valueOf(systemParmas.getConfigValue());
        //1:为开启分数补充法
        if (isScore == 1) {

            String conLineScore = iSystemParmasService.getControleLine(parmasView.getBatch(), parmasView.getCategorie(), parmasView.getProvince());

            String[] conLineArr=conLineScore.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
            String[] batArr = ReportUtil.getBatchArr(parmasView.getBatch());
            if(conLineArr.length>1) {
                Integer line = Integer.valueOf(conLineArr[Integer.valueOf(batArr[1]) - 1]);
                if (parmasView.getScore() >= line) {
                    SystemParmas conLinePlusScoreParmas = iSystemParmasService.getThresoldModel(parmasView.getProvince(), ReportUtil.CON_LINE_PLUS_VALUE_KEY, parmasView.getCategorie());
                    if (conLinePlusScoreParmas == null)
                        return false;
                    //批次线追加分
                    Integer plusScore = Integer.valueOf(conLinePlusScoreParmas.getConfigValue());
                    //用户分数小于(批次线+批次线追加分)
                    if (parmasView.getScore() <= Integer.valueOf(line) + plusScore) {
                        result = true;
                    }
                }
            }else {
                //用户分数大于 批次线
                if (parmasView.getScore() >= Integer.valueOf(conLineScore)) {
                    SystemParmas conLinePlusScoreParmas = iSystemParmasService.getThresoldModel(parmasView.getProvince(), ReportUtil.CON_LINE_PLUS_VALUE_KEY, parmasView.getCategorie());
                    if (conLinePlusScoreParmas == null)
                        return false;
                    //批次线追加分
                    Integer plusScore = Integer.valueOf(conLinePlusScoreParmas.getConfigValue());
                    //用户分数小于(批次线+批次线追加分)
                    if (parmasView.getScore() <= Integer.valueOf(conLineScore) + plusScore)
                        result = true;
                }
            }
        }
        return result;
    }
    /**
     * 根据分数及控制线 计算线差
     *
     * @return
     */
    @Override
    public Integer getLineDiff(String batch, Integer score, Integer cate, String provinceCode) {
        String[] bch = batch.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
        String controleLine = iSystemParmasService.getControleLine(bch[0], cate, provinceCode);
        String[] conLineArr = controleLine.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
        Integer line = 0;
        if (conLineArr.length > 1) {
            line = (score - Integer.valueOf(conLineArr[Integer.valueOf(bch[1])]));
        } else
            line = (score - Integer.valueOf(conLineArr[0]));

        return line;
    }

    /*************************************************排位法*************************************************/

    /**
     * 根据位次信息获取排位法动态参数
     * @param map
     * @return
     */
    private List<RankingRoleParmas> getRankingParmasByPrecedence(Map map) {
        LOGGER.info("=====根据位次信息获取排位法动态参数 Start=====");
        //省份 , 位次 , 专业or院校 ?
        String proCode = map.get("province").toString(), precedence = map.get("precedence").toString(), first = map.get("first").toString();
        String cate = map.get("majorType").toString();

        LOGGER.info("province:" + proCode);
        LOGGER.info("precedence:" + precedence);
        LOGGER.info("first:" + first);
        LOGGER.info("cate:" + cate);


        if (StringUtils.isBlank(proCode) && StringUtils.isBlank(precedence)
                && StringUtils.isBlank(first))
            return null;
        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(proCode, ReportUtil.VOLUNTEER_RANKING_VALUE_KEY, Integer.valueOf(cate));
        if (systemParmas == null)
            return null;
        //1:一批 2：二批 3：高职高专 4:三批
        Integer batch = Integer.valueOf(ReportUtil.getBatchArr(map.get("batch").toString())[0]);

        LOGGER.info("batch:" + batch);

        Integer preNum = (batch == 3 ? Integer.valueOf(systemParmas.getConfigValue()) : Integer.valueOf(precedence)), firstValue = Integer.valueOf(first);

        LOGGER.info("阀值:" + preNum);

        Integer rangeIndex = iSystemParmasService.getRankingRangeIndex(proCode, preNum, Integer.valueOf(cate));

        LOGGER.info("rangeIndex:" + rangeIndex);

        if (rangeIndex < 0)
            return null;

        Map searchMap = new HashMap();
        searchMap.put("precedenceIndex", rangeIndex);
        searchMap.put("provinceCode", proCode);
        searchMap.put("whoFirst", firstValue);
        searchMap.put("majorType", cate);
        searchMap.put("isScore", 0);
        List<RankingRoleParmas> rankingRoleParmas = iRankingRoleParmasService.selectRankingRuleParmasList(searchMap);

        LOGGER.info("参数集总数:" + rankingRoleParmas.size());
        LOGGER.info("=====根据位次信息获取排位法动态参数 end=====");
        return rankingRoleParmas;

    }

    /**
     * 根据动态参数生成DB Parmas集  --
     * @param rankingRoleParmases
     * @return
     */
    private List<Map> getParmasMapByRoleParmas(Map initMap,List<RankingRoleParmas> rankingRoleParmases) {
        LOGGER.info("=====生成DB Parmas集 Start=====");
        if (rankingRoleParmases == null)
            return null;
        LOGGER.info("RankingRoleParmas Size:" + rankingRoleParmases.size());
        List<Map> parmasMaps = new ArrayList<>();

        LOGGER.info("tableName:" + initMap.get("tableName"));
        LOGGER.info("province:" + initMap.get("province"));
        LOGGER.info("majorType:" + initMap.get("majorType"));
        LOGGER.info("precedenceParmas:" + initMap.get("precedenceParmas"));

        for (RankingRoleParmas rankingRoleParmas : rankingRoleParmases) {
            Map map = new HashMap();
            map.put("tableName", initMap.get("tableName"));
            map.put("province", initMap.get("province"));//key
            map.put("majorType", initMap.get("majorType"));
            map.put("precedenceParmas", initMap.get("precedenceParmas"));
            //院校排名规则
            if (rankingRoleParmas.getWhoDim() == ReportEnum.RankDim.RANK.getValue()) {
                LOGGER.info("-------走排名规则 start-------");
                ArrayList<Integer> roleArr = ReportUtil.strSplit(rankingRoleParmas.getRankRuleParmas(), ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
                map.put("rankStart", roleArr.get(0));
                map.put("rankEnd", roleArr.get(1));
                map.put("isRank", true);
                LOGGER.info("rankStart:"+roleArr.get(0));
                LOGGER.info("rankEnd:"+ roleArr.get(1));
                LOGGER.info("-------走排名规则 end-------");
            }
            //院校录取率规则
            if (rankingRoleParmas.getWhoDim() == ReportEnum.RankDim.ENROLLING.getValue()) {
                LOGGER.info("-------走录取率规则 start-------");
                ArrayList<Integer> roleArr = ReportUtil.strSplit(rankingRoleParmas.getEnrollingRuleParmas(), ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
                map.put("enrollStart", roleArr.get(0));
                map.put("enrollEnd", roleArr.get(1));

                LOGGER.info("enrollStart:"+roleArr.get(0));
                LOGGER.info("enrollEnd:"+roleArr.get(1));

                if (rankingRoleParmas.getOrderParmas() == 0) {
                    map.put("enrollOrder", "asc");     //录取率从小到大
                    map.put("scoreDiffOrder", "desc"); //线差从大到小
                } else {
                    map.put("enrollOrder", "desc");  //录取率从大到小
                    map.put("scoreDiffOrder", "asc");//线差从小到大
                }

                LOGGER.info("-------排序规则 start-------");
                LOGGER.info("enrollOrder:"+map.get("enrollOrder"));
                LOGGER.info("scoreDiffOrder:"+map.get("scoreDiffOrder"));
                LOGGER.info("-------排序规则 end--------");

                ArrayList<Integer> limitArr = ReportUtil.strSplit(rankingRoleParmas.getLimitParmas(), ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
                map.put("begin", limitArr.get(0));
                map.put("end", limitArr.get(1));
                map.put("isEnrolling", true);

                LOGGER.info("-------走录取率规则 end-------");
            }
            map.put("whoDim", rankingRoleParmas.getWhoDim());
            map.put("isScore",initMap.get("isScore"));
            parmasMaps.add(map);
        }
        LOGGER.info("共生成" + parmasMaps.size() + "组参数集");

        LOGGER.info("=====生成DB Parmas集 Start=====");
        return parmasMaps;
    }

    /**
     * 组合各种规则筛选出来的清单为一个List   然后输出~
     * @param maps
     * @return
     */
    private List<UniversityInfoView>   getUniversityByRoleMap(List<Map> maps) {
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();

        for (Map map : maps) {
            List<UniversityInfoView> universityInfoViewRankList = iUniversityInfoDao.selectUniversityInfoByRanking(map);
            universityInfoViews.addAll(universityInfoViewRankList);
        }

        return universityInfoViews;
    }

    /**
     * 冲稳保垫标签
     * @param universityInfoView
     * @return
     */
    private Integer setUniversityTag(String proCode,Integer cate,UniversityInfoView universityInfoView) {
        LOGGER.info("-------冲稳保垫标签 start-------");
        if (universityInfoView == null)
            return -1;
        BigDecimal enrollRate = universityInfoView.getEnrollRate();
        LOGGER.info("proCode:" + proCode);
        LOGGER.info("cate:" + cate);
        LOGGER.info("录取率:" + enrollRate);

        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(proCode, ReportUtil.CLASSIFY_TAG_KEY, cate);

        if (systemParmas == null)
            return -1;

        BigDecimal maxBigDec = new BigDecimal(100);

        Integer enroll = enrollRate.multiply(maxBigDec).intValue();
        String classifyTagRole = systemParmas.getConfigValue();
        LOGGER.info("录取率转化后:" + enroll);
        LOGGER.info("冲稳保垫层级:" + classifyTagRole);
        String[] classifyArr = classifyTagRole.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
        for (int i = 0; i < classifyArr.length; i++) {
            String str = classifyArr[i];
            String[] classTagArr = str.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
            Integer start = Integer.valueOf(classTagArr[0]);
            Integer end = Integer.valueOf(classTagArr[1]);


            if (enroll >= start && enroll <= end)
                return i;
        }
        LOGGER.info("-------冲稳保垫标签 end-------");
        return -1;
    }
    /*************************************************排位法*************************************************/
    /**
     * 录取率规则 及 利用率规则
     *
     * @param map
     * @return
     */
    private Map converThreshold(Map map) {
        LOGGER.info("=====录取率&利用率规则 Start=====");
        String proCode = map.get("province").toString();
        Integer majorType= Integer.valueOf(map.get("majorType").toString());
        LOGGER.info("省份:" + proCode);
        if (!StringUtils.isBlank(proCode)) {

            //录取率规则
            ArrayList<Integer> enrollRateArr = iSystemParmasService.getEnrollRate(proCode,majorType);
            //利用率规则
            ArrayList<Integer> scoreUsedArr = iSystemParmasService.getUsedRate(proCode,majorType);

            if (enrollRateArr == null || scoreUsedArr == null)
                return map;
            Integer enroll0 = enrollRateArr.get(0), enroll1 = enrollRateArr.get(1), used0 = scoreUsedArr.get(0), used1 = scoreUsedArr.get(1);

            LOGGER.info("录取率begin:" + enroll0);
            LOGGER.info("录取率end:" + enroll1);
            LOGGER.info("利用率begin:" + used0);
            LOGGER.info("利用率end:" + used1);
            map.put("enrollRateBegin", enroll0);
            map.put("enrollRateEnd", enroll1);
            map.put("scoreUseRateBegin", used0);
            map.put("scoreUseRateEnd", used1);
        }
        LOGGER.info("=====录取率&利用率规则 End=====");
        return map;
    }
    /**
     * 梯度拆分
     *
     * @return
     */
    private List<UniversityInfoView> gradientSplit(String province, Integer cate, List<UniversityInfoView> oldUniversityInfos) {
        LOGGER.info("=====梯度拆分规则 Start=====");
        if (StringUtils.isBlank(province))
            return null;
        if (oldUniversityInfos == null)
            return null;
        List<UniversityInfoView> universityInfoViews = new ArrayList<UniversityInfoView>();

        //获取录取率梯度规则
        SystemParmas enrollSystemParmas = iSystemParmasService.getThresoldModel(province, ReportUtil.VOLUNTEER_ENROLL_KEY,cate);
        //获取利用率梯度规则
        SystemParmas usedSystemParmas = iSystemParmasService.getThresoldModel(province, ReportUtil.VOLUNTEER_USED_KEY,cate);
        LOGGER.info("录取率规则串:" + enrollSystemParmas.getConfigValue());
        LOGGER.info("利用率规则串:" + usedSystemParmas.getConfigValue());
        //梯度范围
        Integer volunteerSize = ReportUtil.getVolunteerCount(enrollSystemParmas.getConfigValue());
        LOGGER.info("共分" + volunteerSize + "个梯度");
        BigDecimal maxDec = new BigDecimal(100);
        for (UniversityInfoView universityInfoView : oldUniversityInfos) {
//            UniversityInfoView resultUniversity=new UniversityInfoView();
            //录取率
            BigDecimal enroll = universityInfoView.getEnrollRate(),
                    used = universityInfoView.getScoreUseRate();
            double erollD = enroll.multiply(maxDec).intValue(),
                    usedD = used.multiply(maxDec).intValue();
//            resultUniversity=universityInfoView;
            LOGGER.info("当前院校录取率:" + enroll);
            LOGGER.info("当前院校利用率:" + usedD);
            for (int i = 0; i < volunteerSize; i++) {

                ArrayList<Integer> volRoleArr = ReportUtil.getVolunteer(enrollSystemParmas.getConfigValue(), (i + 1));
                ArrayList<Integer> usedRoleArr = ReportUtil.getVolunteer(usedSystemParmas.getConfigValue(), (i + 1));

                Integer enroll0 = volRoleArr.get(0), enroll1 = volRoleArr.get(1),
                        used0 = usedRoleArr.get(0), used1 = usedRoleArr.get(1);
                LOGGER.info("第" + (i + 1) + "录取率梯度规则Begin:" + enroll0);
                LOGGER.info("第" + (i + 1) + "录取率梯度规则End:" + enroll1);
                LOGGER.info("第" + (i + 1) + "利用率梯度规则Begin:" + used0);
                LOGGER.info("第" + (i + 1) + "利用率梯度规则End:" + used1);

                //利用率达标
                if (erollD >= enroll0 && erollD <= enroll1
                        && usedD >= used0 && usedD <= used1) {
                    //为了去除循环应用$ref  暂时这样处理 。  属于业务问题 ， 一个院校被分配到了多个梯度
//                    UniversityInfoView resultUniversity=new UniversityInfoView();
//                    resultUniversity=universityInfoView;
                    universityInfoView.setSequence((i + 1));

                    universityInfoViews.add(universityInfoView);
                    i = volunteerSize; //符合一个梯度后  不会再向下匹配
                }
            }
        }
        LOGGER.info("=====梯度拆分规则 End=====");
        return universityInfoViews;
    }

    /**
     * 填充院校个别属性 -- 后期新增属性 在此方法扩展 不操作其他逻辑块
     *
     * @param oldUniversityInfos
     * @return
     */
    private List<UniversityInfoView> setUniversityProper(String proCode,Integer cate,List<UniversityInfoView> oldUniversityInfos) {
        LOGGER.info("=====填充院校个别属性 Start=====");
        List<UniversityInfoView> universityInfoViews = new ArrayList<UniversityInfoView>();

        for (UniversityInfoView universityInfoView : oldUniversityInfos) {
            Map planMap = new HashMap();
            planMap.put("universityId", universityInfoView.getUniversityId());
            planMap.put("majorType", converMajorType(universityInfoView.getMajorType()));
            planMap.put("areaId", universityInfoView.getAreaId());
            Integer PlanEnrolling = iUniversityMajorEnrollingService.selectUniversityPlanEnrollingNumber(planMap);
//            Integer PlanEnrolling = selectPlanEnrolling(planMap);
            LOGGER.info("计划招生人数:" + PlanEnrolling);
            universityInfoView.setPlanEnrolling(PlanEnrolling);
            Map averageMap = new HashMap();
            averageMap.put("universityId", universityInfoView.getUniversityId());
            averageMap.put("areaId", universityInfoView.getAreaId());
            averageMap.put("majorType", converMajorType(universityInfoView.getMajorType()));
            averageMap.put("averageScore", 0);//固定给0
            UniversityEnrollingView universityEnrollingView = iUniversityMajorEnrollingService.selectUniversityAverageScore(averageMap);
            universityInfoView.setAverageScore(universityEnrollingView == null ? 0 : universityEnrollingView.getAverageScore());
            universityInfoView.setAverageYear(universityEnrollingView == null ? 0 : universityEnrollingView.getAverageYear());
            Integer seq = setUniversityTag(proCode, cate, universityInfoView); //冲稳保垫
            LOGGER.info("冲稳保垫Tag:" + seq);
            universityInfoView.setSequence(seq);
            universityInfoViews.add(universityInfoView);
        }
        LOGGER.info("=====填充院校个别属性 Start=====");
        return universityInfoViews;
    }
    private Integer converMajorType(String major) {
        return (major.equals("文科") ? 1 : 2);
    }

    /**
     * 院校风险标签
     *
     * @return
     */
    private Map converUniversiTag(Map map) {
        LOGGER.info("=====院校风险标签 Start=====");
//        SystemParmas systemParmas = iSystemParmasService.getRoleByKey(ReportUtil.UNIVERSITY_ENROLL_YEAR_KEY);
//        if (systemParmas != null) {
//            map.put("year", systemParmas.getConfigValue());
//        }
        LOGGER.info("=====院校风险标签 End=====");
        return map;
    }
}
