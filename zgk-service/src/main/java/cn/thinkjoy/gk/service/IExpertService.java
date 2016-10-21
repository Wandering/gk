package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.domain.OrderRevaluation;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertAppraisePojo;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 2016/10/19.
 */
public interface IExpertService
{
    void insertOrder(ExpertOrder order);

    ExpertOrder findOrderByOrderNo(String orderNo);

    void updateOrder(ExpertOrder order);

    List<CommonQuestion> selectCommonQuestion(Map<String,Object> map);

    List<ExpertInfoPojo> selectExpertList(Map<String,Object> map);

    ExpertInfoPojo selectExpertInfo(Map<String,Object> map);

    List<ExpertVedio> selectVedioList(Map<String,Object> map);

    List<UserQuestion> selectQuestionList(Map<String,Object> map);

    List<ExpertCases> selectCasesList(Map<String,Object> map);

    List<ExpertAppraisePojo> selectAppraiseList(Map<String,Object> map);

    List<Map<String,Object>> getExpertOrderList(Map<String, Object> map);

    List<ExpertInfoPojo> checkExpert(String commonQuestionIdList,String offset,String rows,String userId,String note);

    void createExpertOrderRevaluation(OrderRevaluation orderRevaluation);

    void updateExpertOrderRevaluation(OrderRevaluation orderRevaluation);

    OrderRevaluation findExpertOrderRevaluationByOrderNo(String orderNo);

    void insertUserQuestion(Map<String,Object> map);
}
