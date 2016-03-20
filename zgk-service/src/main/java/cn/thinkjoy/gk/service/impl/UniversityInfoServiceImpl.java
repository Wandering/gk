package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IUniversityInfoDao;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.UniversityEnrollingView;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by douzy on 16/3/14.
 */
@Service
public class UniversityInfoServiceImpl implements IUniversityInfoService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UniversityInfoServiceImpl.class);

    @Resource
    IUniversityInfoDao iUniversityInfoDao;

    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityMajorEnrollingService iUniversityMajorEnrollingService;


    /**
     * 录取率规则 及 利用率规则
     * @param map
     * @return
     */
    private Map converThreshold(Map map) {
        LOGGER.info("=====录取率&利用率规则 Start=====");
        String proCode = map.get("province").toString();
        LOGGER.info("省份:"+proCode);
        if (!StringUtils.isBlank(proCode)) {

            //录取率规则
            ArrayList<Integer> enrollRateArr = iSystemParmasService.getEnrollRate(proCode);
            //利用率规则
            ArrayList<Integer> scoreUsedArr = iSystemParmasService.getUsedRate(proCode);

            if (enrollRateArr == null || scoreUsedArr == null)
                return map;
            Integer enroll0=enrollRateArr.get(0)
                    ,enroll1=enrollRateArr.get(1)
                    ,used0=scoreUsedArr.get(0)
                    ,used1=scoreUsedArr.get(1);

            LOGGER.info("录取率begin:"+enroll0);
            LOGGER.info("录取率end:"+enroll1);
            LOGGER.info("利用率begin:"+used0);
            LOGGER.info("利用率end:"+used1);
            map.put("enrollRateBegin", enroll0);
            map.put("enrollRateEnd", enroll1);
            map.put("scoreUseRateBegin", used0);
            map.put("scoreUseRateEnd", used1);
        }
        LOGGER.info("=====录取率&利用率规则 End=====");
        return map;
    }

    /**
     * 院校风险标签
     * @return
     */
    private Map converUniversiTag(Map map) {
        LOGGER.info("=====院校风险标签 Start=====");
        SystemParmas systemParmas = iSystemParmasService.getRoleByKey(ReportUtil.UNIVERSITY_ENROLL_YEAR_KEY);
        if (systemParmas != null) {
            map.put("year", systemParmas.getConfigValue());
        }
        LOGGER.info("=====院校风险标签 End=====");
        return map;
    }
    /**
     * 院校清单List
     * @param map
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfo(Map map) {
        LOGGER.info("=====院校清单 Start=====");
        //录取率 利用率 筛选
        map = converThreshold(map);
        List<UniversityInfoView> universityInfoViews = iUniversityInfoDao.selectUniversityInfo(map);
        LOGGER.info("录取&利用-筛选后院校总数:" + universityInfoViews.size());

        List<UniversityInfoView> universityInfoViews1 = gradientSplit(map.get("province").toString(), universityInfoViews);

        LOGGER.info("梯度规则-筛选后院校总数:" + universityInfoViews1.size());
        List<UniversityInfoView> universityInfoViewsResult = setUniversityProper(universityInfoViews1);
        LOGGER.info("=====院校清单 End=====");
        return universityInfoViewsResult;
    }
    /**
     * 梯度拆分
     * @return
     */
    private List<UniversityInfoView> gradientSplit(String province,List<UniversityInfoView> oldUniversityInfos) {
        LOGGER.info("=====梯度拆分规则 Start=====");
        if (StringUtils.isBlank(province))
            return null;
        if (oldUniversityInfos == null)
            return null;
        List<UniversityInfoView> universityInfoViews = new ArrayList<UniversityInfoView>();

        //获取录取率梯度规则
        SystemParmas enrollSystemParmas = iSystemParmasService.getThresoldModel(province, ReportUtil.VOLUNTEER_ENROLL_KEY);
        //获取利用率梯度规则
        SystemParmas usedSystemParmas=iSystemParmasService.getThresoldModel(province,ReportUtil.VOLUNTEER_USED_KEY);
        LOGGER.info("录取率规则串:"+enrollSystemParmas.getConfigValue());
        LOGGER.info("利用率规则串:"+usedSystemParmas.getConfigValue());
        //梯度范围
        Integer volunteerSize = ReportUtil.getVolunteerCount(enrollSystemParmas.getConfigValue());
        LOGGER.info("共分"+volunteerSize+"个梯度");
        BigDecimal maxDec=new BigDecimal(100);
        for(UniversityInfoView universityInfoView:oldUniversityInfos) {
//            UniversityInfoView resultUniversity=new UniversityInfoView();
            //录取率
            BigDecimal enroll = universityInfoView.getEnrollRate(),
                    used = universityInfoView.getScoreUseRate();
            double erollD = enroll.multiply(maxDec).intValue(),
                    usedD = used.multiply(maxDec).intValue();
//            resultUniversity=universityInfoView;
            LOGGER.info("当前院校录取率:" +enroll);
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
                        &&usedD>=used0&&usedD<=used1) {
                    //为了去除循环应用$ref  暂时这样处理 。  属于业务问题 ， 一个院校被分配到了多个梯度
//                    UniversityInfoView resultUniversity=new UniversityInfoView();
//                    resultUniversity=universityInfoView;
                    universityInfoView.setSequence((i + 1));

                    universityInfoViews.add(universityInfoView);
                    i= volunteerSize; //符合一个梯度后  不会再向下匹配
                }
            }
        }
        LOGGER.info("=====梯度拆分规则 End=====");
        return universityInfoViews;
    }

    /**
     * 填充院校个别属性 -- 后期新增属性 在此方法扩展 不操作其他逻辑块
     * @param oldUniversityInfos
     * @return
     */
    private List<UniversityInfoView> setUniversityProper(List<UniversityInfoView> oldUniversityInfos) {
        LOGGER.info("=====填充院校个别属性 Start=====");
        List<UniversityInfoView> universityInfoViews = new ArrayList<UniversityInfoView>();

        for(UniversityInfoView universityInfoView:oldUniversityInfos) {
            Map planMap = new HashMap();
            planMap.put("universityId", universityInfoView.getUniversityId());
            planMap.put("majorType",(universityInfoView.getMajorType().equals("文科") ? 1 : 2));
            planMap.put("areaId",universityInfoView.getAreaId());
            Integer PlanEnrolling = iUniversityMajorEnrollingService.selectUniversityPlanEnrollingNumber(planMap);
//            Integer PlanEnrolling = selectPlanEnrolling(planMap);
            LOGGER.info("计划招生人数:" + PlanEnrolling);
            universityInfoView.setPlanEnrolling(PlanEnrolling);
            Map averageMap = new HashMap();
            averageMap.put("universityId", universityInfoView.getUniversityId());
            averageMap.put("areaId", universityInfoView.getAreaId());
            averageMap.put("majorType", (universityInfoView.getMajorType().equals("文科") ? 1 : 2));
            averageMap.put("averageScore", 0);//固定给0
            UniversityEnrollingView universityEnrollingView = iUniversityMajorEnrollingService.selectUniversityAverageScore(averageMap);
            universityInfoView.setAverageScore(universityEnrollingView == null ? 0 : universityEnrollingView.getAverageScore());
            universityInfoView.setAverageYear(universityEnrollingView == null ? 0 : universityEnrollingView.getAverageYear());
            universityInfoViews.add(universityInfoView);
        }
        LOGGER.info("=====填充院校个别属性 Start=====");
        return universityInfoViews;
    }

    /**
     * 根据分数及控制线 计算线差
     * @return
     */
    @Override
    public Integer getLineDiff(Integer batch,Integer score,Integer cate, String provinceCode) {
        Integer controleLine = iSystemParmasService.getControleLine(batch, cate, provinceCode);
        return (score - controleLine);
    }
    @Override
    public Integer selectPlanEnrolling(Map map) {
        return iUniversityInfoDao.selectPlanEnrolling(map);
    }

}
