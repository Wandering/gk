package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.entity.RiskForecast;

import java.util.Map;

/**
 * Created by douzy on 16/5/11.
 */
public interface IRiskForecastDAO {
    Integer insert(RiskForecast riskForecast);

    Integer selectRiskRankByUniversityId(Map map);
}
