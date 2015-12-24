/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserInfoDAO.java 2015-09-21 16:58:04 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.UserInfo;

import java.util.Map;

public interface IUserInfoDAO extends IBaseDAO<UserInfo>{
	
    void insertTelSchoolInfo(Map<String, String> paramMap);
}
