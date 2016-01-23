package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.query.OrdersQuery;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IOrdersService;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import cn.thinkjoy.gk.util.IPUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * Created by clei on 15/4/7.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value="/orders")
public class OrdersController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private IOrdersService ordersService;

    /**
     * 下订单
     * @return
     */
    @RequestMapping(value = "createOrders", method = RequestMethod.POST)
    @ResponseBody
    public String createOrder(OrdersQuery ordersQuery) {

        if(ordersQuery==null) {
            LOGGER.info("====pay /orders/createOrders PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Long userId = userAccountPojo.getId();

        //判断用户是否已经是VIP，如果已经是VIP则返回提示
        if (userAccountPojo.getVipStatus()==1){
            throw new BizException(ERRORCODE.VIP_EXIST.getCode(),ERRORCODE.VIP_EXIST.getMessage());
        }
        //判断用户是否已经申请VIP，如果已经已经VIP则返回提示
        if (userAccountPojo.getVipStatus()==2){
            throw new BizException("0100002","该用户已经申请VIP了，请勿重复申请");
        }

        String orderNo = String.valueOf(System.currentTimeMillis())+userId;

        String products = ordersQuery.getProducts();

        BigDecimal validValue = BigDecimal.ZERO;

        BigDecimal amount = BigDecimal.ZERO;

        JSONArray jsonArray = JSON.parseArray(products);

        for(int i =0;i<jsonArray.size();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            BigDecimal unitPrice = new BigDecimal(obj.getString("unitPrice"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal time = new BigDecimal(obj.getString("validValue"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal count = new BigDecimal(obj.getIntValue("productNum"))
                    .setScale(0, BigDecimal.ROUND_DOWN);

            amount = amount.add(unitPrice.multiply(count));

            validValue = validValue.add(time.multiply(count));
        }
//        Iterator<Object> iterator = jsonArray.iterator();
//        while (iterator.hasNext()) {
//            JSONObject jsonObject = (JSONObject) iterator.next();
//            BigDecimal unitPrice = new BigDecimal(jsonObject.getString("unitPrice"))
//                    .setScale(2, BigDecimal.ROUND_HALF_UP);
//            BigDecimal count = new BigDecimal(jsonObject.getIntValue("productNum"))
//                    .setScale(0, BigDecimal.ROUND_DOWN);
//            amount = amount.add(unitPrice.multiply(count));
//        }

        Orders order = new Orders();
        order.setUserId(userId);
//        order.setUserId(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setDetail(products);
        order.setAmount(amount);
        long now = System.currentTimeMillis();
        order.setCreateDate(now);
        order.setLastModDate(now);
        order.setInvalidDate(0l);
        order.setStatus(0);
        order.setPayStatus(0);
        order.setDescription(ordersQuery.getExtra());
//        order.setChannel(ordersQuery.getChannel());
        order.setChannel(ordersQuery.getChannel());
        try {
            ordersService.insert(order);
            LOGGER.info("create orders :" + orderNo);

            String payResult  = createPay(ordersQuery, orderNo, order, userAccountPojo.getAccount());

            LOGGER.info("====pay /orders/createOrder payResult: "+payResult);

            if(StringUtils.isNotBlank(payResult)){
                return payResult;
            }else {
                throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
            }
        } catch (Exception e){
            LOGGER.info("====pay /orders/createOrder catch: "+e.getMessage());
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    public String createPay(OrdersQuery orderQuery,String orderNo,Orders order,String account) throws Exception{
        String moduleKey = DynConfigClientFactory.getClient().getConfig("common", "moduleKey");

        String url = DynConfigClientFactory.getClient().getConfig("common", "payUrl");
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("orderNo", orderNo);
        chargeParams.put("amount", String.valueOf(order.getAmount()));
        chargeParams.put("channel",  orderQuery.getChannel());
        chargeParams.put("currency", "cny");
        chargeParams.put("remoteIp", IPUtil.getRemortIP(request));
        chargeParams.put("extra",orderQuery.getExtra());

        JSONArray jsonArray = JSON.parseArray(order.getDetail());
        StringBuilder subject = new StringBuilder();
        StringBuilder body = new StringBuilder();

        Iterator<Object> iter = jsonArray.iterator();
        while (iter.hasNext()) {
            JSONObject json = (JSONObject) iter.next();
            subject.append(json.getString("productCode")).append("|");
            body.append(json.getString("productCode")).append("^")
                    .append(json.getString("productNum")).append("|");
        }

        chargeParams.put("subject",  subject.toString().substring(0,subject.toString().length()-1));
        chargeParams.put("body",  body.toString().substring(0,body.toString().length()-1));
        Map<String,Object> metadata = new HashMap<String,Object>();
        metadata.put("orderNo",orderNo);
//        metadata.put("month", calculateMonth(body.toString().substring(0,body.toString().length()-1)));
        metadata.put("userId", order.getUserId());
        metadata.put("account",account);
        chargeParams.put("metadata", JSON.toJSONString(metadata));
        chargeParams.put("moduleKey",moduleKey);
        try {

            LOGGER.info("====pay /orders/createOrder 请求JSON: "+JSON.toJSONString(chargeParams));

            String payResult = HttpRequestUtil.doHttpPost(url, chargeParams);
            if(StringUtils.isNotBlank(payResult)){
                JSONObject obj = JSON.parseObject(payResult);
                return obj.getString("bizData");
            } else {
                return null;
            }
        }catch (Exception e){
            LOGGER.info(" create order success ,create pay error ");
            return null;
        }

    }

    /**
     * 订单详情查询
     * @param response
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "getOrderDetail", method = RequestMethod.GET)
    public String getOrderDetail(HttpServletResponse response,
         @RequestParam(value="pageNo",required=false) Integer pageNo,
         @RequestParam(value="pageSize",required=false) Integer pageSize,
         @RequestParam(value="userAccount",required=false) String userAccount,
         @RequestParam(value="orderNo",required=false) String orderNo)
    {
        Map<String, Object> paramMap = new HashMap<>();
        if(null != pageNo && null != pageSize) {
            paramMap.put("offset", pageNo * pageSize);
        }
        paramMap.put("rows", 10);
        paramMap.put("userAccount", userAccount);
        paramMap.put("orderNo", orderNo);
        List<Map<String,String>> resultList = ordersService.queryOrderDetail(paramMap);
        sendMessage(response, JSON.toJSONString(resultList), "Invoke method getOrderDetail failed!");
        return null;
    }

    /**
     * 各省订单销售总记录
     * @param response
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "getOrderStatisticsData", method = RequestMethod.GET)
    public String getOrderStatisticsData(HttpServletResponse response, HashMap<String, Object> paramMap) {
        List<Map<String,String>> resultList = ordersService.queryOrderStatisticsData(paramMap);
        sendMessage(response, JSON.toJSONString(resultList), "Invoke method getOrderStatisticsData failed!");
        return null;
    }

    private void sendMessage(HttpServletResponse response, String msg, String errorMsg) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(msg);
        } catch (Exception e) {
            if (null != pw) {
                pw.write("Error!");
            }
            LOGGER.error(errorMsg);
        } finally {
            if (null != pw) {
                pw.close();
            }
        }
    }
}