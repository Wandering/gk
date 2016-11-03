/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: market
 * $Id:  OrderStatementsService.java 2016-03-26 13:36:18 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.gk.domain.OrderStatements;
import cn.thinkjoy.gk.pojo.ExpertReservationOrderDetailDTO;

import java.util.List;

public interface IOrderStatementsService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    void updateByOrderNo(OrderStatements orderStatements);
}
