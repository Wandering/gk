/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountDAO.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.dao;


import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;

import java.util.Map;

public interface IUserInfoExDAO {

    UserInfo findUserInfo(Map<String, Object> params);

    int findUserInfoCount(Map<String, Object> params);

}
