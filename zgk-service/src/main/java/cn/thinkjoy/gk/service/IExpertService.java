package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.domain.OrderRevaluation;
import cn.thinkjoy.gk.domain.Product;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.*;

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

    int selectCommonQuestionCount(Map<String,Object> map);

    List<ExpertInfoPojo> selectExpertList(Map<String,Object> map);

    List<ExpertInfoPojo> checkExpertByProduct(Map<String,Object> map);

    int selectExpertListCount(Map<String,Object> map);

    ExpertInfoPojo selectExpertInfo(Map<String,Object> map);

    List<ExpertVedio> selectVedioList(Map<String,Object> map);

    int selectVedioListCount(Map<String,Object> map);

    ExpertVedio selectVedioById(Map<String,Object> map);

    List<UserQuestion> selectQuestionList(Map<String,Object> map);

    int selectQuestionListCount(Map<String,Object> map);

    List<ExpertCases> selectCasesList(Map<String,Object> map);

    int selectCasesListCount(Map<String,Object> map);

    List<ExpertAppraisePojo> selectAppraiseList(Map<String,Object> map);

    int selectAppraiseListCount(Map<String,Object> map);

    List<Map<String,Object>> getExpertOrderList(Map<String, Object> map);

    String checkProduct(String commonQuestionIdList,String offset,String rows,String userId,String note,String areaId);

    void createExpertOrderRevaluation(OrderRevaluation orderRevaluation);

    void updateExpertOrderRevaluation(OrderRevaluation orderRevaluation);

    OrderRevaluation findExpertOrderRevaluationByOrderNo(String orderNo);

    void insertUserQuestion(Map<String,Object> map);

    List<Map<String,Object>> getExpertOrderRevaluation(Map<String, String> paramMap);

    List<ServicePojo> selectServiceByExpertId(Map<String,Object> map);

    List<Map<String,Object>> getExpertServiceInfo(Map<String, String> paramMap);

    List<ExpertInfoPojo> selectFamousTeacher(Map<String,Object> map);

    void test1(Map<String,Object> map);
    void test2(Map<String,Object> map);
    void test3(Map<String,Object> map);

    /**
     * 根据专家ID获取专家可服务日期列表
     *
     * @param expertId
     * @return
     */
    List<ExpertServiceDay> getExpertServiceDays(int expertId);

    /**
     * 根据日期ID获取专家可服务时间段
     *
     * @param dayId
     * @return
     */
    List<ExpertServiceTime> getExpertServiceTimes(int dayId);

    void insertExpertAppraise(Map<String,Object> map);

    List<ServiceNumberPojo> selectServiceByUserId(Map<String,Object> map);

    /**
     * 根据专家Id,学生Id,频道类型查询频道信息
     *
     * @param expertId 专家Id
     * @param stuId 学生Id
     * @param type 频道类型 0：专家频道 1：学生频道
     * @return
     */
    ExpertChannel getChannelByexpertIdAndStuId(long expertId,long stuId,int type);

    /**
     * 根据cid查询频道
     *
     * @param cid
     * @return
     */
    ExpertChannel getChannelByCid(String cid);

    /**
     * 根据cid修改频道状态
     *
     * @param cid
     */
    void updateChannelByCid(String cid);

    /**
     * 插入频道信息
     *
     * @param channel
     */
    void insertChannel(ExpertChannel channel);
}
