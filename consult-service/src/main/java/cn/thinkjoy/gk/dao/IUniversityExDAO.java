/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityDAO.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.MajoredScoreLinePojo;
import cn.thinkjoy.gk.pojo.UniversityDetailDto;
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
}
