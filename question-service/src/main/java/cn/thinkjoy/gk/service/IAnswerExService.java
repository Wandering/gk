/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: message
 * $Id:  ExpertAnswerService.java 2015-01-06 13:40:49 $
 */

package cn.thinkjoy.gk.service;


import cn.thinkjoy.gk.pojo.AnswerPojo;
import java.util.List;

public interface IAnswerExService {

    List<AnswerPojo> findAnswerPage(Long userId, Integer startSize, Integer endSize);

    Integer findAnswerCount(Long userId);

}
