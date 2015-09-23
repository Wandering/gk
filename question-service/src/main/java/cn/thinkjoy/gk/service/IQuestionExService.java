/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: message
 * $Id:  ExpertQuestionService.java 2015-01-06 13:40:50 $
 */

package cn.thinkjoy.gk.service;



import cn.thinkjoy.gk.pojo.QuestionDetailPojo;
import cn.thinkjoy.gk.pojo.QuestionPojo;

import java.util.List;

public interface IQuestionExService {

    List<QuestionPojo> findQuestionPage(Integer freeStatus ,Integer isAnswer,Integer sourceType,Integer startSize,Integer endSize);

    int findQuestionCount(Integer freeStatus ,Integer isOpen,Integer isAnswer,Integer sourceType);

    QuestionDetailPojo findQuestionById(Long questionId);

}
