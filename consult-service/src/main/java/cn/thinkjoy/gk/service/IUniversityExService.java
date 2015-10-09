/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityService.java 2015-09-26 11:19:41 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.UniversityDetailDto;

public interface IUniversityExService{

    UniversityDetailDto getUniversityDetail(String schoolCode,Integer type);

}
