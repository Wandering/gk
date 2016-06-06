package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IRankingRoleParmasDao;
import cn.thinkjoy.gk.entity.RankingRoleParmas;
import cn.thinkjoy.gk.service.IRankingRoleParmasService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/25.
 */
@Service
public class RankingRoleParmasServiceImpl implements IRankingRoleParmasService {

    @Resource
    IRankingRoleParmasDao iRankingRoleParmasDao;


    @Override
    public RankingRoleParmas selectModel(Map map) {
        return iRankingRoleParmasDao.selectModel(map);
    }

    @Override
    public List<RankingRoleParmas> selectRankingRuleParmasList(Map map) {
        return iRankingRoleParmasDao.selectRankingRuleParmasList(map);
    }


    @Override
    public List<RankingRoleParmas> selectLineDiffRuleParmasList(Map map) {
        return iRankingRoleParmasDao.selectLineDiffRuleParmasList(map);
    }


}
