package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.UniversityInfoView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface IUniversityInfoDao extends IBaseDAO<UniversityInfoView> {
    /**
     * 院校清单List    --- 有位次 按位次   没有位次  按分数
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfo(Map map);
    /**
     * 院校清单List    --- 按院校排名   规则见DB zgk_system_parmas
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByRanking(Map map);
    /**
     * 获取计划招生数
     * @param map
     * @return
     */
    Integer selectPlanEnrolling(Map map);


}
