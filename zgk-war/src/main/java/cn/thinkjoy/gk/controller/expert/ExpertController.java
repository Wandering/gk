package cn.thinkjoy.gk.controller.expert;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.Constants;
import cn.thinkjoy.gk.common.MatrixToImageWriter;
import cn.thinkjoy.gk.common.NumberGenUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.*;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IExpertApplyService;
import cn.thinkjoy.gk.service.IExpertService;
import cn.thinkjoy.gk.service.IOrderStatementsService;
import cn.thinkjoy.gk.service.impl.ProvinceServiceImpl;
import cn.thinkjoy.gk.util.IPUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by liusven on 2016/10/19.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert")
public class ExpertController extends ZGKBaseController
{
    //专家申请service
    @Autowired
    private IExpertApplyService expertApplyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private IExpertService expertService;

    @Autowired
    private IOrderStatementsService orderStatementService;

    @Autowired
    private ProvinceServiceImpl provinceServiceImp;
    //订单过期时间间隔2小时
    private final long expireDuration = 2 * 60 * 60 * 1000;
    /**
     * 下订单
     *
     * @return
     */
    @RequestMapping(value = "createOrders")
    @ResponseBody
    public Map<String, String> createOrder(@RequestParam(value = "token", required = true) String token
        , ExpertOrder expertOrder)
        throws Exception
    {
        if (expertOrder == null)
        {
            LOGGER.error("====pay /orders/createOrders PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null)
        {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        String returnUrl = expertOrder.getReturnUrl();
        Long userId = userAccountPojo.getId();
        RedisUtil.getInstance().set("pay_return_url_" + userId, returnUrl, 24l, TimeUnit.HOURS);
        expertOrder.setUserId(userId + "");
        ExpertOrder order = getOrder(expertOrder);
        Map<String, String> resultMap = new HashMap<>();
        try
        {
            resultMap.put("orderNo", order.getOrderNo());
            expertService.insertOrder(order);
            return resultMap;
        }
        catch (Exception e)
        {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    /**
     * 支付宝支付
     *
     * @return
     */
    @RequestMapping(value = "aliOrderPay")
    @ResponseBody
    public Charge aliOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
        @RequestParam(value = "token", required = true) String token)
    {
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null)
        {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        ExpertOrder order = getOrder(orderNo);
        String price = new BigDecimal(order.getServerPrice()).
            multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channel", "alipay_pc_direct");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        paramMap.put("amount", price);
        paramMap.put("productType", order.getServerType());
        Charge charge;
        try
        {
            charge = getCharge(paramMap);
        }
        catch (Exception e)
        {
            throw new BizException(ERRORCODE.FAIL.getCode(), e.getMessage());
        }
        return charge;
    }

    private ExpertOrder getOrder(String orderNo)
    {
        ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
        if (null == order)
        {
            throw new BizException("0000010", "订单号无效!");
        }
        return order;
    }

    /**
     * 微信支付
     *
     * @return
     */
    @RequestMapping(value = "wxOrderPay")
    public void wxOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
        @RequestParam(value = "token", required = true) String token,
        @RequestParam(value = "qrSize", required = false) String size, HttpServletResponse response)
    {
        int width = 200;
        int height = 200;
        if (null != size)
        {
            int qrSize;
            try
            {
                qrSize = Integer.parseInt(size);
                height = qrSize;
                width = qrSize;
            }
            catch (NumberFormatException e)
            {
                throw new BizException("0000010", "二维码大小错误!");
            }
        }
        try
        {
            getQrCode(orderNo, token, response, width, height);
        }
        catch (BizException e)
        {
            throw new BizException(e.getErrorCode(), e.getMsg());
        }
    }

    private ExpertOrder getOrder(ExpertOrder expertOrder)
    {
        String orderNo = NumberGenUtil.genOrderNo();
        expertOrder.setOrderNo(orderNo);
        expertOrder.setCreateDate(System.currentTimeMillis());
        expertOrder.setOrderStatus("0");
        return expertOrder;
    }

    private Charge getCharge(Map<String, String> paramMap)
        throws Exception
    {
        Pingpp.apiKey = DynConfigClientFactory.getClient().getConfig("common", "apiKey");
        String appid = DynConfigClientFactory.getClient().getConfig("common", "appId");
        String aliReturnUrl = DynConfigClientFactory.getClient().getConfig("common", "expertAliReturnUrl");
        Map<String, Object> chargeParams = new HashMap<>();
        Map<String, String> app = new HashMap<>();
        app.put("id", appid);
        String channel = paramMap.get("channel");
        String statemenstNo = NumberGenUtil.genOrderNo();
        chargeParams.put("order_no", statemenstNo);
        chargeParams.put("amount", paramMap.get("amount"));
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        chargeParams.put("client_ip", IPUtil.getRemortIP(request));
        chargeParams.put("subject", "智高考");
        chargeParams.put("body", "问专家");
        chargeParams.put("currency", "cny");
        if ("alipay_pc_direct".equals(channel))
        {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("success_url", aliReturnUrl + "?token=" + paramMap.get("token"));
            chargeParams.put("extra", extraMap);
        }
        else if ("wx_pub_qr".equals(channel))
        {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("product_id", "1");
            chargeParams.put("extra", extraMap);
        }
        createOrderStatement(paramMap, chargeParams, statemenstNo);
        return Charge.create(chargeParams);
    }

    /**
     * 创建交易流水
     *
     * @param paramMap
     * @param chargeParams
     */
    private void createOrderStatement(Map<String, String> paramMap, Map<String, Object> chargeParams,
        String statemenstNo)
    {
        OrderStatements orderstatement = new OrderStatements();
        orderstatement.setCreateDate(System.currentTimeMillis());
        orderstatement.setOrderNo(paramMap.get("orderNo"));
        orderstatement.setAmount(Double.parseDouble(paramMap.get("amount")));
        //0:交易进行中  1：交易成功  2：交易失败
        orderstatement.setStatementNo(statemenstNo);
        orderstatement.setStatus(0);
        orderstatement.setState("N");
        orderstatement.setPayJson(JSONObject.toJSONString(chargeParams));
        orderStatementService.insert(orderstatement);
    }

    private void getQrCode(String orderNo, String token, HttpServletResponse response, int width, int height)
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map params = new HashMap();
        params.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        ExpertOrder order = getOrder(orderNo);
        String price = new BigDecimal(order.getServerPrice()).
            multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channel", "wx_pub_qr");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        paramMap.put("amount", price);
        paramMap.put("productType", order.getServerType());
        Charge charge;
        try
        {
            charge = getCharge(paramMap);
        }
        catch (Exception e)
        {
            throw new BizException(ERRORCODE.FAIL.getCode(), e.getMessage());
        }
        String strCharge = JSON.toJSONString(charge);
        JSONObject obj = JSON.parseObject(strCharge);
        String credential = (String)obj.get("credential");
        JSONObject credentialObj = JSON.parseObject(credential);
        String url = (String)credentialObj.get("wx_pub_qr");
        BitMatrix bitMatrix;
        try
        {
            bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, width, height, params);
        }
        catch (WriterException e)
        {
            throw new BizException(ERRORCODE.FAIL.getCode(), "生成二维码错误!");
        }
        try
        {
            MatrixToImageWriter.writeToStream(bitMatrix, MatrixToImageWriter.FORMAT, response.getOutputStream());
        }
        catch (IOException e)
        {
            throw new BizException(ERRORCODE.FAIL.getCode(), "传输二维码错误!");
        }
    }

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
    public Map<String, Object> getExpertList(@RequestParam(value = "areaId",required = false)String areaId,
                                             @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                             @RequestParam(value = "rows", required = false, defaultValue = "10") String rows)
    {
        Map<String, Object> map = new HashMap<>();
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
        for (Map<String, Object> map: list)
        {
            String orderNo = map.get("orderNo") + "";
            ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
            checkExpire(order);
            map.put("orderStatus", order.getOrderStatus());
        }
        return list;
    }

    @RequestMapping("checkExpert")
    @ResponseBody
    public Map<String,Object> checkExpert(@RequestParam(value = "commonQuestionIdList")String commonQuestionIdList,
                                          @RequestParam(value="userId")String userId,
                                          @RequestParam(value="note",required = false)String note,
                                          @RequestParam(value="offset",required = false,defaultValue = "0")String offset,
                                          @RequestParam(value="rows",required = false,defaultValue = "2")String rows){
        List<ExpertInfoPojo> expertInfoPojoList=expertService.checkExpert(commonQuestionIdList,offset,rows,userId,note);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("expertInfoPojoList",expertInfoPojoList);
        return resultMap;
    }


    @RequestMapping("addUserQuestion")
    @ResponseBody
    public Map<String,Object> addUserQuestion(@RequestParam(value = "expertId")String expertId,
                                              @RequestParam(value = "userId")String userId,
                                              @RequestParam(value = "userName")String userName,
                                              @RequestParam(value = "userQuestion")String userQuestion){
        Map<String,Object> map=new HashMap<>();
        map.put("expertId",expertId);
        map.put("userId",userId);
        map.put("userName",userName);
        map.put("userQuestion",userQuestion);
        map.put("createDate",new Date());
        expertService.insertUserQuestion(map);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result","ok");
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
        if(null == revaluation)
        {
            orderRevaluation.setCreateDate(System.currentTimeMillis()+"");
            expertService.createExpertOrderRevaluation(orderRevaluation);
        }else
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
    public List<Map<String,Object>> getExpertOrderRevaluation(
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
        if(!order.getUserId().equals(getAccoutId()))
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

        if(null == order)
        {
            throw new BizException("1000111", "orderNo错误，未找到订单信息！");
        }

        if(!order.getUserId().equals(getAccoutId()))
        {
            throw new BizException("1000111", "token或orderNo错误参数错误！");
        }
        checkExpire(order);
        return order;
    }

    private void checkExpire(ExpertOrder order) {
        long createDate = order.getCreateDate();
        if("0".equals(order.getOrderStatus()+"") && System.currentTimeMillis() -  createDate > expireDuration)
        {
            //订单过期
            order.setOrderStatus("10");
            expertService.updateOrder(order);
        }
    }

    @RequestMapping(value = "getServiceByExpertId")
    @ResponseBody
    public Map<String,Object> getServiceByExpertId(@RequestParam("expertId")String expertId,@RequestParam(value = "areaId",required = false)String areaId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("expertId", expertId);
        paramMap.put("areaId", areaId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("service", expertService.selectServiceByExpertId(paramMap));
        return resultMap;
    }

    @RequestMapping(value = "getExpertServiceInfo")
    @ResponseBody
    public List<Map<String,Object>> getExpertServiceInfo(
        @RequestParam(value = "provinceCode", required = true) String provinceCode,
        @RequestParam(value = "expertId", required = true) String expertId)
    {
        Province province = provinceServiceImp.findOne("code", provinceCode);
        if(null == province)
        {
            throw new BizException("1100110","请输入正确的provinceCode!");
        }
        String areaId = province.getId() + "";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("areaId", areaId);
        paramMap.put("expertId", expertId);

        return expertService.getExpertServiceInfo(paramMap);
    }

    @RequestMapping(value = "getFamousTeacher")
    @ResponseBody
    public Map<String,Object> getFamousTeacher(@RequestParam(value = "paramName") String paramName,
                                               @RequestParam(value = "paramValue") String paramValue,
                                               @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                               @RequestParam(value = "rows", required = false, defaultValue = "4") String rows){
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
    @ApiDesc(owner = "hzuo",value = "导入数据临时使用")
    @ResponseBody
    public void test1(@RequestParam("expertName")String expertName,@RequestParam("specialityList")String specialityList){
        String[] specialityString=specialityList.split("、");
        List<Map<String,Object>> list=new ArrayList<>();
        for(String speciality:specialityString){
            Map<String,Object> specialityMap=new HashMap<>();
            specialityMap.put("speciality",speciality);
            list.add(specialityMap);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("expertName", expertName);
        map.put("list", list);
        expertService.test1(map);
    }

    @RequestMapping(value = "test2")
    @ApiDesc(owner = "hzuo",value = "导入数据临时使用")
    @ResponseBody
    public void test2(@RequestParam("expertName")String expertName,@RequestParam("provinceNameList")String provinceNameList,@RequestParam("serviceStyle")String serviceStyle,@RequestParam("serviceTypeList")String serviceTypeList){
        String[] provinceNameString=provinceNameList.split("、");
        for(String provinceName:provinceNameString) {
            String[] specialityString = serviceTypeList.split("、");
            List<Map<String, Object>> list = new ArrayList<>();
            for (String speciality : specialityString) {
                Map<String, Object> specialityMap = new HashMap<>();
                specialityMap.put("serviceType", speciality.split(":")[0]);
                specialityMap.put("servicePrice", speciality.split(":")[1]);
                list.add(specialityMap);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("expertName", expertName);
            map.put("provinceName", provinceName);
            map.put("serviceStyle", serviceStyle);
            map.put("list", list);
            expertService.test2(map);
        }
    }

    @ApiDesc(value = "查询专家可服务日期列表",owner = "杨国荣")
    @RequestMapping(value = "/getExpertServiceDays",method = RequestMethod.GET)
    @ResponseBody
    public List<ExpertServiceDay> getExpertServiceDays(@RequestParam("expertId") int expertId){
        return expertService.getExpertServiceDays(expertId);
    }

    @ApiDesc(value = "查询专家某天可服务的时间段列表",owner = "杨国荣")
    @RequestMapping(value = "/getExpertServiceTimes",method = RequestMethod.GET)
    @ResponseBody
    public List<ExpertServiceTime> getExpertServiceTimes(@RequestParam("dayId") int dayId){
        return expertService.getExpertServiceTimes(dayId);
    }

}
