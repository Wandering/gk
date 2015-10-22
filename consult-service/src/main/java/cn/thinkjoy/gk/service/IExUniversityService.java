package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.EnrollInfo;
import cn.thinkjoy.gk.pojo.PlanInfo;
import cn.thinkjoy.gk.pojo.UniversityDto;

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

    List<UniversityDto> getUniversityList(Map<String,Object> universityQuery);

    Integer getUniversityCount(Map<String,Object> universityQuery);

    UniversityDto getUniversityDetail(String schoolId,String batch);

    List<EnrollInfo> getEnrollInfoByYear(Integer year,String schoolId);

    String getUniversityEnrollIntro(String schoolId);

    List<PlanInfo> getPlanInfosByYear(Integer year,String schoolId,String batch);

    String getUniversityIntro(String schoolId);
}
