package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.HttpClientUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IOrdersService;
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
    private IOrdersService ordersService;

    //高考学堂注册接口
    private String gkxtActiveUrl = "http://xuetang.zhigaokao.cn/userapi/tovip?mobile=%s&duration=12&unit=month&levelId=1";

    @RequestMapping(value = "payCallback", method = RequestMethod.POST)
    public String payCallback(HttpServletRequest request) {
        String returnUrl = doCallBack(request);
        return "redirect:http://"+ returnUrl;
    }

    @RequestMapping(value = "aLiPayCallback", method = RequestMethod.GET)
    public String aLiPayCallback(HttpServletRequest request) {
        String returnUrl = doCallBack(request);
        return "redirect:http://"+ returnUrl;
    }

    private String doCallBack(HttpServletRequest request) {
        String returnUrl = "www.zhigaokao.cn";
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
                String orderNo = callBackMap.get("order_no").toString();
                String channel = callBackMap.get("channel").toString();
                String amount = callBackMap.get("amount").toString();
                BigDecimal price = new BigDecimal(amount).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN);
                if("charge.succeeded".equals(result))
                {
                    status=1;
                    LOGGER.debug("订单号:"+orderNo+"支付成功!支付金额为"+price.toString());
                }

                Orders order =(Orders) ordersService.findOne("orderNo", orderNo);
                if(order !=null&&order.getPayStatus()==0){
                    order.setPayStatus(status);
                    order.setStatus(status);
                    order.setChannel(channel);
                    order.setLastModDate(System.currentTimeMillis());
                    ordersService.update(order);
                    LOGGER.debug("订单号:"+orderNo+"状态跟新成功!");
                }
                long userId = getUserAccountPojo().getId();
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
                String urlKey = "pay_return_url_"+userId;
                //获取回调url
                if(RedisUtil.getInstance().exists(urlKey))
                {
                    returnUrl = String.valueOf(RedisUtil.getInstance().get(urlKey));
                    returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
                    if(returnUrl.indexOf("?")>0)
                    {
                        returnUrl += "&status="+status;
                    }else
                    {
                        returnUrl += "?status="+status;
                    }
                    RedisUtil.getInstance().del(urlKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return returnUrl;
    }

}
