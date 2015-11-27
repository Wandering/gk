/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityService.java 2015-09-26 11:19:41 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.UniversityDetailDto;

import java.util.List;

public interface IUniversityExService{

    UniversityDetailDto getUniversityDetail(String schoolCode,String batch,Integer type,Integer year,long areaId);

    List<UniversityDetailDto> getUniversityDetailByCodes(List<String> schoolCodes,String batch,Integer type,Integer year,long areaId);

    List<Integer> getRecentlyPlanInfosByYear(String universityId,String batch,long areaId);

    List<Integer> getRecentlyEnrollInfoByYear(String universityId,long areaId);
}
