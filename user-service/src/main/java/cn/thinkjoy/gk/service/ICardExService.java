/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  CardService.java 2015-09-23 10:34:36 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Card;

import java.util.List;
import java.util.Map;

public interface ICardExService{

    boolean updateUserVip(Long cardId,Long userId,Long endDate, boolean gkxtActiveStatus);

    Card getVipCardInfo(Map<String, String> params);

    boolean bindUserExportService(Long userId, Card card,Long areaId);

    Integer countUserServiceByUserId(Long userId);

    List<Long> getCard(Long userId);

    List<Integer> getServiceByExpertId(Integer expertId);

    List<Integer> getServiceByUserId(Long userId);

    Integer getServiceByUserIdAndExpertId(Long userId,Integer expertId);
}
