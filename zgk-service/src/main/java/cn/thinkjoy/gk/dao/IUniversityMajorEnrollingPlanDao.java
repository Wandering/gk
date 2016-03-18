package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.UniversityMajorEnrollingPlan;
import cn.thinkjoy.gk.pojo.UniversityEnrollingView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
public interface IUniversityMajorEnrollingPlanDao extends IBaseDAO<UniversityMajorEnrollingPlan> {

    public List<UniversityMajorEnrollingPlan> selectList(Map map);

    Integer lowestScoreAvg(Map map);

    UniversityEnrollingView selectUniversityAverageScore(Map map);

}
