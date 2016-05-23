package cn.thinkjoy.gk.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.gk.domain.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {

    List<Map<String,Object>> queryOrderListByUserId(long userId, int pageNo, int pageSize);

    Map<String,Object> queryOrderByNo(String orderNo);

    void updateByOrderNo(Order order);
}
