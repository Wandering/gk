package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityMajorEnrollingPlanDao;
import cn.thinkjoy.gk.entity.UniversityMajorEnrollingPlan;
import cn.thinkjoy.gk.pojo.SpecialtyView;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
@Service
public class IUniversityMajorEnrollingServiceImpl implements IUniversityMajorEnrollingService {
    @Resource
    IUniversityMajorEnrollingPlanDao iUniversityMajorEnrollingPlanDao;
    @Override
    public List<SpecialtyView> selectList(Map map) {
        List<UniversityMajorEnrollingPlan> universityMajorEnrollingPlans = iUniversityMajorEnrollingPlanDao.selectList(map);
        List<SpecialtyView> specialtyViews=new ArrayList<>();
        for(UniversityMajorEnrollingPlan universityMajorEnrollingPlan : universityMajorEnrollingPlans) {
            SpecialtyView specialtyView = new SpecialtyView();
            specialtyView.setUniversityId(universityMajorEnrollingPlan.getUniversityId());
            specialtyView.setUniversityName(universityMajorEnrollingPlan.getUniversityName());
            specialtyView.setSpecialtyName(universityMajorEnrollingPlan.getMajorName());
            specialtyViews.add(specialtyView);
        }
        return specialtyViews;
    }
}
