/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityDAO.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.UniversityDetailDto;
import org.apache.ibatis.annotations.Param;


public interface IUniversityExDAO{


    UniversityDetailDto getWSUniversityDetail(@Param("code") String schoolCode);

    UniversityDetailDto getLGUniversityDetail(@Param("code") String schoolCode);
}
