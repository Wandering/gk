package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.SpecialtyView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/15.
 */
public interface IUniversityMajorEnrollingService {
    List<SpecialtyView> selectList(Map map);
    Integer lowestScoreAvg(Map map);
}
