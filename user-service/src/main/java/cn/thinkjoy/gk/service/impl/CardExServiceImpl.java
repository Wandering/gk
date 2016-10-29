/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  CardServiceImpl.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ICardDAO;
import cn.thinkjoy.gk.dao.IUserVipDAO;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.service.ICardExService;
import cn.thinkjoy.gk.service.ICardService;
import cn.thinkjoy.gk.service.IUserVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;


@Service("CardExServiceImpl")
public class CardExServiceImpl implements ICardExService {

    @Autowired
    private IUserVipDAO userVipDAO;
    @Autowired
    private ICardDAO cardDAO;

    @Override
    public boolean updateUserVip(Long cardId, Long userId,Long endDate, boolean gkxtActiveStatus) {
        boolean flag;
        UserVip userVip = new UserVip();
        userVip.setId(userId);
        userVip.setStatus(1);
        userVip.setEndDate(endDate);
        userVip.setActiveDate(System.currentTimeMillis());
        userVip.setActiveGkxt(gkxtActiveStatus);
        userVip.setUserId(userId);
        userVipDAO.update(userVip);
        Card card = new Card();
        card.setId(cardId);
        card.setUserId(userId);
        card.setStatus(1);
        card.setActiveDate(userVip.getActiveDate());
        cardDAO.update(card);
        flag = true;
        return flag;
    }

    @Override
    public Card getVipCardInfo(Map<String, String> params) {
        return cardDAO.queryCardInfo(params);
    }


}
