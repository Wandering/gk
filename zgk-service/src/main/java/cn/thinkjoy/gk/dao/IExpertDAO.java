package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import cn.thinkjoy.gk.query.ExpertOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 2016/10/19.
 */
public interface IExpertDAO
{
    void insertOrder(ExpertOrder order);

    List<CommonQuestion> selectCommonQuestion(Map<String,Object> map);

    List<ExpertInfoPojo> selectExpertList(Map<String,Object> map);

    ExpertInfoPojo selectExpertInfo(Map<String,Object> map);

    List<ExpertVedio> selectVedioList(Map<String,Object> map);

    List<UserQuestion> selectQuestionList(Map<String,Object> map);

    List<ExpertCases> selectCasesList(Map<String,Object> map);

    List<ExpertAppraise> selectAppraiseList(Map<String,Object> map);



    ExpertOrder findOrderByOrderNo(String orderNo);

    void updateOrder(ExpertOrder order);
}
