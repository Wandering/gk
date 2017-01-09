/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  UniversityEnrollingDAO.java 2015-12-28 09:55:36 $
 */
package cn.thinkjoy.gk.dao.information.ex;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.pojo.UniversityEnrollingDTO;

import java.util.List;

public interface IUniversityEnrollingExDAO extends IBaseDAO<UniversityEnrollingDTO>{

    public List<String> getYear();

}
