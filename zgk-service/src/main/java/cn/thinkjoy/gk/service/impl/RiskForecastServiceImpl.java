package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IRiskForecastDAO;
import cn.thinkjoy.gk.service.IRiskForecastService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by douzy on 16/5/20.
 */
@Service
public class RiskForecastServiceImpl implements IRiskForecastService {

    @Resource
    IRiskForecastDAO iRiskForecastDAO;

    @Override
    public Integer selectRiskRankByUniversityId(Map map) {
        return iRiskForecastDAO.selectRiskRankByUniversityId(map);
    }
}
