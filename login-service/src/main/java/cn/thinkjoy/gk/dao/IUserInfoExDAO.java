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

    long insertUserInfo(UserInfo userInfo);

    void updateUserCanTargetByUid(long uid);

    boolean isPredictByUid(long uid);

    UserInfo getUserInfoById(long id);

    void updateUserAliUserId(UserInfo userInfo);

    void updateUserQQUserId(UserInfo userInfo);
}
