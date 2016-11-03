/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertProductServiceDAO.java 2016-11-03 11:33:42 $
 */
package cn.thinkjoy.gk.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IExpertProductServiceExDAO{


    /**
     * 根据productId和地区获取当前省份的卡套餐详情
     * @param map
     * @return
     */
    List<Map<String,Object>> getCardServiceByProductId(Map<String,Object> map);
}
