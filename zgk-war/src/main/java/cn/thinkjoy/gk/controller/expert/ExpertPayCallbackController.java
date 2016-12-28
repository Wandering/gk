package cn.thinkjoy.gk.controller.expert;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.domain.OrderStatements;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IExpertService;
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
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by svenlau on 2016/5/19.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expertCallBack")
public class ExpertPayCallbackController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExpertPayCallbackController.class);

    @Autowired
    private IExpertService expertService;
    @Autowired
    private IOrderStatementsService orderStatementService;
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
            int readlen;
            try {
                readlen = request.getInputStream().read(buffer, i,
                        contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
                String requestJson=new String(buffer,"UTF-8");
                String status;
                JSONObject object= JSONObject.parseObject(requestJson);
                String result = object.getString("type");
                Map<String,Object> callBackMap= (Map<String, Object>) ((Map)object.get("data")).get("object");
                String statementNo = callBackMap.get("order_no").toString();
                String channel = callBackMap.get("channel").toString();
                if("charge.succeeded".equals(result))
                {
                    status="1";
                    OrderStatements orderStatement =
                        (OrderStatements)orderStatementService.findOne("statement_no", statementNo);
                    if(null !=orderStatement && !"1".equals(orderStatement.getStatus() + ""))
                    {
                        orderStatement.setStatus(1);
                        orderStatement.setCallBackJson(requestJson);
                        orderStatementService.update(orderStatement);
                    }
                    if(null != orderStatement)
                    {
                        String orderNo = orderStatement.getOrderNo();
                        ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
                        if(order !=null&&"0".equals(order.getOrderStatus())){
                            order.setOrderStatus(status);
                            order.setChannel(channel);
                            expertService.updateOrder(order);
                        }
                    }
                    response.setStatus(200);
                }else if ("refund.succeeded".equals(result)) {
                    response.setStatus(200);
                } else {
                    response.setStatus(500);
                }
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
        Enumeration<String> names = request.getParameterNames();
        String prop;
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            paramMap.put(prop, request.getParameter(prop));
        }
        try {
            request.setCharacterEncoding("UTF-8");
            if(!paramMap.isEmpty()) {
                String statementNo = paramMap.get("out_trade_no");
                OrderStatements orderStatement =
                    (OrderStatements)orderStatementService.findOne("statement_no", statementNo);
                if(null != orderStatement && !"1".equals(orderStatement.getStatus() + ""))
                {
                    orderStatement.setStatus(1);
                    orderStatementService.update(orderStatement);
                }
                if(null != orderStatement)
                {
                    String orderNo = orderStatement.getOrderNo();
                    ExpertOrder order = expertService.findOrderByOrderNo(orderNo);
                    if(order !=null&&"0".equals(order.getOrderStatus())){
                        order.setOrderStatus("1");
                        order.setChannel("alipay_pc_direct");
                        expertService.updateOrder(order);
                        String userId = order.getUserId();
                        String urlKey = "pay_return_url_"+userId;
                        //获取回调url
                        if(RedisUtil.getInstance().exists(urlKey))
                        {
                            returnUrl = String.valueOf(RedisUtil.getInstance().get(urlKey));
                            returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
                            RedisUtil.getInstance().del(urlKey);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("error",e);
        }
        return "redirect:http://"+ returnUrl;
    }
}
