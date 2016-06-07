/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityService.java 2015-09-26 11:19:41 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.UniversityDetailDto;

import java.util.List;
import java.util.Map;

public interface IUniversityExService{

    UniversityDetailDto getUniversityDetail(String schoolCode,String batch,Integer type,Integer year,long areaId);

    List<UniversityDetailDto> getUniversityDetailByCodes(List<String> schoolCodes,String batch,Integer type,Integer year,long areaId);

    List<Integer> getRecentlyPlanInfosByYear(String universityId,String batch,long areaId);

    List<Integer> getRecentlyEnrollInfoByYear(String universityId,long areaId);

    void saveMajoredScoreLine(Map<String,Object> map);

    /**
     * 根据关键词查询学校基本信息
     *
     * @param keywords
     * @return
     */
    Map<String,String> getUniversityInfoByKeywords(String keywords);

    List getUniversityList(Map<String, Object> condition, int offset, int rows, String orderBy, String sqlOrderEnum, Map<String, Object> selectorpage);

    int getUniversityCount(Map<String, Object> condition);

    List getUniversityMajorEnrollingPlanList(Map<String, Object> condition);
}
