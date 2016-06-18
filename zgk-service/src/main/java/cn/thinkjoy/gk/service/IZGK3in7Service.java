/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastService.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

import java.util.List;
import java.util.Map;

public interface IZGK3in7Service{
    List<Map<String,Object>> getUnversityByArea(Map<String, Object> map);
    List<Map<String,Object>> getMajorByUnversityId(Map<String, Object> map);
    Map<String,Object> getSubjectByMajor(Map<String, Object> map);
    List<Map<String,Object>> queryPage(Map<String, Object> map);
    int count(Map<String, Object> map);
}