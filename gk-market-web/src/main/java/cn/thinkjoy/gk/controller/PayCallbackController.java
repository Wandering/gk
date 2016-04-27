package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.common.HttpClientUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.service.IOrdersService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
    private String gkxtActiveUrl = "http://zhigaokao.kongkonghou.cn/userapi/tovip?mobile=%s&duration=12&unit=month&levelId=1";

    @RequestMapping(value = "payCallback", method = RequestMethod.GET)
    public String payCallback(HttpServletRequest request) {
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

                String orderNo = paramMap.get("out_trade_no");

                Orders order =(Orders) ordersService.findOne("orderNo", orderNo);
                if(order !=null&&order.getPayStatus()==0){
                    order.setPayStatus(1);
                    order.setStatus(1);
                    order.setLastModDate(System.currentTimeMillis());
                    ordersService.update(order);
                }
                String account = getUserAccountPojo().getAccount();
                long userId = getUserAccountPojo().getId();
                gkxtActiveUrl = String.format(gkxtActiveUrl, account);
                String result = HttpClientUtil.getContents(gkxtActiveUrl);
                if(result.indexOf("\"ret\":\"200\"")==-1)
                {
                    LOGGER.error("帐号"+account+", 激活高考学堂会员失败.....");
                }else
                {
                    LOGGER.debug("帐号"+account+"激活高考学堂会员成功!");
                }
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
