/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountService.java 2015-07-13 09:45:17 $
 */

package cn.thinkjoy.gk.service;


import cn.thinkjoy.gk.domain.Department;
import cn.thinkjoy.gk.domain.UserAccount;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserInfoPojo;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface IUserAccountExService {

    UserAccountPojo findUserAccountPojoByToken(String token);

    UserAccountPojo findUserAccountPojoById(Long id);

    UserAccountPojo findUserAccountPojoByPhone(String phone);

    int findUserAccountCountByPhone(String phone, Long areaId);

    boolean insertUserAccount(UserAccount userAccount, Long sharerId, Integer sharerType) throws WriterException, IOException;

    boolean insertUserMarketInfo(Long sharerId, Integer sharerType, long id) throws WriterException, IOException;

    boolean updateUserAccount(UserAccount userAccount);

    UserAccount findUserAccountById(long id);

    UserInfoPojo getUserInfoPojoById(long id);

    Map<String,Object> findUserInfo(Map<String, String> paramMap);

    Department findDepartMent(Map<String, String> paramMap);

    List<Map<String,Object>> getOrderList(Map<String, String> paramMap);

    int updateUserAccountRegistXueTang(Map<String, Object> paramMap);

    Map<String,Object> findUserInfoByAlipayId(String aliUserId);

    boolean bindUserAccount(UserAccount userAccount);

    boolean bindUserAccountExist(UserAccountPojo userAccountPojo, String userId, String aliUserId);

    void updateUserQQUserId(UserAccountPojo userAccountPojo, String userId, String aliUserId);
}
