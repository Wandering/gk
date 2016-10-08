/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityDAO.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.University;
import cn.thinkjoy.gk.pojo.MajoredScoreLinePojo;
import cn.thinkjoy.gk.pojo.OpenMajoredPojo;
import cn.thinkjoy.gk.pojo.SpecialMajorDto;
import cn.thinkjoy.gk.pojo.UniversityDetailDto;
import cn.thinkjoy.zgk.dto.UniversityMajorEnrollingPlanDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface IUniversityExDAO{

    UniversityDetailDto getWSUniversityDetail(Map<String,Object> params);

    UniversityDetailDto getLGUniversityDetail(Map<String,Object> params);

    List<UniversityDetailDto> getWSUniversityDetailByCodes(Map<String,Object> params);

    List<UniversityDetailDto> getLGUniversityDetailByCodes(Map<String,Object> params);

    List<MajoredScoreLinePojo> getMajoredScoreLinePojoList(Map<String,Object> params);

    List<String> getMajoredScoreLineYears(Map<String,Object> params);

    List<OpenMajoredPojo> getOpenMajoredPojoList(Map<String,Object> params);

    List<Integer> getRecentlyPlanInfosByYear(Map<String,Object> params);

    List<Integer> getRecentlyEnrollInfoByYear(Map<String,Object> params);

    void saveMajoredScoreLine(Map<String,Object> map);

    /**
     * 根据关键词查询学校基本信息
     *
     * @param keywords
     * @return
     */
    List<University> getUniversityInfoByKeywords(@Param("keywords") String keywords);

    List<Map<String,Object>> queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                                       @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector")Map<String, Object> selector);

    int count(Map<String, Object> condition);

    List<UniversityMajorEnrollingPlanDTO> getUniversityMajorEnrollingPlanList(Map<String,Object> params);

    /**
     * 根据省份ID查询录取年份集合
     *
     * @param proviceId
     * @return
     */
    List<String> getYearsByProviceId(@Param("proviceId") long proviceId);

    /**
     * 根据省份ID查询录取批次集合
     *
     * @param proviceId
     * @return
     */
    List<String> getBatchsByProviceId(@Param("proviceId") long proviceId);

    /**
     * 根据条件查询院校招生信息
     *
     * @param schoolName
     * @param year
     * @param batch
     * @param majorType
     * @param userProviceId
     * @param schoolProviceId
     * @param index
     * @param pageSize
     * @return
     */
    List<SpecialMajorDto> searchSpecialMajorInfo(
            @Param("schoolName") String schoolName,
            @Param("year") String year,
            @Param("batch") String batch,
            @Param("majorType") Integer majorType,
            @Param("userProviceId") Long userProviceId,
            @Param("schoolProviceId") Long schoolProviceId,
            @Param("index") Integer index,
            @Param("pageSize") Integer pageSize
    );
}
