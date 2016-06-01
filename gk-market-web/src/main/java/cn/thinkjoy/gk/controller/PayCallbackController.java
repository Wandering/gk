package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Order;
import cn.thinkjoy.gk.domain.OrderStatements;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IOrderService;
import cn.thinkjoy.gk.service.IOrderStatementsService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by jzli on 15/6/3.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("")
public class PayCallbackController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(PayCallbackController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderStatementsService orderStatementService;

    //高考学堂注册接口
    private String gkxtActiveUrl = "http://xuetang.zhigaokao.cn/userapi/tovip?mobile=%s&duration=12&unit=month&levelId=1";

    /**
     * 微信支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "payCallback", method = RequestMethod.POST)
    public void payCallback(HttpServletRequest request) {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            throw new BizException(ERRORCODE.FAIL.getCode(), "回调参数为空!");
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = 0;
            try {
                readlen = request.getInputStream().read(buffer, i,
                        contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
                String requestJson=new String(buffer,"UTF-8");
                LOGGER.debug(requestJson);
                int status = 0;
                JSONObject object=   JSONObject.parseObject(requestJson);
                String result = object.getString("type");
                Map<String,Object> callBackMap= (Map) ((Map)object.get("data")).get("object");
                String statementNo = callBackMap.get("order_no").toString();
                String channel = callBackMap.get("channel").toString();
                String amount = callBackMap.get("amount").toString();
                BigDecimal price = new BigDecimal(amount).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN);
                if("charge.succeeded".equals(result))
                {
                    status=1;
                    LOGGER.debug("流水号:"+statementNo+"支付成功!支付金额为"+price.toString());
                    OrderStatements orderStatement =(OrderStatements) orderStatementService.findOne("statement_no", statementNo);
                    if(!"1".equals(orderStatement.getStatus() + ""))
                    {
                        orderStatement.setStatus(1);
                        orderStatement.setCallBackJson(requestJson);
                        orderStatementService.update(orderStatement);
                        LOGGER.debug("流水号:"+statementNo+"状态跟新成功!");
                    }
                    String orderNo = orderStatement.getOrderNo();
                    Order order = (Order) orderService.findOne("order_no", orderNo);
                    if(order !=null&&order.getStatus()==0){
                        order.setStatus(status);
                        order.setChannel(channel);
                        orderService.update(order);
                        LOGGER.debug("订单号:"+orderNo+"状态跟新成功!");
                    }
                    response.setStatus(200);
                }else if ("refund.succeeded".equals(result)) {
                    response.setStatus(200);
                } else {
                    response.setStatus(500);
                }
//                String account = getUserAccountPojo().getAccount();
//                gkxtActiveUrl = String.format(gkxtActiveUrl, account);
//                String result = HttpClientUtil.getContents(gkxtActiveUrl);
//                //激活状态,0为未激活,1为激活
//
//                if(result.indexOf("\"ret\":\"200\"")==-1)
//                {
//                    status = 0;
//                    LOGGER.error("帐号"+account+", 激活高考学堂会员失败.....");
//                }else
//                {
//                    status = 1;
//                    LOGGER.debug("帐号"+account+"激活高考学堂会员成功!");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 支付宝支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "aLiPayCallback", method = RequestMethod.GET)
    public String aLiPayCallback(HttpServletRequest request) {
        String returnUrl = "www.zhigaokao.cn";
        Map<String, String> paramMap = Maps.newHashMap();
        String prop;
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            paramMap.put(prop, request.getParameter(prop));
        }
        try {
            request.setCharacterEncoding("UTF-8");
            if(!paramMap.isEmpty()) {
                String statementNo = paramMap.get("out_trade_no");
                OrderStatements orderStatement =(OrderStatements) orderStatementService.findOne("statement_no", statementNo);
                String orderNo = orderStatement.getOrderNo();
                Order order = (Order) orderService.findOne("order_no", orderNo);
                if(order !=null&&order.getStatus()==0){
                    order.setStatus(1);
                    order.setChannel("alipay_pc_direct");
                    orderService.update(order);
                }
                long userId = order.getUserId();
                String urlKey = "pay_return_url_"+userId;
                //获取回调url
                if(RedisUtil.getInstance().exists(urlKey))
                {
                    returnUrl = String.valueOf(RedisUtil.getInstance().get(urlKey));
                    returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
                    RedisUtil.getInstance().del(urlKey);
                }
            }
        } catch (Exception e) {
            LOGGER.error("error",e);
        }
        return "redirect:http://"+ returnUrl;
    }
}
