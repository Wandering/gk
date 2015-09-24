package cn.thinkjoy.gk.controller.market;

/**
 * Created by clei on 15/5/4.
 */

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.controller.market.query.OrdersQuery;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 订单
 * Created by clei on 15/4/7.
 */
@Controller
@RequestMapping(value="/orders")
public class OrdersController extends BaseController{

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

        String userId = getCookieValue();

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
        order.setUserId(Long.valueOf(1));
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
        order.setChannel(ordersQuery.getChannel());
        try {
            ordersService.insert(order);
            LOGGER.info("creaate order :" + orderNo);

            String payResult  = createPay(ordersQuery,orderNo,order,validValue.longValue());

            LOGGER.info("====pay /orders/createOrder payResult: "+payResult);

            if(StringUtils.isNotBlank(payResult)){
                return payResult;
            }else {
                throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
            }
        }catch (Exception e){
            LOGGER.info("====pay /orders/createOrder catch: "+e.getMessage());
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    public String createPay(OrdersQuery orderQuery,String orderNo,Orders order,Long validTime) throws Exception{
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
        metadata.put("validTime",validTime);
        chargeParams.put("metadata", JSON.toJSONString(metadata));
        chargeParams.put("moduleKey",moduleKey);
        try {

            LOGGER.info("====pay /orders/createOrder 请求JSON: "+JSON.toJSONString(chargeParams));

            String payResult = HttpRequestUtil.doHttpPost(url, chargeParams);
            if(StringUtils.isNotBlank(payResult)){
                JSONObject obj = JSON.parseObject(payResult);
                return obj.getString("bizData");
            }else {
                return null;
            }
        }catch (Exception e){
            LOGGER.info(" create order success ,create pay error ");
            return null;
        }

    }

}