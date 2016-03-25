package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface IUniversityInfoService extends IBaseUniversityInfoService {

    /**
     * 获取招生计划数
     *
     * @param map
     * @return
     */
    public Integer selectPlanEnrolling(Map map);

    public List<UniversityInfoView> selectUniversityInfoViewByVersion(Integer version,Integer score,Integer categorie,String province,Integer batch,Integer precedence,Integer first);
}
