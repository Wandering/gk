package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.RankingRoleParmas;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/25.
 */
public interface IRankingRoleParmasDao extends IBaseDAO<RankingRoleParmas> {
    /**
     * 获取排位法系统参数
     * @param map
     * @return
     */
    public RankingRoleParmas selectModel(Map map);

    /**
     * 获取排位法系统参数
     * @param map
     * @return
     */
    public List<RankingRoleParmas> selectRankingRuleParmasList(Map map);


    /**
     * 获取线差法系统参数
     * @param map
     * @return
     */
    public List<RankingRoleParmas> selectLineDiffRuleParmasList(Map map);
}
