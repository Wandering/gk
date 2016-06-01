/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: market
 * $Id:  OrderStatementsDAO.java 2016-03-26 13:36:18 $
 */
package cn.thinkjoy.gk.dao;


import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.OrderStatements;

public interface IOrderStatementsDAO extends IBaseDAO<OrderStatements> {


    void updateByOrderNo(OrderStatements orderStatements);
}
