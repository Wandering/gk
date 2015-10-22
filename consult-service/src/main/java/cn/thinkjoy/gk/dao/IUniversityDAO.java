/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityDAO.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.University;
import cn.thinkjoy.gk.pojo.EnrollInfo;
import cn.thinkjoy.gk.pojo.PlanInfo;
import cn.thinkjoy.gk.pojo.UniversityDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUniversityDAO extends IBaseDAO<University>{


    List<UniversityDto> getUniversityList(@Param("universityQuery")Map<String, Object> universityQuery);

    Integer getUniversityCount(@Param("universityQuery")Map<String, Object> universityQuery);

    UniversityDto getUniversityDetail(@Param("universityId")String id,@Param("batch")String batch);

    List<EnrollInfo> getEnrollInfoByYear(Map<String, Object> params);

    List<PlanInfo> getPlanInfosByYear(Map<String, Object> params);

    String getUniversityIntro(@Param("id")String id);

    String getUniversityEnrollIntro(@Param("id")String id);
}
