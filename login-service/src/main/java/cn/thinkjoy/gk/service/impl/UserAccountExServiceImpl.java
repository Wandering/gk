/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.dao.*;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.domain.UserExam;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserInfoPojo;
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
    private IUserExamDAO userExamDAO;

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
    public UserAccountPojo findUserAccountPojoByPhone(String account,long areaId) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        params.put("areaId",areaId);
        return  userAccountExDAO.findUserAccountPojo(params);

    }

    @Override
    public int findUserAccountCountByPhone(String account,long areaId) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        params.put("areaId",areaId);
        return userAccountExDAO.findUserAccountCount(params);
    }

    @Override
    public boolean insertUserAccount(UserAccount userAccount) {
        boolean flag;
        userAccountDAO.insert(userAccount);
        long id = userAccount.getId();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        String account = userAccount.getAccount();
        userInfo.setName("gk-" + account.substring(0,3)+"****"+account.substring(account.length()-4,account.length()));
        userInfo.setToken(UUID.randomUUID().toString());
        userInfo.setProvinceId(userAccount.getProvinceId());
        userInfo.setCityId(userAccount.getCityId());
        userInfo.setCountyId(userAccount.getCountyId());
        userInfoExDAO.insertUserInfo(userInfo);
        UserVip userVip = new UserVip();
        userVip.setId(id);
        userVip.setStatus(0);
        userVip.setCreateDate(System.currentTimeMillis());
        userVipDAO.insert(userVip);
        UserExam userExam = new UserExam();
        userExam.setId(id);
        userExam.setIsReported(0);
        userExam.setIsSurvey(0);
        userExamDAO.insert(userExam);
        flag = true;

        return flag;
    }

    @Override
    public boolean updateUserAccount(UserAccount userAccount){
        boolean flag = false;
        userAccountDAO.update(userAccount);
        flag = true;
        return flag;
    }

    @Override
    public UserAccount findUserAccountById(long id){
        return userAccountDAO.fetch(id);
    }

    @Override
    public UserInfoPojo getUserInfoPojoById(long id){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userAccountExDAO.getUserInfoPojoById(params);
    }

    @Override
    public UserInfoPojo findOldUserAccountPojoById(long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userAccountExDAO.findOldUserAccountPojo(params);
    }

    @Override
    public UserInfoPojo findOldUserAccountPojoByPhone(String phone) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",phone);
        return userAccountExDAO.findOldUserAccountPojo(params);
    }

}
