package cn.thinkjoy.gk.controller;

import cn.thinkjoy.cloudstack.context.CloudContextFactory;
import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.*;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.*;
import cn.thinkjoy.gk.query.OrdersQuery;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import cn.thinkjoy.gk.util.IPUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.sms.api.SMSService;
import cn.thinkjoy.sms.domain.SMSSendVipCard;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentProductRelationPojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.StringUtils;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private SMSService smsService;

    @Autowired
    private IOrderStatementsService orderStatementService;
    @Autowired
    private IUserAccountExService userAccountExService;

    @Autowired
    private IProductService productService;

    @Autowired
    private DeparmentApiService deparmentApiService;

    @Autowired
    private IUserGoodsAdressService userGoodsAdressService;

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
            LOGGER.error("====pay /orders/createOrders PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Long userId = userAccountPojo.getId();
        //返回的地址
        String returnUrl = ordersQuery.getReturnUrl();
        //redis缓存返回url
        RedisUtil.getInstance().set("pay_return_url_" + userId, returnUrl, 24l, TimeUnit.HOURS);
        //生成订单序号
        String orderNo = String.valueOf(System.currentTimeMillis()) + userId;
        //获取产品(状元及第/金榜题名)
        String products = ordersQuery.getProducts();
        //获取商品信息
        Order order = getOrder(userId, products);
        //保存手机号码
        order.setPhone(ordersQuery.getPhone());
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap.put("orderNo", order.getOrderNo());
            //保存订单信息
            orderService.insert(order);
            LOGGER.info("create orders :" + orderNo);
            return resultMap;
        } catch (Exception e) {
            LOGGER.error("====pay /orders/createOrder catch: " + e.getMessage());
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    public void paySuccessCallback(@RequestParam(value = "orderNo", required = true) String orderNo){
        Map<String,Object> resultMap = new HashedMap();
        //根据订单号获取订单信息
        Order order = getOrder(orderNo);
        /*判断交易状态(交易未支付等其他状态直接return,交易已支付判断交易是否已经生成过账号密码,
        * 初次支付成功,一定未生成卡号)
        */
        Card card = null;
        order.setUpdateDate(System.currentTimeMillis());
        try {
            if (PayEnum.SUCCESS.getCode().equals(order.getStatus())) {
                //支付成功
                //判断用户发货状态
                if (CardHandleStateEnum.N.getCode().toString().equals(order.getHandleState())) {
                    //没有发货状态
                    /**
                     * 生成vip卡号
                     */
                    try {
                        card = orderService.singleCreateCard(Integer.valueOf(order.getProductType()));
                    }catch (Exception e){
                        //判定异常,唯一性约束冲突,尝试重新生成卡号
                        card = orderService.singleCreateCard(Integer.valueOf(order.getProductType()));
                    }
                    //HandleState 0:未发货 1:已发货
                    order.setHandleState(CardHandleStateEnum.Y.getCode().toString());
                    //从insert回调中取得cardId做关联
                    order.setCardId(card.getId());
                    resultMap.put("cardNumber",card.getCardNumber());
                    resultMap.put("password",card.getPassword());
                    resultMap.put("phone",order.getPhone());
                    //TODO 发送短信
                    try {
                        smsService.sendVipCard(new SMSSendVipCard(order.getPhone(),card.getCardNumber(),card.getPassword(), CloudContextFactory.getCloudContext().getApplicationName()));

                    }catch (Exception e){
                        LOGGER.info("发送短信失败:"+"phone:"+order.getPhone()+" card:"+card.getCardNumber()+" password:"+card.getPassword());
                    }
                }
//                else {
//                    //已经发货状态
//                    //取得当前用户该订单的卡号
//                    Map<String,Object> orderMap = orderService.getCardByUidAndNo(userAccountPojo.getId(),orderNo);
//                    resultMap.put("cardNumber",orderMap.get("cardNumber"));
//                    resultMap.put("password",orderMap.get("password"));
//                    resultMap.put("phone",order.getPhone());
//                }
            }else {
                LOGGER.info("生成卡号失败-订单号:"+orderNo);
                throw new BizException(ERRORCODE.ORDER_PAY_FAIL.getCode(),ERRORCODE.ORDER_PAY_FAIL.getMessage());
            }
        }finally {
            //更新订单状态
            orderService.update(order);
        }

        //返回vip卡号和密码
//        return resultMap;
    }
    /**
     * 支付宝支付
     *
     * @return
     */
    @RequestMapping(value = "paySuccess")
    @ResponseBody
    public Map<String,Object> paySuccess(@RequestParam(value = "orderNo", required = true) String orderNo,
                           @RequestParam(value = "token", required = true) String token){
        Map<String,Object> resultMap = new HashedMap();



        //获取用户信息
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        //根据订单号获取订单信息
        Order order = getOrder(orderNo);
        /*判断交易状态(交易未支付等其他状态直接return,交易已支付判断交易是否已经生成过账号密码,
        * 初次支付成功,一定未生成卡号)
        */


        Card card = null;
        order.setUpdateDate(System.currentTimeMillis());
            if (PayEnum.SUCCESS.getCode().equals(order.getStatus())) {
                while ("0".equals(order.getHandleState())){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    order = getOrder(orderNo);
                }
                //支付成功
                    //已经发货状态
                    //取得当前用户该订单的卡号
                    Map<String,Object> orderMap = orderService.getCardByUidAndNo(userAccountPojo.getId(),orderNo);
                    resultMap.put("cardNumber",orderMap.get("cardNumber"));
                    resultMap.put("password",orderMap.get("password"));
                    resultMap.put("phone",order.getPhone());
            }else {
                throw new BizException(ERRORCODE.ORDER_PAY_FAIL.getCode(),ERRORCODE.ORDER_PAY_FAIL.getMessage());
            }
        //返回vip卡号和密码
        return resultMap;
    }

    /**
     * 支付宝支付
     *
     * @return
     */
    @RequestMapping(value = "aliOrderPay")
    @ResponseBody
    public Charge aliOrder(@RequestParam(value = "orderNo", required = true) String orderNo,
                           @RequestParam(value = "token", required = true) String token,
                            @RequestParam(value = "channel", required = false) String channel){
        String payChannel = "alipay_pc_direct";
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(channel))
        {
            payChannel = channel;
        }
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Order order = getOrder(orderNo);
        String price = new BigDecimal(order.getProductPrice()).
                multiply(new BigDecimal(100)).setScale(0 , BigDecimal.ROUND_HALF_EVEN).toString();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("channel", payChannel);
        paramMap.put("orderNo", orderNo);
        paramMap.put("token", token);
        paramMap.put("amount", price);
        paramMap.put("productType", order.getProductType());
        Charge charge;
        try {
            charge = getCharge(paramMap);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
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

    private Order getOrder(Long userId, String products) {
        String orderNo= NumberGenUtil.genOrderNo();
        Order order=new Order();
        //来源:0微信,1web
        order.setChannle(1);
        order.setCreateDate(System.currentTimeMillis());
        order.setOrderNo(orderNo);
        UserGoodsAdress userGoodsAdress = (UserGoodsAdress) userGoodsAdressService.findOne("userId",userId);
        if(null != userGoodsAdress)
        {
            order.setGoodsAddress(userGoodsAdress.getReceivingAddress());
            order.setDepartmentName(userGoodsAdress.getContactName());
            order.setDepartmentPhone(userGoodsAdress.getContactPhone());
        }
        order.setUserId(userId);
        order.setStatus(0);
        JSONArray jsonArray = JSON.parseArray(products);
        if(jsonArray.size() >= 1)
        {
            JSONObject obj = jsonArray.getJSONObject(0);
            String productId = obj.getString("productId");
            Product product = (Product) productService.findOne("id", productId);
            if(null == product)
            {
                throw new BizException("0000007", "无效的产品code!");
            }
            order.setProductType(productId);
            BigDecimal count = new BigDecimal(obj.getIntValue("productNum")).setScale(0, BigDecimal.ROUND_DOWN);
            BigDecimal salePrice = getSalePrice(productId);
            if(salePrice.toString().equals(BigDecimal.ZERO.toString()))
            {
               throw new BizException("1000111", "未找到匹配的产品类型!");
            }
            order.setUnitPrice(salePrice.toString());
            order.setGoodsCount(count.intValue());
            order.setProductPrice(salePrice.multiply(count).toString());
        }else {
            throw new BizException("1000112", "输入信息有误!");
        }
        return order;
    }

    private BigDecimal getSalePrice(String productId) {
        BigDecimal salePrice = BigDecimal.ZERO;
        List<DepartmentProductRelationPojo > relations = null;
        try {
            relations = deparmentApiService.queryProductPriceByAreaId(getAreaId().toString());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (DepartmentProductRelationPojo relation: relations) {
            if(productId.equals(relation.getProductId().toString()))
            {
                salePrice = new BigDecimal(relation.getSalePrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return salePrice;
    }

    private Charge getCharge(Map<String, String> paramMap) throws Exception {
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
            extraMap.put("success_url", aliReturnUrl+"?token=" + paramMap.get("token"));
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
    public Map<String, Object> getOrderDetail(@RequestParam(value = "orderNo", required = true)String orderNo,
                                              @RequestParam(value = "token", required = true)String token)
    {
        String userId = getAccoutId();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("orderNo", orderNo+"");
        List<Map<String, Object>> orderList = userAccountExService.getOrderList(paramMap);
        if (null == orderList || orderList.size()==0) {
            throw new BizException("0000010", "订单号或token无效!");
        }
        fixOrderList(orderList);
        return orderList.get(0);
    }


    /**
     * 获取订单列表
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getOrderList")
    @Deprecated
    public List<Map<String, Object>> getOrderList(@RequestParam(value = "token", required = true)String token,@RequestParam(required = false)String more)
    {
        String userId = getAccoutId();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        if(more==null){
            paramMap.put("limit", String.valueOf(MarketConstants.ORDER_LIMIT));
        }
        List<Map<String, Object>> orderList = userAccountExService.getOrderList(paramMap);
        fixOrderList(orderList);
        return orderList;
    }




    private void fixOrderList(List<Map<String, Object>> orderList) {
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
                //标示已发货状态
                if("1".equals(order.get("payStatus") + "") && "1".equals(order.get("handleState") + ""))
                {
                    order.put("payStatus", PayEnum.PAY_SUCCESS.getCode());
                }
            }
        }
    }

    private void checkExpire(Order order) {
        long createDate = order.getCreateDate();
        if("0".equals(order.getStatus()+"") && System.currentTimeMillis() -  createDate > expireDuration)
        {
            //订单过期
            order.setStatus(PayEnum.PAY_TIME_OUT.getCode());
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
            order.setState("Y");
            orderService.update(order);
            result = true;
        }else {
            throw new BizException("0000010", "token无效");
        }
        return result;
    }

}
