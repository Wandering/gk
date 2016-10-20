package cn.thinkjoy.gk.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

import java.util.List;
import java.util.Map;

public interface IOrdersService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{

    List<Map<String, String>> queryOrderDetail(Map<String, Object> paramMap);

    List<Map<String, String>> queryOrderStatisticsData(Map<String, Object> paramMap);


}
