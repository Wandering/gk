/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastService.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.service;

import java.util.List;
import java.util.Map;

public interface IUserFavorites3in7Service {
    List<Map<String,Object>> getFavoritesByMajor(Map<String,Object> map);
    List<Map<String,Object>> getFavoritesBySubjectKey(Map<String,Object> map);
    List<Map<String,Object>> getFavoritesBySubject(Map<String,Object> map);
    int count(Map<String,Object> map);
    boolean insertFavorites(Map<String,Object> map);
    boolean deleteById(Map<String,Object> map);
    boolean deleteBySubjects(Map<String,Object> map);
}