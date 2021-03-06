/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUserInfoDAO;
import cn.thinkjoy.gk.dao.IUserInfoExDAO;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.service.IUserInfoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserInfoExServiceImpl")
public class UserInfoExServiceImpl implements IUserInfoExService {

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Autowired
    private IUserInfoExDAO userInfoExDAO;

    @Override
    public UserInfo findUserInfoById(long id) {
        return userInfoExDAO.getUserInfoById(id);
    }

    @Override
    public boolean updateUserInfoById(UserInfo userInfo) {
        boolean flag;
        userInfoDAO.update(userInfo);
        flag = true;
        return flag;
    }

    @Override
    public void updateUserCanTargetByUid(long uid) {
        userInfoExDAO.updateUserCanTargetByUid(uid);
    }

    @Override
    public boolean isPredictByUid(long uid) {
        return userInfoExDAO.isPredictByUid(uid);
    }
}
