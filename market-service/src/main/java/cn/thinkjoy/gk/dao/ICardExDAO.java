/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  OrdersDAO.java 2015-09-07 10:14:40 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICardExDAO extends IBaseDAO<Orders>{
    /**
     * 批量生成卡片
     *
     * @param cards
     */
    void batchCreateCard(@Param("cards") List<Card> cards);

    int singleCreateCard(@Param("card") Card card);
}
