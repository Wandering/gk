package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.*;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.query.OrdersQuery;
import cn.thinkjoy.gk.service.IOrdersService;
import cn.thinkjoy.gk.util.HttpRequestUtil;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 订单
 * Created by clei on 15/4/7.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value="/ordersNew")
public class OrdersControllerNew extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrdersControllerNew.class);

    @Autowired
    private IOrdersService ordersService;

    /**
     * 下订单
     * @return
     */
    @RequestMapping(value = "createOrders")
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
        String returnUrl = ordersQuery.getReturnUrl();

        RedisUtil.getInstance().set("pay_return_url_"+userId, returnUrl, 1l, TimeUnit.HOURS);
        //判断用户是否已经是VIP，如果已经是VIP则返回提示
        if (userAccountPojo.getVipStatus()==1){
            throw new BizException(ERRORCODE.VIP_EXIST.getCode(),ERRORCODE.VIP_EXIST.getMessage());
        }
        //判断用户是否已经申请VIP，如果已经已经VIP则返回提示
        if (userAccountPojo.getVipStatus()==2){
            throw new BizException("0100002","该用户已经申请VIP了，请勿重复申请");
        }

        String orderNo = String.valueOf(System.currentTimeMillis())+userId;

        Orders order = setOrders(ordersQuery, userId, orderNo);
        try {
            ordersService.insert(order);
            LOGGER.info("create orders :" + orderNo);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderNo", order.getOrderNo());
            resultMap.put("amount", order.getAmount());
            return JSON.toJSONString(resultMap);
        } catch (Exception e){
            LOGGER.info("====pay /orders/createOrder catch: "+e.getMessage());
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    /**
     * 支付宝支付
     * @return
     */
    @RequestMapping(value = "aliOrderPay")
    @ResponseBody
    public String aliOrder(String orderNo) throws Exception {
        Orders order = (Orders) ordersService.findOne("orderNo", orderNo);
        if(null == order)
        {
            throw new BizException("0000010", "订单号无效!");
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        String payResult  = createPay(orderNo, order, userAccountPojo.getAccount());

        LOGGER.info("====pay /orders/createOrder payResult: "+payResult);

        if(StringUtils.isNotBlank(payResult)){
            return payResult;
        }else {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
    }

    /**
     * 微信支付
     * @return
     */
    @RequestMapping(value = "wxOrderPay")
    public void wxOrder(@RequestParam(value="orderNo",required=false)String orderNo,
                        @RequestParam(value="qrSize",required=false)String size, HttpServletResponse response) {
//        Orders order = (Orders) ordersService.findOne("orderNo", orderNo);
//        if(null == order)
//        {
//            throw new BizException("0000010", "订单号无效!");
//        }

        int width = 200;
        int height = 200;
        if(null != size)
        {
            int qrSize;
            try {
                qrSize = Integer.parseInt(size);
            }catch (NumberFormatException e)
            {
                throw new BizException("0000010", "二维码大小格式错误!");
            }
            width = qrSize;
            height = qrSize;
        }
        String url = orderNo;
        try {
            Orders order = new Orders();
            order.setAmount(new BigDecimal(1));
            order.setOrderNo("123456789");
            String result = getWxPayUrl(order);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map params = new HashMap();
            params.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(result, BarcodeFormat.QR_CODE, width, height, params);
            MatrixToImageWriter.writeToStream(bitMatrix, MatrixToImageWriter.FORMAT, response.getOutputStream());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取微信支付地址
     * @param order
     * @return
     * @throws Exception
     */
    private String getWxPayUrl(Orders order) throws Exception {
        String appid = DynConfigClientFactory.getClient().getConfig("common", "wxAppid");
        String body = "body";
        String key = DynConfigClientFactory.getClient().getConfig("common", "appSecret");
        String mch_id = DynConfigClientFactory.getClient().getConfig("common", "wxMchId");
        String nonce_str = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(nonce_str);
        String urlKey = "pay_return_url_"+getUserAccountPojo().getId();
        String notify_url = "www.baidu.com";
        if(RedisUtil.getInstance().exists(urlKey))
        {
            notify_url = String.valueOf(RedisUtil.getInstance().get(urlKey));
            notify_url = URLDecoder.decode(notify_url, "UTF-8");
        }
        String orderUrl = DynConfigClientFactory.getClient().getConfig("common", "wxApiUnifiedOrderUrl");
        String out_trade_no = order.getOrderNo();
        String spbill_create_ip = IPUtil.getRemortIP(request);
        String total_fee = order.getAmount().toString();
        String trade_type = "NATIVE";
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
        packageParams.put("sign", sign);

        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println(requestXML);

        String resXml = HttpUtil.postData(orderUrl, requestXML);
        Map map = XMLUtil.doXMLParse(resXml);
        String urlCode = (String) map.get("return_code");
        return urlCode;
    }

    private Orders setOrders(OrdersQuery ordersQuery, Long userId, String orderNo) {
        String products = ordersQuery.getProducts();

        BigDecimal amount = BigDecimal.ZERO;

        JSONArray jsonArray = JSON.parseArray(products);

        for(int i =0;i<jsonArray.size();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            BigDecimal unitPrice = new BigDecimal(obj.getString("unitPrice"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal count = new BigDecimal(obj.getIntValue("productNum"))
                    .setScale(0, BigDecimal.ROUND_DOWN);
            amount = amount.add(unitPrice.multiply(count));
        }

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
        order.setChannel(ordersQuery.getChannel());
        return order;
    }

    public String createPay(String orderNo,Orders order,String account) throws Exception{
        String moduleKey = DynConfigClientFactory.getClient().getConfig("common", "moduleKey");

        String url = DynConfigClientFactory.getClient().getConfig("common", "payUrl");
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("orderNo", orderNo);
        chargeParams.put("amount", String.valueOf(order.getAmount()));
        chargeParams.put("channel", "alipay_pc_direct");
        chargeParams.put("currency", "cny");
        chargeParams.put("remoteIp", IPUtil.getRemortIP(request));
        chargeParams.put("extra", null);

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

    private String localIp(){
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 发送请求
     * @param urlStr
     * @param data
     * @return
     */
    public String postData(String urlStr, String data) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            if(data == null)
                data = "";
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
        return null;
    }
}