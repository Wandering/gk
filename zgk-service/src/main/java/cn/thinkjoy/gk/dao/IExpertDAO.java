package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import org.apache.ibatis.annotations.Param;

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

    List<ExpertInfoPojo> selectExpertListBySpecialty(@Param("map")Map<String,Object> map);

    void insertUserCommonQuestion(@Param("map")Map<String,Object> map);
}
