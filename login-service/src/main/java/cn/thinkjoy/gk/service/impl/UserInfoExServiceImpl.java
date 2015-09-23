/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUserAccountDAO;
import cn.thinkjoy.gk.dao.IUserAccountExDAO;
import cn.thinkjoy.gk.dao.IUserInfoDAO;
import cn.thinkjoy.gk.dao.IUserVipDAO;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service("UserInfoExServiceImpl")
public class UserInfoExServiceImpl implements IUserInfoExService {

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Override
    public UserInfo findUserInfoById(long id) {
        return userInfoDAO.fetch(id);
    }

    @Override
    public boolean updateUserInfoById(UserInfo userInfo) {
        boolean flag = false;
        try {
            userInfoDAO.update(userInfo);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
