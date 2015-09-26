package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.dto.EnrollInfo;
import cn.thinkjoy.gk.dto.PlanInfo;
import cn.thinkjoy.gk.dto.UniversityDto;
import cn.thinkjoy.gk.query.UniversityQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/25.
 */
public interface IExUniversityService {

    List<Map> getProvinces();

    List<Map> getUniversityType();

    List<Map> getUniversiyBatch();

    List<Map> getuniversityFeature();

    List<UniversityDto> getUniversityList(UniversityQuery universityQuery);

    Integer getUniversityCount(UniversityQuery universityQuery);

    UniversityDto getUniversityDetail(String schoolCode);

    List<EnrollInfo> getEnrollInfoByYear(int i);

    String getUniversityEnrollIntro(String schoolCode);

    List<PlanInfo> getPlanInfosByYear(int i);
}
