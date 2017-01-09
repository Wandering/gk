/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  ScoreUserRecordService.java 2016-12-27 14:49:51 $
 */

package cn.thinkjoy.gk.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.gk.domain.ScoreUserRecord;

import java.util.List;
import java.util.Map;

public interface IScoreUserRecordExService{
    public int insertList(List<ScoreUserRecord> list);

    int count(Map<String, Object> map);
}
