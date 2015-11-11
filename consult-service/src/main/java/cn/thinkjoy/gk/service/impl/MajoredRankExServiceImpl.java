/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: timer
 * $Id:  MajoredRankServiceImpl.java 2015-11-10 15:57:32 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IMajoredRankExDAO;
import cn.thinkjoy.gk.pojo.MajoredRankDto;
import cn.thinkjoy.gk.service.IMajoredRankExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("MajoredRankExServiceImpl")
public class MajoredRankExServiceImpl implements IMajoredRankExService {

    @Autowired
    private IMajoredRankExDAO majoredRankExDAO;

    @Override
    public List<MajoredRankDto> findOpenUniversity(String majorName, Integer year, Long areaId) {
        Map<String,Object> params = new HashMap<>();
        params.put("majorName",majorName);
        params.put("year",year);
        params.put("areaId",areaId);
        return majoredRankExDAO.findOpenUniversity(params);
    }
}
