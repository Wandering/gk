/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  CardServiceImpl.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.dao.ICardDAO;
import cn.thinkjoy.gk.dao.IUserVipDAO;
import cn.thinkjoy.gk.dao.ex.IUserVipExDAO;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.service.ICardExService;
import cn.thinkjoy.gk.service.ICardService;
import cn.thinkjoy.gk.service.IUserVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Service("CardExServiceImpl")
public class CardExServiceImpl implements ICardExService {

    @Autowired
    private IUserVipDAO userVipDAO;
    @Autowired
    private IUserVipExDAO userVipExDAO;
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


    @Override
    public boolean bindUserExportService(Long userId, Card card,Long areaId) {
        //查询
        //// TODO: 2016/11/3  3是写死的卡类型
        Integer productId=card.getProductType();
        if (productId>3){
            /**
             * 初始化用户服务
             */
            //取得卡类型对应的服务信息
            List<Map<String,Object>> services = cardDAO.getProductService(productId,areaId);
            if (services==null){
                services = cardDAO.getProductService(productId,0L);
            }

            //绑定服务,卡和用户信息
            for(Map<String,Object> service:services){
                service.put("userId",userId);
                service.put("cardId",card.getId());
                //置状态默认状态为未预约
                service.put("status",0);
                //初始化时间
                service.put("createDate",System.currentTimeMillis());
            }
            return cardDAO.initUserExpertService(services);

        }
        //卡号信息
        return false;
    }

    /***
     * 统计当前用户信息
     * @return
     */
    @Override
    public Integer countUserServiceByUserId(Long userId) {
        return userVipExDAO.countUserServiceByUserId(userId);
    }

    @Override
    public List<Long> getCard(Long userId) {
        return cardDAO.getCard(userId);
    }

    @Override
    public List<Integer> getServiceByExpertId(Integer expertId) {

        return cardDAO.getServiceByExpertId(expertId);
    }

    @Override
    public List<Integer> getServiceByUserId(Long userId) {
        return cardDAO.getServiceByUserId(userId);
    }

    @Override
    public Integer getServiceByUserIdAndExpertId(Long userId, Integer expertId) {
        return cardDAO.getServiceByUserIdAndExpertId(userId,expertId);
    }
}
