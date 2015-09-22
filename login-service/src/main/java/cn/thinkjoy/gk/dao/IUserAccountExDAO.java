/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountDAO.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.dao;


import cn.thinkjoy.gk.bean.UserAccountBean;

import java.util.Map;

public interface IUserAccountExDAO {

    UserAccountBean findUserAccountBean(Map<String, Object> params);

    int findUserAccountCount(Map<String, Object> params);

}
