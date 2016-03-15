package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.UniversityMajoyEnrollingPlan;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
public interface IUniversityMajoyEnrollingPlanDao extends IBaseDAO<UniversityMajoyEnrollingPlan> {

    public List<UniversityMajoyEnrollingPlan> selectList(Map map);
}
