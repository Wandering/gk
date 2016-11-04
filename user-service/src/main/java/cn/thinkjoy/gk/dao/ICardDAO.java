/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  CardDAO.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Card;

import java.util.List;
import java.util.Map;

public interface ICardDAO extends IBaseDAO<Card>{

    Card queryCardInfo(Map<String, String> params);


    List<Long> getCard(Long userId);

    List<Map<String,Object>> getProductService(Integer productId,Long areaId);

    boolean initUserExpertService(List<Map<String,Object>> list);
}
