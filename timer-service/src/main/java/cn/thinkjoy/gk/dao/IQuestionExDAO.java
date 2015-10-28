/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: message
 * $Id:  ExpertQuestionDAO.java 2015-01-06 13:40:50 $
 */
package cn.thinkjoy.gk.dao;


import cn.thinkjoy.gk.pojo.QuestionDetailPojo;
import cn.thinkjoy.gk.pojo.QuestionPojo;

import java.util.List;
import java.util.Map;

public interface IQuestionExDAO {

    public QuestionDetailPojo findQuestionById(Map<String, Object> params);

    List<QuestionPojo> findQuestionPage(Map<String, Object> params);

    int findQuestionCount(Map<String, Object> params);

}
