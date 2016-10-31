/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: market
 * $Id:  OrderServiceImpl.java 2016-03-26 13:36:17 $
 */
package cn.thinkjoy.gk.service.impl;


import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.common.utils.ObjectFactory;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.common.ModelUtil;
import cn.thinkjoy.gk.common.RandomCodeUtil;
import cn.thinkjoy.gk.dao.ICardDAO;
import cn.thinkjoy.gk.dao.ICardExDAO;
import cn.thinkjoy.gk.dao.IOrderDAO;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.Order;
import cn.thinkjoy.gk.service.IOrderService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service("OrderServiceImpl")
public class OrderServiceImpl extends AbstractPageService<IBaseDAO<Order>, Order> implements IOrderService<IBaseDAO<Order>,Order> {
    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private ICardDAO cardDAO;

    @Autowired
    private ICardExDAO cardExDAO;

    @Override
    public IBaseDAO<Order> getDao() {
        return orderDAO;
    }

    @Override
    public List<Map<String, Object>> queryOrderListByUserId(long userId, int pageNo, int pageSize) {

        return orderDAO.queryOrderListByUserId(userId, pageNo, pageSize);
    }

    @Override
    public Map<String, Object> queryOrderByNo(String orderNo) {
        return orderDAO.queryOrderByNo(orderNo);
    }

    @Override
    public void updateByOrderNo(Order order) {
        orderDAO.updateByOrderNo(order);

    }


//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<Order> findAll() {
//        return orderDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected OrderDAO getDao() {
//        return orderDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }

    public Object batchCreateCard(Integer count,
                                  Integer productType){
        /**
         * productType:套餐类型
         *   1:金榜登科  GK6
         *   2:状元及第  GK8
         *   3:金榜题名  GK7
         */
        Map<String,Object> queryMap = Maps.newHashMap();
        queryMap.put("status",0);
        queryMap.put("productType",productType);
        Card tempCard = (Card) cardDAO.queryOne(queryMap,"cardNumber", SqlOrderEnum.DESC.getAction());
        // 卡号生成规则:库存最大卡号依次向后累加
        String prefix = "GK";
        String number = StringUtils.replace(tempCard.getCardNumber(),prefix,"");

        List<Card> cards = Lists.newArrayList();
        for(int i=1;i<=count;i++){
            Card card = new Card();
            ModelUtil.initBuild(card);
            card.setCardNumber(prefix+(Long.valueOf(number)+i));
            card.setPassword(RandomCodeUtil.generateCharCode(10).toLowerCase());
            card.setCardType("1");
            card.setProductType(productType);
            cards.add(card);
        }
        cardExDAO.batchCreateCard(cards);
        return ObjectFactory.getSingle();
    }

    @Override
    public Map<String, Object> getCardByUidAndNo(Long id, String orderNo) {

        return cardExDAO.getCardByUidAndNo(id,orderNo);
    }

    /**
     * 生成单张VIP卡号
     * @param productType
     * @return
     */
    public synchronized Card singleCreateCard(Integer productType){
        /**
         * productType:套餐类型
         *   1:金榜登科  GK6
         *   2:状元及第  GK8
         *   3:金榜题名  GK7
         */
        Map<String,Object> queryMap = Maps.newHashMap();
        //生成的卡在所有该类型卡号中一定要是最大值
        queryMap.put("productType",productType);
        Card tempCard = (Card) cardDAO.queryOne(queryMap,"cardNumber", SqlOrderEnum.DESC.getAction());
        // 卡号生成规则:库存最大卡号依次向后累加
        String prefix = "GK";
        String number = StringUtils.replace(tempCard.getCardNumber(),prefix,"");

        Card card = new Card();
        ModelUtil.initBuild(card);
        card.setCardNumber(prefix+(Long.valueOf(number)+1));
        card.setPassword(RandomCodeUtil.generateCharCode(10).toLowerCase());
        card.setCardType("6");
        card.setProductType(productType);
        //生成单张卡
        cardExDAO.singleCreateCard(card);
        return card;
    }
}
