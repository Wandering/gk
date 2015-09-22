/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountService.java 2015-07-13 09:45:17 $
 */

package cn.thinkjoy.gk.service;


import cn.thinkjoy.gk.bean.UserAccountBean;
import cn.thinkjoy.gk.domain.UserAccount;


public interface IUserAccountExService {

    UserAccountBean findUserAccountBeanByToken(String token);

    UserAccountBean findUserAccountBeanById(Long id);

    UserAccountBean findUserAccountBeanByPhone(String phone);

    int findUserAccountCountByPhone(String phone);

    boolean insertUserAccount(UserAccount userAccount);

}
