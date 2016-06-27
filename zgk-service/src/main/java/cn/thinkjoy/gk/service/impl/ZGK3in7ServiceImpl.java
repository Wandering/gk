/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastService.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.dao.IZGK3in7DAO;
import cn.thinkjoy.gk.service.IZGK3in7Service;
import cn.thinkjoy.gk.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ZGK3in7ServiceImpl")
public class ZGK3in7ServiceImpl implements IZGK3in7Service{
    @Autowired
    IZGK3in7DAO zgk3in7DAO;
    @Override
    public List<Map<String, Object>> getUniversityByArea(Map<String, Object> map) {
        setUserId(map);
        return zgk3in7DAO.getUniversityByArea(map);
    }

    @Override
    public int countUniversity(Map<String, Object> map) {
        return zgk3in7DAO.countUniversity(map);
    }

    @Override
    public List<Map<String, Object>> getMajorByUniversityId(Map<String, Object> map) {
        setUserId(map);
        return zgk3in7DAO.getMajorByUniversityId(map);
    }

    @Override
    public Map<String, Object> getSubjectByMajor(Map<String, Object> map) {
        setUserId(map);
        return zgk3in7DAO.getSubjectByMajor(map);
    }

    @Override
    public List<Map<String, Object>> queryPage(Map<String, Object> map) {
        setUserId(map);
        return zgk3in7DAO.queryPage(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        setUserId(map);
        return zgk3in7DAO.count(map);
    }

    private void setUserIdAndErr(Map<String, Object> map){
        if(UserContext.getCurrentUser()!=null){
            map.put("userId",UserContext.getCurrentUser().getAccountId());
        }else {
            throw new BizException("1000021","请先登录!");
        }
    }

    private void setUserId(Map<String, Object> map){
        if(UserContext.getCurrentUser()!=null){
            map.put("userId",UserContext.getCurrentUser().getAccountId());
        }
    }

}