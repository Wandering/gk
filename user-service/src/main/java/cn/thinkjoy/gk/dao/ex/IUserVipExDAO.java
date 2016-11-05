/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UserVipDAO.java 2015-09-23 10:34:37 $
 */
package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.UserVip;

import java.util.List;
import java.util.Map;

public interface IUserVipExDAO extends IBaseDAO<UserVip>{

    List<Map<String, String>> findVipInfoListByArea(Map<String, String> paramMap);

    /**
     * 获取用户注册总数
     *
     * @return
     */
    Integer getRegisteUserCount();

    Integer countUserServiceByUserId(Long userId);
}
