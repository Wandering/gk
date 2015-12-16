/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  OrdersDAO.java 2015-09-07 10:14:40 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Orders;

import java.util.List;
import java.util.Map;

public interface IOrdersDAO extends IBaseDAO<Orders>{
	
    List<Map<String, String>> selectOrderDetail(Map<String, Object> paramMap);

    List<Map<String, String>> selectOrderStatisticsData(Map<String, Object> paramMap);
}
