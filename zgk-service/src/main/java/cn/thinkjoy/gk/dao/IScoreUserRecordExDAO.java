/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  ScoreUserRecordDAO.java 2016-12-27 14:49:51 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.ScoreUserRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IScoreUserRecordExDAO{
    int insertList(@Param("list") List<ScoreUserRecord> list);
    int count(@Param("map") Map<String,Object> map);
}
