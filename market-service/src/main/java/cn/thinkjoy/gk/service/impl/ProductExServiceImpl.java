/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: pay
 * $Id:  ProductServiceImpl.java 2015-05-11 14:45:04 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IProductExDAO;
import cn.thinkjoy.gk.domain.Product;
import cn.thinkjoy.gk.service.IProductExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductExServiceImpl implements IProductExService {

    @Autowired
    private IProductExDAO productExDAO;

    @Override
    public List<Product> findProductList(Integer type,Integer startSize,Integer endSize) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",type);
        int pageSize = endSize - startSize;
        params.put("startSize",startSize);
        params.put("endSize",pageSize);
        return productExDAO.findPage(params);
    }

    @Override
    public List<Product> findProductList(Integer type) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",type);
        return productExDAO.findPage(params);
    }

    @Override
    public List<Product> findProductList(List<Long> codes) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("codes",codes);
        return productExDAO.findPage(params);
    }

}
