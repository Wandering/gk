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
import java.util.Map;

public interface ICardExDAO extends IBaseDAO<Orders>{
    /**
     * 批量生成卡片
     *
     * @param cards
     */
    void batchCreateCard(@Param("cards") List<Card> cards);

    long singleCreateCard(Card card);

    Map<String,Object> getCardByUidAndNo(@Param("userId")Long id, @Param("orderNo")String orderNo);
}
