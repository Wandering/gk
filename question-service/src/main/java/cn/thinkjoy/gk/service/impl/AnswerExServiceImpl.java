/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: message
 * $Id:  ExpertAnswerServiceImpl.java 2015-01-06 13:40:49 $
 */
package cn.thinkjoy.gk.service.impl;


import cn.thinkjoy.gk.dao.IAnswerExDAO;
import cn.thinkjoy.gk.pojo.AnswerPojo;
import cn.thinkjoy.gk.service.IAnswerExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("AnswerExServiceImpl")
public class AnswerExServiceImpl implements IAnswerExService {

    @Autowired
    private IAnswerExDAO answerExDAO;

    @Override
    public List<AnswerPojo> findAnswerPage(Long userId,Integer startSize, Integer endSize) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId",userId);
        int pageSize = endSize - startSize;
        params.put("startSize",startSize);
        params.put("endSize",pageSize);

        return answerExDAO.findAnswerPage(params);
    }

    @Override
    public Integer findAnswerCount(Long userId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId",userId);
        return answerExDAO.findAnswerCount(params);
    }
}
