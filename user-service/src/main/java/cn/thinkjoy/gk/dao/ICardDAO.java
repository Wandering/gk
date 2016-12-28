/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  CardDAO.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Card;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ICardDAO extends IBaseDAO<Card>{

    Card queryCardInfo(Map<String, String> params);


    List<Long> getCard(Long userId);

    List<Map<String,Object>> getProductService(@Param("productId") Integer productId,@Param("areaId") Long areaId);

    boolean initUserExpertService(List<Map<String,Object>> list);

    List<Integer> getServiceByExpertId(Integer expertId);

    List<Integer> getServiceByUserId(Long userId);

    Integer getServiceByUserIdAndExpertId(@Param("userId") Long userId,@Param("expertId")Integer expertId);

    Integer getProductServiceCount(@Param("productId")Integer productId, @Param("areaId")Long areaId);
}
