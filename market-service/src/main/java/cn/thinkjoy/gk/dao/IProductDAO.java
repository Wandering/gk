/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  ProductDAO.java 2015-09-07 10:14:40 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.ss.domain.Product;

public interface IProductDAO extends IBaseDAO<Product>{
	
	Product findByCode(Long code);

}
