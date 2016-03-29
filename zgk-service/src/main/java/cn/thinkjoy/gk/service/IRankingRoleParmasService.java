package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.RankingRoleParmas;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/25.
 */
public interface IRankingRoleParmasService {
    /**
     * 获取排位法动态参数 对象
     * @param map
     * @return
     */
    public RankingRoleParmas selectModel(Map map);
    /**
     * 获取排位法动态参数 对象
     * @param map
     * @return
     */
    public List<RankingRoleParmas> selectRankingRuleParmasList(Map map);
}
