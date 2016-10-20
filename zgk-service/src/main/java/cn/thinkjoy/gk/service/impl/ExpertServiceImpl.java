package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.query.ExpertOrder;
import cn.thinkjoy.gk.dao.IExpertDAO;
import cn.thinkjoy.gk.service.IExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liusven on 2016/10/19.
 */
@Service("ExpertServiceImpl")
public class ExpertServiceImpl implements IExpertService
{
    @Autowired
    private IExpertDAO dao;

    @Override
    public void insertOrder(ExpertOrder order)
    {
        dao.insertOrder(order);
    }

    @Override
    public ExpertOrder findOrderByOrderNo(String orderNo, String orderNo1)
    {
        return null;
    }

}
