package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.MatrixToImageWriter;
import cn.thinkjoy.gk.common.NumberGenUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.*;
import cn.thinkjoy.gk.query.OrdersQuery;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import cn.thinkjoy.gk.util.IPUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.DoubleArraySerializer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private IOrderService orderService;
    @Autowired
    private IOrderStatementsService orderStatementService;
    @Autowired
    private IUserAccountExService userAccountExService;

    @Autowired
    private IProductService productService;

    //订单过期时间间隔2小时
    private final long expireDuration = 2 * 60 * 60 * 1000;
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
        RedisUtil.getInstance().set("pay_return_url_" + userId, returnUrl, 24l, TimeUnit.HOURS);
        String orderNo = String.valueOf(System.currentTimeMillis()) + userId;
        String products = ordersQuery.getProducts();
        BigDecimal amount = BigDecimal.ZERO;
        amount = getAmount(products, amount);
        Order order = getOrder(userId, products, amount);
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap.put("orderNo", order.getOrderNo());
            orderService.insert(order);
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
    public Charge aliOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
                           @RequestParam(value = "token", required = true) String token){
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Order order = getOrder(orderNo);
        String price = new BigDecimal(order.getProductPrice()).
                multiply(new BigDecimal(100)).setScale(0 , BigDecimal.ROUND_HALF_EVEN).toString();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("channel", "alipay_pc_direct");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        paramMap.put("amount", price);
        paramMap.put("productType", order.getProductType());
        Charge charge;
        try {
            charge = getCharge(paramMap);
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), e.getMessage());
        }
        return charge;
    }

    private Order getOrder(String orderNo) {
        Order order = (Order) orderService.findOne("order_no", orderNo);
        if (null == order) {
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
                        @RequestParam(value = "qrSize", required = false) String size, HttpServletResponse response) {
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
        } catch (BizException e) {
            throw new BizException(e.getErrorCode(), e.getMsg());
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

    private Order getOrder(Long userId, String products, BigDecimal amount) {
        String orderNo= NumberGenUtil.genOrderNo();
        Order order=new Order();
        Department  department= getDepartment();
        order.setCreateDate(System.currentTimeMillis());
        order.setOrderNo(orderNo);
        if(null != department)
        {
            order.setDepartmentName(department.getDepartmentName());
            order.setDepartmentCode(department.getDepartmentCode());
            order.setDepartmentPhone(department.getDepartmentPhone());
            order.setGoodsAddress(department.getGoodsAddress());
            //来源:0微信,1web
            order.setChannle(1);
        }
        order.setProductPrice(amount.toString());
        order.setUserId(userId);
        order.setStatus(0);
        JSONArray jsonArray = JSON.parseArray(products);
        if(jsonArray.size() >= 1)
        {
            JSONObject obj = jsonArray.getJSONObject(0);
            String productCode = obj.getString("productCode");
            Product product = (Product) productService.findOne("code", productCode);
            if(null == product)
            {
                throw new BizException("0000007", "无效的产品code!");
            }
            order.setProductType(product.getType() + "");
            order.setGoodsCount(obj.getIntValue("productNum"));
        }
        return order;
    }

    private Charge getCharge(Map<String,String> paramMap) throws Exception {
        Pingpp.apiKey = DynConfigClientFactory.getClient().getConfig("common", "apiKey");
        String appid = DynConfigClientFactory.getClient().getConfig("common", "appId");
        String aliReturnUrl = DynConfigClientFactory.getClient().getConfig("common", "aliReturnUrl");
        Map<String, Object> chargeParams = new HashMap<>();
        String channel = paramMap.get("channel");
        String productType = paramMap.get("productType");
        Product product = (Product)productService.findOne("type", productType);
        if(null == product)
        {
            throw new BizException("0000007", "无效的产品类型!");
        }
        Map<String, String> app = new HashMap<>();
        app.put("id", appid);
        String statemenstNo = NumberGenUtil.genOrderNo();
        chargeParams.put("order_no", statemenstNo);
        chargeParams.put("amount", paramMap.get("amount"));
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        chargeParams.put("client_ip", IPUtil.getRemortIP(request));
        chargeParams.put("subject", product.getName());
        chargeParams.put("body", product.getIntro());
        chargeParams.put("currency", "cny");
        if ("alipay_pc_direct".equals(channel)) {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("success_url", aliReturnUrl+"?token="+paramMap.get("token"));
            chargeParams.put("extra", extraMap);
        } else if ("wx_pub_qr".equals(channel)) {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("product_id", productType);
            chargeParams.put("extra", extraMap);
        }
        createOrderStatement(paramMap, chargeParams, statemenstNo);
        return Charge.create(chargeParams);
    }

    /**
     * 创建交易流水
     * @param paramMap
     * @param chargeParams
     */
    private void createOrderStatement(Map<String, String> paramMap, Map<String, Object> chargeParams, String statemenstNo ) {
        OrderStatements orderstatement=new OrderStatements();
        orderstatement.setAmount(Double.parseDouble(paramMap.get("amount")));
        orderstatement.setCreateDate(System.currentTimeMillis());
        orderstatement.setOrderNo(paramMap.get("orderNo"));
        //0:交易进行中  1：交易成功  2：交易失败
        orderstatement.setStatus(0);
        orderstatement.setStatementNo(statemenstNo);
        orderstatement.setState("N");
        orderstatement.setPayJson(JSONObject.toJSONString(chargeParams));
        orderStatementService.insert(orderstatement);
    }

    private void getQrCode(String orderNo, String token, HttpServletResponse response, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map params = new HashMap();
        params.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        Order order = getOrder(orderNo);
        String price = new BigDecimal(order.getProductPrice()).
                multiply(new BigDecimal(100)).setScale(0 , BigDecimal.ROUND_HALF_EVEN).toString();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("channel", "wx_pub_qr");
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        paramMap.put("amount", price);
        paramMap.put("productType", order.getProductType());
        Charge charge;
        try {
            charge = getCharge(paramMap);
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), e.getMessage());
        }
        String strCharge = JSON.toJSONString(charge);
        JSONObject obj = JSON.parseObject(strCharge);
        String credential = (String) obj.get("credential");
        JSONObject credentialObj = JSON.parseObject(credential);
        String url = (String) credentialObj.get("wx_pub_qr");
        BitMatrix bitMatrix;
        try {
            bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, width, height, params);
        } catch (WriterException e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), "生成二维码错误!");
        }
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, MatrixToImageWriter.FORMAT, response.getOutputStream());
        } catch (IOException e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), "传输二维码错误!");
        }
    }

    /**
     * 下订单时获取离用户最近的取货代理商信息
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getAgentInfo")
    public Department getAgentInfo(@RequestParam(value = "token", required = true)String token)
    {
        return getDepartment();
    }

    private Department getDepartment() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("accountId", getUserAccountPojo().getId()+"");
        Map<String, Object> userAccountMap =  userAccountExService.findUserInfo(paramMap);
        if(null == userAccountMap)
        {
            throw new BizException("100001", "帐号ID错误!");
        }
        Object countyId = userAccountMap.get("countyId");
        Object cityId = userAccountMap.get("cityId");
        Object provinceId = userAccountMap.get("provinceId");
        Department countyDepartment;
        Department cityDepartment;
        Department provinceDepartment;
        Map<String, String> params = new HashMap<>();
        if(isValideAreaId(countyId))
        {
            String countyIdStr = String.valueOf(countyId);
            params.put("areaCode", countyIdStr);
            countyDepartment =  userAccountExService.findDepartMent(params);
            if(null != countyDepartment)
            {
                return fixReturnValue(countyDepartment);
            }
            params.put("areaCode", countyIdStr.substring(0,4));
            cityDepartment =  userAccountExService.findDepartMent(params);
            if(null != cityDepartment)
            {
                return fixReturnValue(cityDepartment);
            }
            params.put("areaCode", countyIdStr.substring(0,2));
            provinceDepartment =  userAccountExService.findDepartMent(params);
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }else if(isValideAreaId(cityId))
        {
            String cityIdStr = String.valueOf(cityId);
            params.put("areaCode", cityIdStr.substring(0,4));
            cityDepartment =  userAccountExService.findDepartMent(params);
            if(null != cityDepartment)
            {
                return fixReturnValue(cityDepartment);
            }
            params.put("areaCode", cityIdStr.substring(0,2));
            provinceDepartment =  userAccountExService.findDepartMent(params);
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }else if(isValideAreaId(provinceId))
        {
            String provinceIdStr = String.valueOf(provinceId);
            params.put("areaCode", provinceIdStr.substring(0,2));
            provinceDepartment =  userAccountExService.findDepartMent(params);
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }
        throw new BizException("100001", "未查找到相关代理商,用户区域信息有误!");
    }

    private Department fixReturnValue(Department countyDepartment) {
        countyDepartment.setCompanyCode(null);
        countyDepartment.setDepartmentPrincipal(null);
        countyDepartment.setDescription(null);
        countyDepartment.setParentCode(null);
        countyDepartment.setSeqSort(null);
        countyDepartment.setCreator(null);
        countyDepartment.setRoleType(null);
        countyDepartment.setCreateDate(null);
        countyDepartment.setCreatorName(null);
        countyDepartment.setLastModDate(null);
        countyDepartment.setLastModifier(null);
        countyDepartment.setLastModifierName(null);
        countyDepartment.setStatus(null);
        countyDepartment.setAreaCode(null);
        countyDepartment.setDepartmentFax(null);
        return countyDepartment;
    }

    private boolean isValideAreaId(Object countyId) {
        return null != countyId && !"00".equals(countyId) && String.valueOf(countyId).length() == 6;
    }

    /**
     * 获取订单详情
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getOrderInfo")
    public Map<String, String> getOrderDetail(@RequestParam(value = "orderNo", required = true)String orderNo,
                                              @RequestParam(value = "token", required = true)String token)
    {
        Order order = (Order) orderService.findOne("order_no", orderNo);
        if (null == order) {
            throw new BizException("0000010", "订单号无效!");
        }

        checkExpire(order);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        String amount = order.getProductPrice();
        if(null != amount)
        {
            resultMap.put("amount", amount);
        }else
        {
            resultMap.put("amount", "未知");
        }
        resultMap.put("createTime", order.getCreateDate().toString());
        resultMap.put("payStatus",order.getStatus().toString());
        resultMap.put("productNum", order.getGoodsCount().toString());
        String productType = order.getProductType();
        if(null != productType)
        {
            Product product = (Product)productService.findOne("type", productType);
            if(null != product)
            {
                resultMap.put("productName", product.getName());
                resultMap.put("unitPrice",  product.getMarketPrice());
            }
        }
//        resultMap.put("goodsAddress", jsonObject.getString("goodsAddress"));
//        resultMap.put("contactPhoneNumber", jsonObject.getString("contactPhoneNumber"));
        return resultMap;
    }

    /**
     * 获取订单列表
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getOrderList")
    public List<Map<String, Object>> getOrderList(@RequestParam(value = "token", required = true)String token)
    {
        long userId = getUserAccountPojo().getId();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId+"");
        List<Map<String, Object>> orderList = userAccountExService.getOrderList(paramMap);
        if(null != orderList && orderList.size()>0)
        {
            for (Map<String, Object> order: orderList) {
                if("0".equals(order.get("payStatus")+""))
                {
                    String orderNo = order.get("orderNo") + "";
                    Order ord = (Order) orderService.findOne("order_no", orderNo);
                    if(null != ord)
                    {
                        checkExpire(ord);
                        order.put("payStatus", ord.getStatus());
                    }
                }
            }
        }
        return orderList;
    }

    private void checkExpire(Order order) {
        long createDate = order.getCreateDate();
        if("0".equals(order.getStatus()+"") && System.currentTimeMillis() -  createDate > expireDuration)
        {
            //订单过期
            order.setStatus(2);
            orderService.update(order);
        }
    }

    /**
     * 获取订单列表
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value="removeOrder")
    public boolean removeOrder(@RequestParam(value = "token", required = true)String token,
                                                 @RequestParam(value = "orderNo", required = true)String orderNo)
    {
        boolean result;
        Order order = (Order) orderService.findOne("order_no", orderNo);
        if (null == order) {
            throw new BizException("0000010", "订单号无效!");
        }
        long userId = getUserAccountPojo().getId();
        if(userId == order.getUserId())
        {
            //逻辑删除订单
            order.setStatus(-1);
            orderService.update(order);
            result = true;
        }else {
            throw new BizException("0000010", "token无效");
        }
        return result;
    }

}