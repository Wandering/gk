/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: pay
 * $Id:  ProductDAO.java 2015-05-11 14:45:04 $
 */
package cn.thinkjoy.gk.dao;



import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Product;

import java.util.List;
import java.util.Map;

public interface IProductExDAO extends IBaseDAO<Product> {
	
    List<Product> findPage(Map<String, Object> params);

    List<Product> selectProductByServiceIdAndAreaId(Map<String,Object> params);
}
