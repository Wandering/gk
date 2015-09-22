/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountService.java 2015-07-13 09:45:17 $
 */

package cn.thinkjoy.gk.service;


import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.domain.UserAccount;


public interface IUserAccountExService {

    UserAccountPojo findUserAccountBeanByToken(String token);

    UserAccountPojo findUserAccountBeanById(Long id);

    UserAccountPojo findUserAccountBeanByPhone(String phone);

    int findUserAccountCountByPhone(String phone);

    boolean insertUserAccount(UserAccount userAccount);

}
