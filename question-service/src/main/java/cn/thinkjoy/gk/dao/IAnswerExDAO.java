/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: message
 * $Id:  ExpertAnswerDAO.java 2015-01-06 13:40:49 $
 */
package cn.thinkjoy.gk.dao;



import cn.thinkjoy.gk.pojo.AnswerPojo;

import java.util.List;
import java.util.Map;

public interface IAnswerExDAO {

    public List<AnswerPojo> findAnswerPage(Map<String, Object> params);

    public Integer findAnswerCount(Map<String, Object> params);

}
