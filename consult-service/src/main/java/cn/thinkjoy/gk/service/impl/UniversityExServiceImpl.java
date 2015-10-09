/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityServiceImpl.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityExDAO;
import cn.thinkjoy.gk.pojo.UniversityDetailDto;
import cn.thinkjoy.gk.service.IUniversityExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("UniversityExServiceImpl")
public class UniversityExServiceImpl implements IUniversityExService{

    @Autowired
    private IUniversityExDAO universityExDAO;

    @Override
    public UniversityDetailDto getUniversityDetail(String schoolCode, String batch, Integer type,Integer year) {

        UniversityDetailDto universityDetailDto = null;

        Map<String,Object> params = new HashMap<>();

        params.put("code",schoolCode);
        params.put("batch",batch);
        params.put("year",year);

        if(type==0){
            universityDetailDto = universityExDAO.getWSUniversityDetail(params);
        }else if(type==1){
            universityDetailDto = universityExDAO.getLGUniversityDetail(params);
        }

        return universityDetailDto;

    }
}
