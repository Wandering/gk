/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.bean.UserAccountBean;
import cn.thinkjoy.gk.dao.IUserAccountDAO;
import cn.thinkjoy.gk.dao.IUserAccountExDAO;
import cn.thinkjoy.gk.dao.IUserInfoDAO;
import cn.thinkjoy.gk.dao.IUserVipDAO;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.service.IUserAccountExService;
import com.jlusoft.microschool.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service("UserAccountExServiceImpl")
public class UserAccountExServiceImpl implements IUserAccountExService {

    @Autowired
    private IUserAccountExDAO userAccountExDAO;

    @Autowired
    private IUserAccountDAO userAccountDAO;

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Autowired
    private IUserVipDAO userVipDAO;

    @Override
    public UserAccountBean findUserAccountBeanByToken(String token) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("token",token);
        return userAccountExDAO.findUserAccountBean(params);
    }

    @Override
    public UserAccountBean findUserAccountBeanById(Long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userAccountExDAO.findUserAccountBean(params);
    }

    @Override
    public UserAccountBean findUserAccountBeanByPhone(String phone) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",phone);
        return userAccountExDAO.findUserAccountBean(params);
    }

    @Override
    public int findUserAccountCountByPhone(String phone) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",phone);
        return userAccountExDAO.findUserAccountCount(params);
    }

    @Override
    public boolean insertUserAccount(UserAccount userAccount) {
        boolean flag = false;
        try{
            userAccountDAO.insert(userAccount);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userAccount.getId());
            userInfo.setCountyId(Long.valueOf(0));
            userInfo.setToken(UUID.randomUUID().toString());
            userInfoDAO.insert(userInfo);
            UserVip userVip = new UserVip();
            userVip.setId(userAccount.getId());
            userVip.setStatus(0);
            userVipDAO.insert(userVip);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
