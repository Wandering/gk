package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/23.
 */
public interface IUniversityInfoRankingService {
    /**
     * 院校清单List
     *
     * @param map
     * @return
     */
    List<UniversityInfoView> selectUniversityInfo(Map map);
}
