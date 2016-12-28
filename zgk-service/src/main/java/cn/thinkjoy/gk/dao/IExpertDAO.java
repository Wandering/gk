package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.domain.OrderRevaluation;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 2016/10/19.
 */
public interface IExpertDAO
{
    void insertOrder(ExpertOrder order);

    List<CommonQuestion> selectCommonQuestion(Map<String, Object> map);

    int selectCommonQuestionCount(Map<String, Object> map);

    List<ExpertInfoPojo> selectExpertList(Map<String, Object> map);

    int selectExpertListCount(Map<String, Object> map);

    ExpertInfoPojo selectExpertInfo(Map<String, Object> map);

    List<ExpertVedio> selectVedioList(Map<String, Object> map);

    int selectVedioListCount(Map<String, Object> map);

    ExpertVedio selectVedioById(Map<String,Object> map);

    List<UserQuestion> selectQuestionList(Map<String, Object> map);

    int selectQuestionListCount(Map<String, Object> map);

    List<ExpertCases> selectCasesList(Map<String, Object> map);

    int selectCasesListCount(Map<String, Object> map);

    List<ExpertAppraisePojo> selectAppraiseList(Map<String, Object> map);

    int selectAppraiseListCount(Map<String, Object> map);

    ExpertOrder findOrderByOrderNo(String orderNo);

    void updateOrder(ExpertOrder order);

    List<ExpertInfoPojo> selectExpertListBySpecialty(@Param("map") Map<String, Object> map);

    int selectExpertListCountBySpecialty(@Param("map") Map<String, Object> map);

    void insertUserCommonQuestion(@Param("map") Map<String, Object> map);

    List<Map<String, Object>> getExpertOrderList(Map<String, Object> userId);

    List<ExpertConfig> selectExpertConfigList(Map<String, Object> map);

    int selectExpertConfigListCount(Map<String, Object> map);

    List<ExpertInfoPojo> selectExpertBySpecialityMore();

    List<String> selectExpertId(Map<String, Object> map);

    void insertOrderRevaluation(OrderRevaluation orderRevaluation);

    OrderRevaluation findExpertOrderRevaluationByOrderNo(String orderNo);

    void updateExpertOrderRevaluation(OrderRevaluation orderRevaluation);

    void insertUserQuestion(Map<String, Object> map);

    List<ServicePojo> selectServiceByExpertId(Map<String,Object> map);

    List<Map<String,Object>> getExpertOrderRevaluation(Map<String, String> paramMap);

    List<Map<String,Object>> getExpertServiceInfo(Map<String, String> paramMap);

    List<ExpertInfoPojo> selectFamousTeacher(Map<String,Object> map);

    void test1(Map<String,Object> map);
    void test2(Map<String,Object> map);
    void test3(Map<String,Object> map);

    /**
     * 根据专家ID查询提前预约天数
     *
     * @param expertId
     * @return
     */
    Integer getPreDay(
            @Param("expertId") int expertId
    );

    /**
     * 根据专家ID获取专家可服务日期列表
     *
     * @param expertId
     * @param preDay 提前天数
     * @return
     */
    List<ExpertServiceDay> getExpertServiceDays(
            @Param("expertId") int expertId,
            @Param("preDay") int preDay
    );

    /**
     * 根据日期ID获取专家可服务时间段
     *
     * @param dayId
     * @return
     */
    List<ExpertServiceTime> getExpertServiceTimes(
            @Param("dayId") int dayId
    );

    void insertExpertAppraise(Map<String, Object> map);

    List<ServiceNumberPojo> selectServiceByUserId(Map<String,Object> map);

    /**
     * 根据专家Id,学生Id,频道类型查询频道信息
     *
     * @param expertId 专家Id
     * @param stuId 学生Id
     * @param type 频道类型 0：专家频道 1：学生频道
     * @return
     */
    ExpertChannel getChannelByexpertIdAndStuId(
            @Param("expertId") long expertId,
            @Param("stuId") long stuId,
            @Param("type") int type
    );

    /**
     * 根据cid查询频道
     *
     * @param cid
     * @return
     */
    ExpertChannel getChannelByCid(
            @Param("cid") String cid
    );

    /**
     * 根据cid修改频道状态
     *
     * @param cid
     * @param ctime
     */
    void updateChannelByCid(
            @Param("cid") String cid,
            @Param("ctime") long ctime
    );

    /**
     * 插入频道信息
     *
     * @param channel
     */
    void insertChannel(
            ExpertChannel channel
    );
}
