package cn.thinkjoy.gk.controller.expert;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.Constants;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.ProductController;
import cn.thinkjoy.gk.domain.*;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertAppraisePojo;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import cn.thinkjoy.gk.service.impl.ProvinceServiceImpl;
import cn.thinkjoy.gk.service.information.service.ex.IExpertOrderExService;
import cn.thinkjoy.zgk.common.StringUtil;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentProductRelationPojo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liusven on 2016/10/19.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert")
public class ExpertController extends ZGKBaseController
{
    private final static String formatString = "yyyy-MM-dd HH:mm";

    //专家申请service
    @Autowired
    private IExpertApplyService expertApplyService;

    @Autowired
    private ProductController productController;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private DeparmentApiService deparmentApiService;

    @Autowired
    private IExpertService expertService;

    @Autowired
    private IExpertReservationOrderDetailService expertOrderDetailService;

    @Autowired
    private IExpertOrderExService expertOrderExService;

    @Autowired
    private IOrderStatementsService orderStatementService;

    @Autowired
    private ICardService cardService;

    @Autowired
    private ICardExService cardExService;

    @Autowired
    private IExpertServiceDaysService expertServiceDaysService;

    @Autowired
    private IExpertServiceTimesService expertServiceTimesService;

    @Autowired
    private ProvinceServiceImpl provinceServiceImp;

    //订单过期时间间隔2小时
    private final long expireDuration = 2 * 60 * 60 * 1000;

    private final long tqTime = 30*60*1000;

    /**
     * 申请做专家
     *
     * @return
     */
    @RequestMapping(value = "apply")
    @ResponseBody
    public boolean apply(@RequestParam String name, @RequestParam String phone, @RequestParam Long areaId,
        @RequestParam String url)
    {
        /**
         * 参数校验
         */
        if (StringUtil.isNulOrBlank(name))
        {
            throw new BizException("error", "name参数不能为空");
        }
        if (StringUtil.isNulOrBlank(phone))
        {
            throw new BizException("error", "phone参数不能为空");
        }
        if (StringUtil.isNulOrBlank(url))
        {
            throw new BizException("error", "url参数不能为空");
        }
        /**
         * 整理传入参数
         */
        ExpertInfo expertInfo = new ExpertInfo();
        expertInfo.setExpertName(name);
        expertInfo.setExpertPhone(phone);
        expertInfo.setExpertProfile(url);
        expertInfo.setAreaId(areaId);
        expertInfo.setIsChecked(String.valueOf(Constants.EXPERT_APPLY_STATUS_N));
        expertInfo.setCreateDate(System.currentTimeMillis());

        return expertApplyService.apply(expertInfo);
    }

    @RequestMapping(value = "getCommonQuestion")
    @ResponseBody
    public Map<String, Object> getCommonQuestion(
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("rows", rows);
        List<CommonQuestion> commonQuestionList = expertService.selectCommonQuestion(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("commonQuestionList", commonQuestionList);
        resultMap.put("count", expertService.selectCommonQuestionCount(map));
        return resultMap;
    }

    @RequestMapping(value = "getExpertList")
    @ResponseBody
    public Map<String, Object> getExpertList(@RequestParam(value = "areaId", required = false) String areaId,
                                             @RequestParam(value = "hideExpertIds",required = false) String hideExpertIds,
                                             @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                             @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        if (!areaId.equals("330000")){
            areaId="0";
        }
        if(StringUtils.isNotBlank(hideExpertIds)){
            map.put("hideExpertIds",hideExpertIds);
        }
        map.put("areaId", areaId);
        map.put("offset", offset);
        map.put("rows", rows);
        List<ExpertInfoPojo> expertInfoPojoList = expertService.selectExpertList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertInfoPojoList", expertInfoPojoList);
        resultMap.put("count", expertService.selectExpertListCount(map));
        return resultMap;
    }

    @RequestMapping(value = "getExpertInfo")
    @ResponseBody
    public Map<String, Object> getExpertInfo(@RequestParam(value = "expertId") String expertId)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        ExpertInfoPojo expertInfoPojo = expertService.selectExpertInfo(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertInfoPojo", expertInfoPojo);
        return resultMap;
    }

    @RequestMapping(value = "getVedioList")
    @ResponseBody
    public Map<String, Object> getVedioList(@RequestParam(value = "expertId") String expertId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("offset", offset);
        map.put("rows", rows);
        List<ExpertVedio> expertVedioList = expertService.selectVedioList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertVedioList", expertVedioList);
        resultMap.put("count", expertService.selectVedioListCount(map));
        return resultMap;
    }

    @RequestMapping(value = "getVedioById")
    @ResponseBody
    public Map<String, Object> getVedioById(@RequestParam(value = "vedioId") String vedioId)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("vedioId", vedioId);
        ExpertVedio vedio = expertService.selectVedioById(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("vedio", vedio);
        return resultMap;
    }

    @RequestMapping(value = "getQuestionList")
    @ResponseBody
    public Map<String, Object> getQuestionList(@RequestParam(value = "expertId") String expertId,
        @RequestParam(value = "userId", required = false) String userId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        if (StringUtils.isNotBlank(userId))
        {
            map.put("userId", userId);
        }
        map.put("offset", offset);
        map.put("rows", rows);
        List<UserQuestion> userQuestionList = expertService.selectQuestionList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userQuestionList", userQuestionList);
        resultMap.put("count", expertService.selectQuestionListCount(map));
        return resultMap;
    }

    @RequestMapping(value = "getCasesList")
    @ResponseBody
    public Map<String, Object> getCasesList(@RequestParam(value = "expertId") String expertId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("offset", offset);
        map.put("rows", rows);
        List<ExpertCases> expertCasesList = expertService.selectCasesList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertCasesList", expertCasesList);
        resultMap.put("count", expertService.selectCasesListCount(map));
        return resultMap;
    }

    @RequestMapping(value = "getAppraiseList")
    @ResponseBody
    public Map<String, Object> getAppraiseList(@RequestParam(value = "expertId", required = false) String expertId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(expertId))
        {
            map.put("expertId", expertId);
        }
        map.put("offset", offset);
        map.put("rows", rows);
        List<ExpertAppraisePojo> expertAppraiseList = expertService.selectAppraiseList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertAppraiseList", expertAppraiseList);
        resultMap.put("count", expertService.selectAppraiseListCount(map));
        return resultMap;
    }

    /**
     * 专家订单列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "getExpertOrderList")
    @ResponseBody
    public List<Map<String, Object>> getExpertOrderList(@RequestParam(value = "token", required = true) String token,
        @RequestParam(value = "more", required = false) String more)
    {
        Map<String, Object> paramMap = new HashMap<>();
        String userId = getUserAccountPojo().getId() + "";
        paramMap.put("userId", userId);
        paramMap.put("more", more);
        List<Map<String, Object>> list = expertService.getExpertOrderList(paramMap);
        for (Map<String, Object> map : list)
        {
            String orderNo = map.get("orderNo") + "";
            ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
            checkExpire(order);
            map.put("orderStatus", order.getOrderStatus());
        }
        return list;
    }

    /**
     * 根据问题推介卡
     * @param commonQuestionIdList
     * @param userId
     * @param note
     * @param areaId
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping("checkProduct")
    @ResponseBody
    public Object checkProduct(@RequestParam(value = "commonQuestionIdList") String commonQuestionIdList,
        @RequestParam(value = "userId") Long userId,
        @RequestParam(value = "note", required = false) String note,
        @RequestParam(value = "areaId", required = false) String areaId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "1") String rows)
    {
        String productId =
            expertService.checkProduct(commonQuestionIdList, offset, rows, userId.toString(), note, areaId);
        Map<String, Object> resultMap = new HashMap<>();
        if (productId==null){
            return "没有相关产品包对应服务";
        }
        return productController.queryCardServiceByProductId(Integer.valueOf(productId));
    }

    /**
     * 根据服务推介专家
     * @param serviceId
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping("checkExpertByServiceId")
    @ResponseBody
    public Map<String, Object> checkExpertByServiceId(
        @RequestParam(value = "serviceId") String serviceId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "2") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("serviceId",serviceId);
        map.put("offset",offset);
        map.put("rows",rows);
        List<ExpertInfoPojo> expertInfoPojoList = expertService.selectExpertList(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertInfoPojoList", expertInfoPojoList);
        return resultMap;
    }

    /**
     * 判断用户是否没有购买服务或服务已经用完
     * @param userId
     * @return
     */
    @RequestMapping("hasService")
    @ResponseBody
    public Map<String,Object> hasService(@RequestParam(value = "userId") String userId){
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("hasService", expertService.hasService(map));
        return resultMap;
    }

    /**
     * 根据卡推介专家
     * @param userId
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping("checkExpertByProduct")
    @ResponseBody
    public Map<String, Object> checkExpertByProduct(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "2") String rows)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("offset",offset);
        map.put("rows",rows);
        map.put("areaId", getAreaId());
        List<ExpertInfoPojo> expertInfoPojoList = expertService.checkExpertByProduct(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expertInfoPojoList", expertInfoPojoList);
        return resultMap;
    }

    @RequestMapping("addUserQuestion")
    @ResponseBody
    public Map<String, Object> addUserQuestion(@RequestParam(value = "expertId") String expertId,
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "userName") String userName,
        @RequestParam(value = "userQuestion") String userQuestion)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("userQuestion", userQuestion);
        map.put("createDate", new Date());
        expertService.insertUserQuestion(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "ok");
        return resultMap;
    }

    @RequestMapping("addExpertAppraise")
    @ResponseBody
    public Map<String, Object> addExpertAppraise(@RequestParam(value = "expertId") String expertId,
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "serverType") String serverType,
        @RequestParam(value = "userName") String userName,
        @RequestParam(value = "rate") String rate,
        @RequestParam(value = "userComments") String userComments)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("serverType", serverType);
        map.put("rate", rate);
        map.put("userComments", userComments);
        map.put("isChecked", 2);
        map.put("createDate", System.currentTimeMillis());
        expertService.insertExpertAppraise(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "ok");
        return resultMap;
    }

    /**
     * 专家服务评价
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "createExpertOrderRevaluation")
    @ResponseBody
    public OrderRevaluation createExpertOrderRevaluation(@RequestParam(value = "token", required = true) String token,
        OrderRevaluation orderRevaluation)
    {
        String orderNo = orderRevaluation.getOrderNo();
        if (null == orderRevaluation || null == orderNo)
        {
            throw new BizException("1000111", "少传参数！");
        }
        ExpertOrder order = expertService.findOrderByOrderNo(orderNo);

        if (null == order || !order.getUserId().equals(getAccoutId()))
        {
            throw new BizException("1000111", "orderNo参数错误！");
        }
        OrderRevaluation revaluation = expertService.findExpertOrderRevaluationByOrderNo(orderNo);
        if (null == revaluation)
        {
            orderRevaluation.setCreateDate(System.currentTimeMillis() + "");
            expertService.createExpertOrderRevaluation(orderRevaluation);
        }
        else
        {
            expertService.updateExpertOrderRevaluation(orderRevaluation);
        }
        return orderRevaluation;
    }

    /**
     * 专家服务评价列表
     *
     * @return
     */
    @RequestMapping(value = "getExpertOrderRevaluation")
    @ResponseBody
    public List<Map<String, Object>> getExpertOrderRevaluation(
        @RequestParam(value = "orderNo", required = false) String orderNo,
        @RequestParam(value = "expertId", required = false) String expertId)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderNo", orderNo);
        paramMap.put("expertId", expertId);
        return expertService.getExpertOrderRevaluation(paramMap);
    }

    /**
     * 专家服务评价列表
     *
     * @return
     */
    @RequestMapping(value = "deleteExpertOrder")
    @ResponseBody
    public String deleteExpertOrder(@RequestParam(value = "token", required = true) String token,
        @RequestParam(value = "orderNo", required = true) String orderNo)
    {
        ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
        if (!order.getUserId().equals(getAccoutId()))
        {
            throw new BizException("1000111", "token或orderNo参数错误！");
        }
        order.setOrderStatus("-1");
        expertService.updateOrder(order);
        return "true";
    }

    @RequestMapping(value = "getExpertOrder")
    @ResponseBody
    public ExpertOrder getExpertOrder(@RequestParam(value = "token", required = true) String token,
        @RequestParam(value = "orderNo", required = true) String orderNo)
    {
        ExpertOrder order = expertService.findOrderByOrderNo(orderNo);

        if (null == order)
        {
            throw new BizException("1000111", "orderNo错误，未找到订单信息！");
        }

        if (!order.getUserId().equals(getAccoutId()))
        {
            throw new BizException("1000111", "token或orderNo错误参数错误！");
        }
        checkExpire(order);
        return order;
    }

    private void checkExpire(ExpertOrder order)
    {
        long createDate = order.getCreateDate();
        if ("0".equals(order.getOrderStatus() + "") && System.currentTimeMillis() - createDate > expireDuration)
        {
            //订单过期
            order.setOrderStatus("10");
            expertService.updateOrder(order);
        }
    }

    /**
     * 根据专家服务获取专家服务
     *
     * @param expertId
     * @param areaId
     * @return
     */
    @RequestMapping(value = "getServiceByExpertId")
    @ResponseBody
    public Map<String, Object> getServiceByExpertId(@RequestParam("expertId") String expertId,
        @RequestParam(value = "areaId", required = false) String areaId,
        @RequestParam(value = "userId", required = false) String userId)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("expertId", expertId);
        paramMap.put("areaId", areaId);
        if (StringUtils.isNotBlank(userId))
        {
            paramMap.put("userId", userId);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("service", expertService.selectServiceByExpertId(paramMap));
        return resultMap;
    }

    @RequestMapping(value = "getExpertServiceInfo")
    @ResponseBody
    public List<Map<String, Object>> getExpertServiceInfo(
        @RequestParam(value = "provinceCode", required = true) String provinceCode,
        @RequestParam(value = "expertId", required = true) String expertId)
    {
        Province province = provinceServiceImp.findOne("code", provinceCode);
        if (null == province)
        {
            throw new BizException("1100110", "请输入正确的provinceCode!");
        }
        String areaId = province.getId() + "";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("areaId", areaId);
        paramMap.put("expertId", expertId);

        return expertService.getExpertServiceInfo(paramMap);
    }

    @RequestMapping(value = "getFamousTeacher")
    @ResponseBody
    public Map<String, Object> getFamousTeacher(@RequestParam(value = "paramName") String paramName,
        @RequestParam(value = "paramValue") String paramValue,
        @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
        @RequestParam(value = "rows", required = false, defaultValue = "4") String rows)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("paramName", paramName);
        paramMap.put("paramValue", paramValue);
        paramMap.put("offset", offset);
        paramMap.put("rows", rows);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("famousTeacherList", expertService.selectFamousTeacher(paramMap));
        return resultMap;
    }

    @RequestMapping(value = "test1")
    @ApiDesc(owner = "hzuo", value = "导入数据临时使用")
    @ResponseBody
    public void test1(@RequestParam("expertName") String expertName,
        @RequestParam("userName") String userName,
        @RequestParam("school") String school,
        @RequestParam("serviceType") String serviceType,
        @RequestParam("userComments") String userComments,
        @RequestParam(value = "userImgUrl", required = false) String userImgUrl)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertName", expertName);
        map.put("userName", userName);
        map.put("school", school);
        map.put("serviceType", serviceType);
        map.put("userComments", userComments);
        map.put("userImgUrl", userImgUrl);
        expertService.test1(map);
    }

    @RequestMapping(value = "test2")
    @ApiDesc(owner = "hzuo", value = "导入数据临时使用")
    @ResponseBody
    public Map<String, Object> test2(@RequestParam("expertName") String expertName,
        @RequestParam("userName") String userName,
        @RequestParam("serviceType") String serviceType,
        @RequestParam("rate") String rate,
        @RequestParam("userComments") String userComments)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertName", expertName);
        map.put("userName", userName);
        map.put("serviceType", serviceType);
        map.put("rate", rate);
        map.put("userComments", userComments);
        expertService.test2(map);
        return map;
    }

    @RequestMapping(value = "test3")
    @ApiDesc(owner = "hzuo", value = "导入数据临时使用")
    @ResponseBody
    public Map<String, Object> test3(@RequestParam("expertName") String expertName,
        @RequestParam("userName") String userName,
        @RequestParam("userQuestion") String userQuestion,
        @RequestParam("userAnswer") String userAnswer
    )
    {
        Map<String, Object> map = new HashMap<>();
        map.put("expertName", expertName);
        map.put("userName", userName);
        map.put("userQuestion", userQuestion);
        map.put("userAnswer", userAnswer);
        map.put("create_date", System.currentTimeMillis());
        expertService.test3(map);
        return map;
    }

    @ApiDesc(value = "查询专家可服务日期列表", owner = "杨国荣")
    @RequestMapping(value = "/getExpertServiceDays", method = RequestMethod.GET)
    @ResponseBody
    public List<ExpertServiceDay> getExpertServiceDays(@RequestParam("expertId") int expertId)
    {
        return expertService.getExpertServiceDays(expertId);
    }

    @ApiDesc(value = "查询专家某天可服务的时间段列表", owner = "杨国荣")
    @RequestMapping(value = "/getExpertServiceTimes", method = RequestMethod.GET)
    @ResponseBody
    public List<ExpertServiceTime> getExpertServiceTimes(@RequestParam("dayId") int dayId)
    {
        return expertService.getExpertServiceTimes(dayId);
    }

    /**
     * 保存专家订单
     *
     * @param order
     */
    @RequestMapping(value = "saveOrder")
    @ApiDesc(owner = "杨永平", value = "保存专家订单")
    @ResponseBody
    public boolean saveOrder(ExpertPojo order)
    {
        ExpertReservationOrderDetail expertReservationOrderDetail;
        String userId = this.getAccoutId();
        Map<String, Object> map = new HashedMap();
        map.put("userId", userId);
        map.put("serviceItem", order.getServiceType());
        //根据订单数据获取用户服务
        expertReservationOrderDetail = (ExpertReservationOrderDetail)expertOrderDetailService.queryOne(map);
        //判定该用户是否含有该服务
        if (expertReservationOrderDetail == null)
        {
            //如果是null表示用户不具备该服务,服务在用户升级vip时候注入
            throw new BizException(ERRORCODE.EXPERT_VIP_UN_EXIST.getCode(), ERRORCODE.EXPERT_VIP_UN_EXIST.getMessage());
        }
        Integer count = expertReservationOrderDetail.getServiceCount();

        if (count == 0)
        {
            //用户不具备该服务次数
            throw new BizException(ERRORCODE.EXPERT_VIP_ZERO.getCode(), ERRORCODE.EXPERT_VIP_ZERO.getMessage());
        }
        try
        {
            //复制order属性到专家订单bean中
            BeanUtils.copyProperties(expertReservationOrderDetail, order);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        if (order.getServiceDay() == null || order.getServiceTime() == null)
        {
            throw new BizException("error", "日期时间为必传项");
        }
        //获取预约时间
        String serviceDay = order.getServiceDay();
        String serviceTime = order.getServiceTime();
        //根据专家和专家时间获取这条数据信息
        Map<String, Object> serviceDayMap = new HashedMap();
        serviceDayMap.put("expertId", order.getExpertId());
        serviceDayMap.put("serviceDay", serviceDay);
        ExpertServiceDays expertServiceDays = (ExpertServiceDays)expertServiceDaysService.queryOne(serviceDayMap);
        if (expertServiceDays == null)
        {
            throw new BizException(ERRORCODE.EXPERT_SERVICE_TIME_N.getCode(),
                ERRORCODE.EXPERT_SERVICE_TIME_N.getMessage());
        }
        Map<String, Object> serviceTimeMap = new HashedMap();
        serviceTimeMap.put("expertDayId", expertServiceDays.getId());
        serviceTimeMap.put("timeSegment", serviceTime);
        serviceTimeMap.put("isAvailable", Constants.EXPERT_TIME_Y);
        ExpertServiceTimes expertServiceTimes = (ExpertServiceTimes)expertServiceTimesService.queryOne(serviceTimeMap);
        if (expertServiceTimes == null)
        {
            throw new BizException(ERRORCODE.EXPERT_SERVICE_TIME_N.getCode(),
                ERRORCODE.EXPERT_SERVICE_TIME_N.getMessage());
        }

        //更新专家时间状态
        expertServiceTimes.setIsAvailable(Constants.EXPERT_TIME_N + "");
        int updateCount = expertServiceTimesService.update(expertServiceTimes);
        if (updateCount == 0)
        {
            //更新状态鉴定
            //当更新结果为0说明该条记录已经被抢占不执行后续操作
            throw new BizException(ERRORCODE.EXPERT_SERVICE_TIME_N.getCode(),
                ERRORCODE.EXPERT_SERVICE_TIME_N.getMessage());
        }
        serviceTimeMap = new HashedMap();
        serviceTimeMap.put("expertDayId", expertServiceDays.getId());
        serviceTimeMap.put("isAvailable", Constants.EXPERT_TIME_Y);
        List<ExpertServiceTime> timeList = expertServiceTimesService.queryList(serviceTimeMap, "id", "asc");
        if (timeList == null || timeList.size() == 0)
        {
            //如果当前结果为空 那么僵对应的日期置为不可用
            expertServiceDays.setIsAvailable(Constants.EXPERT_TIME_N + "");
            expertServiceDaysService.update(expertServiceDays);
        }
        // 之所以先置时间状态是校验过后防止被抢占导致出错
        //设置预约时间
        String time = serviceDay + Constants.EXPERT_ORDER_BLANK + serviceTime;
        String endTime = serviceDay + Constants.EXPERT_ORDER_BLANK + serviceTime.split("~")[1];
        expertReservationOrderDetail.setExpectTime(time);
        expertReservationOrderDetail.setEndTime(endTime);
        //设置服务-1  解决潜在的会小于0的情况
        expertReservationOrderDetail.setServiceCount(count == 0 ? 0 : count - 1);

        return expertOrderDetailService.update(expertReservationOrderDetail) > 0;

    }

    /**
     * @return 返回字段  服务内容 剩余服务次数 预约状态 服务专家 预约时间 视频方式
     * 预约状态 0,未预约  1,预约成功  2,服务中  3,结束
     * 预约时间 格式 2016-11-2 12:00-13:00
     * 视频方式 微信/QQ
     * 评价 1,已评价 2,未评价
     */
    @RequestMapping(value = "queryExpertOrder")
    @ApiDesc(owner = "杨永平", value = "查询用户服列表")
    @ResponseBody
    public Object queryExpertOrder()
    {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String userId = this.getAccoutId();
        Long areaId = this.getAreaId();

        List<Long> cardIds = cardExService.getCard(Long.valueOf(userId));
        if (cardIds != null)
        {
            for (Long ll : cardIds)
            {
                Map<String, Object> map = new HashedMap();
                map.put("userId", userId);
                map.put("cardId", ll);
                Card card = (Card) cardService.fetch(ll);
                Integer count = cardExService.getVipServiceCount(card.getProductType(),areaId);
                if (count>0) {
                    map.put("areaId", areaId);
                }else {
                    map.put("areaId", 0);
                }

                //查询卡名称
                String cardName = null;

                DepartmentProductRelationPojo departmentProductRelationPojo = null;

                List<ExpertReservationOrderDetailDTO> expertReservationOrderDetailDTOs =
                    expertOrderExService.queryList(map, "id", "asc");
                /**
                 * 判定,如果是空的不进行后续操作直接返回,为空的原因可能是因为该用户未购买该卡
                 */
                if (expertReservationOrderDetailDTOs == null || expertReservationOrderDetailDTOs.size() == 0)
                {
                    continue;
                }
                //后续操作
                handlerOrder(expertReservationOrderDetailDTOs);
                Map<String, Object> serviceMap = new HashedMap();
                try
                {
                    departmentProductRelationPojo =
                        deparmentApiService.queryProductPriceByAreaId(areaId.toString(), card.getProductType());
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                cardName = departmentProductRelationPojo.getProductName();
                serviceMap.put("serviceName", cardName);
                serviceMap.put("serviceList", expertReservationOrderDetailDTOs);
                resultList.add(serviceMap);
            }
        }
        //判定订单状态
        return resultList;
    }

    private void handlerOrder(List<ExpertReservationOrderDetailDTO> list)
    {
        for (ExpertReservationOrderDetailDTO expertReservationOrderDetailDTO : list)
        {
            handlerOrderStatus(expertReservationOrderDetailDTO);
        }
    }

    /**
     * 处理订单状态 0未预约  1:预约成功  2:服务中 3:结束
     *
     * @param expertReservationOrderDetailDTO
     * @return
     */
    private void handlerOrderStatus(ExpertReservationOrderDetailDTO expertReservationOrderDetailDTO)
    {
        if (expertReservationOrderDetailDTO == null)
        {
            return;
        }
        if (expertReservationOrderDetailDTO.getEndTime() == null)
        {
            return;
        }
        String orderTime = expertReservationOrderDetailDTO.getExpectTime();
        String endTime = expertReservationOrderDetailDTO.getEndTime();
        String startTime = orderTime.substring(0, orderTime.lastIndexOf("~"));
        DateFormat dateFormat = new SimpleDateFormat(formatString);
        Long currTime = System.currentTimeMillis();
        Long lStartTime = 0L;
        try
        {
            lStartTime = dateFormat.parse(startTime).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Long lEndTime = 0L;
        try
        {
            lEndTime = dateFormat.parse(endTime).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Integer status = null;
        try
        {
            if (Constants.EXPERT_ORDER_STATUS_Y4 != expertReservationOrderDetailDTO.getStatus())
            {
                if (lStartTime != 0L && currTime - lStartTime < 0)
                {
                    //预约成功
                    status = Constants.EXPERT_ORDER_STATUS_Y1;
                }
                if (lStartTime != 0L && currTime - lStartTime > 0)
                {
                    //服务中
                    status = Constants.EXPERT_ORDER_STATUS_Y2;
                }
                if (lEndTime != 0L && currTime - lEndTime > 0)
                {
                    //结束
                    status = Constants.EXPERT_ORDER_STATUS_Y3;
                }
                if (lStartTime-tqTime-currTime<0 && lEndTime - currTime >0 && "智高考网站".equals(expertReservationOrderDetailDTO.getChannel())){
                    expertReservationOrderDetailDTO.setIsInto(2);
                }else {
                    expertReservationOrderDetailDTO.setIsInto(1);
                }
                Map<String, Object> map = new HashedMap();
                map.put("id", expertReservationOrderDetailDTO.getId());
                map.put("status", status);
                expertOrderDetailService.updateMap(map);
            }
            else
            {
                status = Constants.EXPERT_ORDER_STATUS_Y4;
            }
        }
        catch (Exception e)
        {
            return;
        }
        expertReservationOrderDetailDTO.setStatus(status);
    }

    /**
     * @return 返回字段  服务内容 剩余服务次数 预约状态 服务专家 预约时间 视频方式
     * 预约状态 0,未预约  1,预约成功  2,服务中  3,结束
     * 预约时间 格式 2016-11-2 12:00-13:00
     * 视频方式 微信/QQ
     * 评价 1,已评价 2,未评价
     */
    @RequestMapping(value = "updateExpertEvaluate")
    @ApiDesc(owner = "杨永平", value = "查询用户服列表")
    @ResponseBody
    public void updateExpertEvaluate(@RequestParam Integer orderId)
    {
        Map<String, Object> map = new HashedMap();
        map.put("status", Constants.EXPERT_ORDER_STATUS_Y4);
        map.put("id", orderId);
        expertOrderDetailService.updateMap(map);
    }
}
