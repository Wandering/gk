package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityDAO;
import cn.thinkjoy.gk.pojo.EnrollInfo;
import cn.thinkjoy.gk.pojo.PlanInfo;
import cn.thinkjoy.gk.pojo.UniversityDto;
import cn.thinkjoy.gk.service.IExUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UniversityDto getUniversityDetail(String schoolCode) {
        return iUniversityDAO.getUniversityDetail(schoolCode);
    }

    @Override
    public List<EnrollInfo> getEnrollInfoByYear(int i,String schoolCode) {


        return iUniversityDAO.getEnrollInfoByYear(i,schoolCode);
    }

    @Override
    public String getUniversityEnrollIntro(String schoolCode) {
        return iUniversityDAO.getUniversityEnrollIntro(schoolCode);
    }

    @Override
    public List<PlanInfo> getPlanInfosByYear(int i,String schoolCode) {
        return iUniversityDAO.getPlanInfosByYear(i,schoolCode);
    }

    @Override
    public String getUniversityIntro(String schoolCode) {
        return iUniversityDAO.getUniversityIntro(schoolCode);
    }
}
