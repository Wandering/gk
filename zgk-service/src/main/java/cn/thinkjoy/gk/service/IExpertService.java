package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExpertOrder;

/**
 * Created by liusven on 2016/10/19.
 */
public interface IExpertService
{
    void insertOrder(ExpertOrder order);

    ExpertOrder findOrderByOrderNo(String orderNo);

    void updateOrder(ExpertOrder order);
}
