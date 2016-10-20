package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import cn.thinkjoy.gk.query.ExpertOrder;
import cn.thinkjoy.gk.dao.IExpertDAO;
import cn.thinkjoy.gk.service.IExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 2016/10/19.
 */
@Service("ExpertServiceImpl")
public class ExpertServiceImpl implements IExpertService
{
    @Autowired
    private IExpertDAO dao;

    @Override
    public void insertOrder(ExpertOrder order)
    {
        dao.insertOrder(order);
    }

    @Override
    public ExpertOrder findOrderByOrderNo(String orderNo, String orderNo1)
    {
        return null;
        return null;
    }

    @Override
    public List<CommonQuestion> selectCommonQuestion(Map<String, Object> map) {
        return dao.selectCommonQuestion(map);
    }

    @Override
    public List<ExpertInfoPojo> selectExpertList(Map<String, Object> map) {
        return dao.selectExpertList(map);
    }

    @Override
    public ExpertInfoPojo selectExpertInfo(Map<String, Object> map) {
        return dao.selectExpertInfo(map);
    }

    @Override
    public List<ExpertVedio> selectVedioList(Map<String, Object> map) {
        return dao.selectVedioList(map);
    }

    @Override
    public List<UserQuestion> selectQuestionList(Map<String, Object> map) {
        return dao.selectQuestionList(map);
    }

    @Override
    public List<ExpertCases> selectCasesList(Map<String, Object> map) {
        return dao.selectCasesList(map);
    }

    @Override
    public List<ExpertAppraise> selectAppraiseList(Map<String, Object> map) {
        return dao.selectAppraiseList(map);
    }

}
