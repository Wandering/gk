package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dto.EnrollInfo;
import cn.thinkjoy.gk.dto.PlanInfo;
import cn.thinkjoy.gk.dto.UniversityDto;
import cn.thinkjoy.gk.query.UniversityQuery;
import cn.thinkjoy.gk.service.IExUniversityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by thinkjoy on 15/9/26.
 */
@Service("ExUniversityServiceImpl")
public class ExUniversityServiceImpl implements IExUniversityService {
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
    public List<UniversityDto> getUniversityList(UniversityQuery universityQuery) {
        return null;
    }

    @Override
    public Integer getUniversityCount(UniversityQuery universityQuery) {
        return null;
    }

    @Override
    public UniversityDto getUniversityDetail(String schoolCode) {
        return null;
    }

    @Override
    public List<EnrollInfo> getEnrollInfoByYear(int i) {
        return null;
    }

    @Override
    public String getUniversityEnrollIntro(String schoolCode) {
        return null;
    }

    @Override
    public List<PlanInfo> getPlanInfosByYear(int i) {
        return null;
    }
}
