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

public interface IUserFavorites3in7DAO extends IBaseDAO<Forecast>{

    List<Map<String,Object>> getFavoritesByMajor(Map<String,Object> map);
    List<Map<String,Object>> getFavoritesBySubjectKey(Map<String,Object> map);
    List<Map<String,Object>> getFavoritesBySubject(Map<String,Object> map);
    boolean insertFavorites(Map<String,Object> map);
    boolean deleteById(Map<String,Object> map);
    boolean deleteBySubjects(Map<String,Object> map);

}
