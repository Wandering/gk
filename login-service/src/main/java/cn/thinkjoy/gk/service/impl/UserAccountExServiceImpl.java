/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.*;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IUserInfoExDAO userInfoExDAO;

    @Autowired
    private IUserVipDAO userVipDAO;

    @Override
    public UserAccountPojo findUserAccountPojoByToken(String token) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("token",token);
        return userAccountExDAO.findUserAccountPojo(params);
    }

    @Override
    public UserAccountPojo findUserAccountPojoById(Long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userAccountExDAO.findUserAccountPojo(params);
    }

    @Override
    public UserAccountPojo findUserAccountPojoByPhone(String account) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        return  userAccountExDAO.findUserAccountPojo(params);

    }

    @Override
    public int findUserAccountCountByPhone(String account) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        return userAccountExDAO.findUserAccountCount(params);
    }

    @Override
    public boolean insertUserAccount(UserAccount userAccount) {
        boolean flag = false;
        try{
            userAccountDAO.insert(userAccount);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userAccount.getId());
            String account = userAccount.getAccount();
            userInfo.setName("gk-" + account.substring(0,3)+"****"+account.substring(account.length()-4,account.length()));
            userInfo.setToken(UUID.randomUUID().toString());
            userInfoExDAO.insertUserInfo(userInfo);
            UserVip userVip = new UserVip();
            userVip.setId(userAccount.getId());
            userVip.setStatus(0);
            userVip.setCreateDate(System.currentTimeMillis());
            userVip.setEndDate(System.currentTimeMillis());
            userVipDAO.insert(userVip);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean updateUserAccount(UserAccount userAccount){
        boolean flag = false;
        try {
            userAccountDAO.update(userAccount);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public UserAccount findUserAccountById(long id){
        return userAccountDAO.fetch(id);
    }


}
