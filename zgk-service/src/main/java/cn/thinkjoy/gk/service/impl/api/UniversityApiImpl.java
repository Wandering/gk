package cn.thinkjoy.gk.service.impl.api;

import cn.thinkjoy.gk.api.IUniversityApi;
import cn.thinkjoy.gk.dao.IUniversityInfoDao;
import cn.thinkjoy.zgk.remote.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/10/31.
 */
@Service("universityApi")
public class UniversityApiImpl implements IUniversityApi {

    @Autowired
    private IUniversityInfoDao iUniversityInfoDao;
    @Autowired
    private IUniversityService universityService;

    @Override
    public List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map) {
        return iUniversityInfoDao.getBatchByYearAndArea(map);
    }

    @Override
    public List getDataDictList(String type) {
        return universityService.getDataDictListByType(type);
    }

    @Override
    public List<String> getEnrollingYearsByProvinceId(long provinceId) {
        return iUniversityInfoDao.getEnrollingYearsByProvinceId(provinceId);
    }

    @Override
    public List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorPlanConditions(map);
    }
}
