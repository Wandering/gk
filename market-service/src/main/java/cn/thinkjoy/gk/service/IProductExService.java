/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: pay
 * $Id:  ProductService.java 2015-05-11 14:45:04 $
 */

package cn.thinkjoy.gk.service;





import cn.thinkjoy.gk.domain.Product;

import java.util.List;

public interface IProductExService {

    List<Product> findProductList(Integer type, Integer startSize, Integer endSize);

    List<Product> findProductList(Integer type);

    List<Product> findProductList(List<Long> codes);

}
