package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityDAO;
import cn.thinkjoy.gk.pojo.EnrollInfo;
import cn.thinkjoy.gk.pojo.PlanInfo;
import cn.thinkjoy.gk.pojo.UniversityDto;
import cn.thinkjoy.gk.service.IExUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkjoy on 15/9/26.
 */
@Service("ExUniversityServiceImpl")
public class ExUniversityServiceImpl implements IExUniversityService {
    @Autowired
    private IUniversityDAO iUniversityDAO;

    @Override
    public List<Map> getProvinces() {
        return null;
    }

    @Override
    public List<Map> getUniversityType() {
        return null;
    }

    @Override
    public List<Map> getUniversiyBatch() {
        return null;
    }

    @Override
    public List<Map> getuniversityFeature() {
        return null;
    }

    @Override
    public List<UniversityDto> getUniversityList(Map<String, Object> universityQuery) {
        return iUniversityDAO.getUniversityList(universityQuery);
    }

    @Override
    public Integer getUniversityCount(Map<String, Object> universityQuery) {
        return iUniversityDAO.getUniversityCount(universityQuery);
    }


    @Override
    public UniversityDto getUniversityDetail(String schoolId,String batch) {
        return iUniversityDAO.getUniversityDetail(schoolId,batch);
    }

    @Override
    public List<EnrollInfo> getEnrollInfoByYear(Integer year,String universityId) {
        Map<String, Object> params = new HashMap<>();

        params.put("year",year);
        params.put("universityId",universityId);

        return iUniversityDAO.getEnrollInfoByYear(params);
    }

    @Override
    public String getUniversityEnrollIntro(String id) {
        return iUniversityDAO.getUniversityEnrollIntro(id);
    }

    @Override
    public List<PlanInfo> getPlanInfosByYear(Integer year,String universityId,String batch) {

        Map<String, Object> params = new HashMap<>();

        params.put("year",year);
        params.put("universityId",universityId);
        params.put("batch",batch);

        return iUniversityDAO.getPlanInfosByYear(params);
    }

    @Override
    public String getUniversityIntro(String schoolId) {
        return iUniversityDAO.getUniversityIntro(schoolId);
    }
}
