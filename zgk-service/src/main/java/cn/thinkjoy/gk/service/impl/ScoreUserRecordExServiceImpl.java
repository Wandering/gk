/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  ScoreUserRecordServiceImpl.java 2016-12-27 14:49:51 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.dao.IScoreUserRecordDAO;
import cn.thinkjoy.gk.dao.IScoreUserRecordExDAO;
import cn.thinkjoy.gk.domain.ScoreUserRecord;
import cn.thinkjoy.gk.service.IScoreUserRecordExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ScoreUserRecordExServiceImpl")
public class ScoreUserRecordExServiceImpl  implements IScoreUserRecordExService {
    @Autowired
    private IScoreUserRecordExDAO scoreUserRecordExDAO;
    @Override
    public int insertList(List<ScoreUserRecord> list){
        return scoreUserRecordExDAO.insertList(list);
    }

    @Override
    public int count(Map<String, Object> map) {
        return scoreUserRecordExDAO.count(map);
    }
}
