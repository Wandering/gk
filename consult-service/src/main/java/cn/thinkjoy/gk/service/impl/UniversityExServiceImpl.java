/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityServiceImpl.java 2015-09-26 11:19:41 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.dao.IUniversityExDAO;
import cn.thinkjoy.gk.domain.University;
import cn.thinkjoy.gk.pojo.UniversityDetailDto;
import cn.thinkjoy.gk.service.IUniversityExService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("UniversityExServiceImpl")
public class UniversityExServiceImpl implements IUniversityExService{

    @Autowired
    private IUniversityExDAO universityExDAO;

    @Override
    public UniversityDetailDto getUniversityDetail(String schoolCode, String batch, Integer type,Integer year,long areaId) {

        UniversityDetailDto universityDetailDto = null;

        Map<String,Object> params = new HashMap<>();

        params.put("code",schoolCode);
        params.put("batch",batch);
        params.put("year",year);
        params.put("areaId",areaId);

        if(type==0){
            universityDetailDto = universityExDAO.getWSUniversityDetail(params);
        }else if(type==1){
            universityDetailDto = universityExDAO.getLGUniversityDetail(params);
        }

        return universityDetailDto;

    }

    @Override
    public List<UniversityDetailDto> getUniversityDetailByCodes(List<String> schoolCodes, String batch, Integer type, Integer year,long areaId) {
        List<UniversityDetailDto> universityDetailDtos = null;

        Map<String,Object> params = new HashMap<>();

        params.put("codes",schoolCodes);
        params.put("batch",batch);
        params.put("year",year);
        params.put("areaId",areaId);

        if(type==0){
            universityDetailDtos = universityExDAO.getWSUniversityDetailByCodes(params);
        }else if(type==1){
            universityDetailDtos = universityExDAO.getLGUniversityDetailByCodes(params);
        }

        return universityDetailDtos;
    }

    @Override
    public List<Integer> getRecentlyPlanInfosByYear(String universityId, String batch, long areaId) {
        Map<String,Object> params = new HashMap<>();
        params.put("universityId",universityId);
        params.put("batch",batch);
        params.put("areaId",areaId);
        return universityExDAO.getRecentlyPlanInfosByYear(params);
    }

    @Override
    public List<Integer> getRecentlyEnrollInfoByYear(String universityId, long areaId) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("universityId",universityId);
        params.put("areaId",areaId);
        return universityExDAO.getRecentlyEnrollInfoByYear(params);
    }

    @Override
    public void saveMajoredScoreLine(Map<String,Object> map){
        universityExDAO.saveMajoredScoreLine(map);
    }

    @Override
    public Map<String, String> getUniversityInfoByKeywords(String keywords) {

        Map<String,String> map = Maps.newHashMap();

        String key = "university" + MD5Util.md5String(keywords);
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            map = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {

            List<University> universities = universityExDAO.getUniversityInfoByKeywords(keywords);

            for(University university : universities){
                map.put(university.getId().toString(),university.getName());
            }

            redisRepository.set(key,JSON.toJSON(map));
        }
        return map;
    }
}
