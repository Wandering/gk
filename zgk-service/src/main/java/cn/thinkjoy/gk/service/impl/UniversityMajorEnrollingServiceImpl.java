package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IProvinceInfoDao;
import cn.thinkjoy.gk.dao.IUniversityMajorEnrollingPlanDao;
import cn.thinkjoy.gk.entity.ProvinceInfo;
import cn.thinkjoy.gk.entity.UniversityMajorEnrollingPlan;
import cn.thinkjoy.gk.pojo.SpecialtyView;
import cn.thinkjoy.gk.pojo.UniversityEnrollingView;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
@Service
public class UniversityMajorEnrollingServiceImpl implements IUniversityMajorEnrollingService {
    @Resource
    IUniversityMajorEnrollingPlanDao iUniversityMajorEnrollingPlanDao;
    @Resource
    IProvinceInfoDao iProvinceInfoDao;
    @Override
    public List<SpecialtyView> selectList(Map map) {

        Map proMap = new HashMap();
        proMap.put("code", map.get("province"));
        ProvinceInfo provinceInfo = iProvinceInfoDao.getProvinceInfoByCode(proMap);

        map.put("areaId",provinceInfo.getId());
        List<UniversityMajorEnrollingPlan> universityMajorEnrollingPlans = iUniversityMajorEnrollingPlanDao.selectList(map);
        List<SpecialtyView> specialtyViews = new ArrayList<>();
        for (UniversityMajorEnrollingPlan universityMajorEnrollingPlan : universityMajorEnrollingPlans) {
            SpecialtyView specialtyView = new SpecialtyView();
            specialtyView.setUniversityId(universityMajorEnrollingPlan.getUniversityId());
            specialtyView.setUniversityName(universityMajorEnrollingPlan.getUniversityName());
            specialtyView.setSpecialtyName(universityMajorEnrollingPlan.getMajorName());
            specialtyView.setSpecialtyId(universityMajorEnrollingPlan.getMajorId() == null ? -1 : universityMajorEnrollingPlan.getMajorId());
            specialtyView.setMajorType(universityMajorEnrollingPlan.getMajorType());
            specialtyView.setPlanEnrolling(universityMajorEnrollingPlan.getPlanEnrolling());
            specialtyView.setEnrollType(universityMajorEnrollingPlan.getEnrollType());
            specialtyView.setYear(universityMajorEnrollingPlan.getYear());
            specialtyViews.add(specialtyView);
        }
        return specialtyViews;
    }

    @Override
    public Integer lowestScoreAvg(Map map) {
        return iUniversityMajorEnrollingPlanDao.lowestScoreAvg(map);
    }
    @Override
    public UniversityEnrollingView selectUniversityAverageScore(Map map){
        return iUniversityMajorEnrollingPlanDao.selectUniversityAverageScore(map);
    }
    @Override
    public Integer selectUniversityPlanEnrollingNumber(Map map) {
        return iUniversityMajorEnrollingPlanDao.selectUniversityPlanEnrollingNumber(map);
    }
}
