package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.query.ExpertOrder;

/**
 * Created by liusven on 2016/10/19.
 */
public interface IExpertDAO
{
    void insertOrder(ExpertOrder order);

    ExpertOrder findOrderByOrderNo(String orderNo);

    void updateOrder(ExpertOrder order);
}
