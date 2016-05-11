package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.MatrixToImageWriter;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.query.OrdersQuery;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IOrdersService;
import cn.thinkjoy.gk.util.IPUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 智高考支付
 * Created by svenlau on 2016/5/11.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/orders")
public class OrdersController extends ZGKBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private IOrdersService ordersService;

    /**
     * 下订单
     *
     * @return
     */
    @RequestMapping(value = "createOrders")
    @ResponseBody
    public Map<String, String> createOrder(OrdersQuery ordersQuery) throws Exception {

        if (ordersQuery == null) {
            LOGGER.info("====pay /orders/createOrders PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Long userId = userAccountPojo.getId();
        String returnUrl = ordersQuery.getReturnUrl();
        RedisUtil.getInstance().set("pay_return_url_" + userId, returnUrl, 1l, TimeUnit.HOURS);
        //判断用户是否已经是VIP，如果已经是VIP则返回提示
        if (userAccountPojo.getVipStatus() == 1) {
            throw new BizException(ERRORCODE.VIP_EXIST.getCode(), ERRORCODE.VIP_EXIST.getMessage());
        }
        //判断用户是否已经申请VIP，如果已经已经VIP则返回提示
        if (userAccountPojo.getVipStatus() == 2) {
            throw new BizException("0100002", "该用户已经申请VIP了，请勿重复申请");
        }
        String orderNo = String.valueOf(System.currentTimeMillis()) + userId;
        String products = ordersQuery.getProducts();
        BigDecimal amount = BigDecimal.ZERO;
        amount = getAmount(products, amount);
        Orders order = getOrders(ordersQuery, userId, orderNo, products, amount);
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap.put("orderNo", order.getOrderNo());
            resultMap.put("amount", order.getAmount().toString());
            ordersService.insert(order);
            LOGGER.info("create orders :" + orderNo);
            return resultMap;
        } catch (Exception e) {
            LOGGER.info("====pay /orders/createOrder catch: " + e.getMessage());
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
    public String aliOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
                           @RequestParam(value = "token", required = true) String token) throws Exception {
        Orders order = (Orders) ordersService.findOne("orderNo", orderNo);
        if (null == order) {
            throw new BizException("0000010", "订单号无效!");
        }
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("channel", "alipay_pc_direct");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        Charge charge = getCharge(paramMap);
        String payResult = JSON.toJSONString(charge);
        LOGGER.info("====pay /orders/createOrder payResult: " + payResult);
        if (StringUtils.isNotBlank(payResult)) {
            return payResult;
        } else {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    /**
     * 微信支付
     *
     * @return
     */
    @RequestMapping(value = "wxOrderPay")
    public void wxOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
                        @RequestParam(value = "token", required = true) String token,
                        @RequestParam(value = "qrSize", required = false) String size, HttpServletResponse response) {
        Orders order = (Orders) ordersService.findOne("orderNo", orderNo);
        if (null == order) {
            throw new BizException("0000010", "订单号无效!");
        }
        int width = 200;
        int height = 200;
        if (null != size) {
            int qrSize;
            try {
                qrSize = Integer.parseInt(size);
            } catch (NumberFormatException e) {
                throw new BizException("0000010", "二维码大小错误!");
            }
            width = qrSize;
            height = qrSize;
        }
        try {
            getQrCode(orderNo, token, response, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BigDecimal getAmount(String products, BigDecimal amount) {
        JSONArray jsonArray = JSON.parseArray(products);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            BigDecimal count = new BigDecimal(obj.getIntValue("productNum"))
                    .setScale(0, BigDecimal.ROUND_DOWN);
            BigDecimal unitPrice = new BigDecimal(obj.getString("unitPrice"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            amount = amount.add(unitPrice.multiply(count));
        }
        return amount;
    }

    private Orders getOrders(OrdersQuery ordersQuery, Long userId, String orderNo, String products, BigDecimal amount) {
        Orders order = new Orders();
        order.setUserId(userId);
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
        order.setChannel("");
        return order;
    }

    private Charge getCharge(Map<String,String> paramMap) throws Exception {
        Pingpp.apiKey = DynConfigClientFactory.getClient().getConfig("common", "apiKey");
        String appid = DynConfigClientFactory.getClient().getConfig("common", "appId");
        Map<String, Object> chargeParams = new HashMap<>();
        String channel = paramMap.get("channel");
        Map<String, String> app = new HashMap<>();
        app.put("id", appid);
        chargeParams.put("order_no", paramMap.get("orderNo"));
        chargeParams.put("amount", 1);
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        chargeParams.put("client_ip", IPUtil.getRemortIP(request));
        chargeParams.put("subject", "智高考");
        chargeParams.put("body", "智高考VIP会员");
        chargeParams.put("currency", "cny");
        if ("alipay_pc_direct".equals(channel)) {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("success_url", "http://dev.service.zhigaokao.cn/payCallback.do?token=" + paramMap.get("token"));
            chargeParams.put("extra", extraMap);
        } else if ("wx_pub_qr".equals(channel)) {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("product_id", "1");
            chargeParams.put("extra", extraMap);
        }
        return Charge.create(chargeParams);
    }

    private void getQrCode(@RequestParam(value = "orderNo", required = true) String orderNo, @RequestParam(value = "token", required = true) String token, HttpServletResponse response, int width, int height) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map params = new HashMap();
        params.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("channel", "wx_pub_qr");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        Charge charge = getCharge(paramMap);
        String strCharge = JSON.toJSONString(charge);
        JSONObject obj = JSON.parseObject(strCharge);
        String credential = (String) obj.get("credential");
        JSONObject credentialObj = JSON.parseObject(credential);
        String url = (String) credentialObj.get("wx_pub_qr");
        BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, width, height, params);
        MatrixToImageWriter.writeToStream(bitMatrix, MatrixToImageWriter.FORMAT, response.getOutputStream());
    }

    /**
     * 订单详情查询
     *
     * @return
     */
    @RequestMapping(value = "getOrderDetail", method = RequestMethod.GET)
    public String getOrderDetail(HttpServletResponse response,
                                 @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 @RequestParam(value = "userAccount", required = false) String userAccount,
                                 @RequestParam(value = "orderNo", required = false) String orderNo) {
        Map<String, Object> paramMap = new HashMap<>();
        if (null != pageNo && null != pageSize) {
            paramMap.put("offset", pageNo * pageSize);
        }
        paramMap.put("rows", 10);
        paramMap.put("userAccount", userAccount);
        paramMap.put("orderNo", orderNo);
        List<Map<String, String>> resultList = ordersService.queryOrderDetail(paramMap);
        sendMessage(response, JSON.toJSONString(resultList), "Invoke method getOrderDetail failed!");
        return null;
    }

    /**
     * 各省订单销售总记录
     *
     * @param response
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "getOrderStatisticsData", method = RequestMethod.GET)
    public String getOrderStatisticsData(HttpServletResponse response, HashMap<String, Object> paramMap) {
        List<Map<String, String>> resultList = ordersService.queryOrderStatisticsData(paramMap);
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