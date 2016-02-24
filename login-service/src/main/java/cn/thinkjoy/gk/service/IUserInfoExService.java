/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountService.java 2015-07-13 09:45:17 $
 */

package cn.thinkjoy.gk.service;


import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;

import java.util.Map;


public interface IUserInfoExService {

    UserInfo findUserInfoById(long id);

    boolean updateUserInfoById(UserInfo userInfo);

    void updateUserCanTargetByUid(long uid);

    boolean isPredictByUid(long uid);
}
