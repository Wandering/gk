package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityMajoyEnrollingPlanDao;
import cn.thinkjoy.gk.entity.UniversityMajoyEnrollingPlan;
import cn.thinkjoy.gk.pojo.SpecialtyView;
import cn.thinkjoy.gk.service.IUniversityMajoyEnrollingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
@Service
public class UniversityMajoyEnrollingServiceImpl implements IUniversityMajoyEnrollingService {
    @Resource
    IUniversityMajoyEnrollingPlanDao iUniversityMajoyEnrollingPlanDao;
    @Override
    public List<SpecialtyView> selectList(Map map) {
        List<UniversityMajoyEnrollingPlan> universityMajoyEnrollingPlans=iUniversityMajoyEnrollingPlanDao.selectList(map);
        List<SpecialtyView> specialtyViews=new ArrayList<>();
        for(UniversityMajoyEnrollingPlan universityMajoyEnrollingPlan:universityMajoyEnrollingPlans) {
            SpecialtyView specialtyView = new SpecialtyView();
            specialtyView.setUniversityId(universityMajoyEnrollingPlan.getUniversityId());
            specialtyView.setUniversityName(universityMajoyEnrollingPlan.getUniversityName());
            specialtyView.setSpecialtyName(universityMajoyEnrollingPlan.getMajorName());
            specialtyViews.add(specialtyView);
        }
        return specialtyViews;
    }
}
