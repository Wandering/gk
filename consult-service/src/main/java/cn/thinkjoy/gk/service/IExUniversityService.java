package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.*;

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

    UniversityDto getUniversityDetail(Map<String, Object> params);

    List<EnrollInfo> getEnrollInfoByYear(Integer year,String schoolId,long areaId);

    String getUniversityEnrollIntro(String schoolId);

    List<PlanInfo> getPlanInfosByYear(Integer year,String schoolId,String batch,long areaId);

    String getUniversityIntro(String schoolId);

    Map<String,Object> getMajoredScoreLinePojoList(long universityId,long areaId);

    List<String> getMajoredScoreLineYears(long universityId,long areaId);

    List<OpenMajoredPojo> getOpenMajoredPojoList(long universityId,long areaId);
}
