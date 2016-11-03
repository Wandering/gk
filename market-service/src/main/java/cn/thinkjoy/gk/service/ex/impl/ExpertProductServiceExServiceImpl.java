/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertProductServiceServiceImpl.java 2016-11-03 11:33:42 $
 */
package cn.thinkjoy.gk.service.ex.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.gk.dao.IExpertProductServiceDAO;
import cn.thinkjoy.gk.dao.IExpertProductServiceExDAO;
import cn.thinkjoy.gk.domain.ExpertProductService;
import cn.thinkjoy.gk.service.IExpertProductServiceService;
import cn.thinkjoy.gk.service.ex.IExpertProductServiceExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ExpertProductServiceExServiceImpl")
public class ExpertProductServiceExServiceImpl implements IExpertProductServiceExService{
    @Autowired
    private IExpertProductServiceExDAO expertProductServiceExDAO;
    @Override
    public List<Map<String, Object>> getCardServiceByProductId(Map<String,Object> map) {
        return expertProductServiceExDAO.getCardServiceByProductId(map);
    }
}
