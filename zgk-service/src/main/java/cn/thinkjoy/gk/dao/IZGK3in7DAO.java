/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastDAO.java 2016-01-22 11:48:22 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Forecast;

import java.util.List;
import java.util.Map;

public interface IZGK3in7DAO extends IBaseDAO<Forecast>{

    List<Map<String,Object>> getUnversityByArea(Map<String, Object> map);
    List<Map<String,Object>> getMajorByUnversityId(Map<String, Object> map);
    Map<String,Object> getSubjectByMajor(Object majorId);
    List<Map<String,Object>> queryPage(Map<String, Object> map);
    int count(Map<String, Object> map);
    boolean deleteById(Map<String, Object> map);
    boolean deleteBySubjects(Map<String, Object> map);

}
