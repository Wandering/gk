package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.service.IOrdersService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
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

    @RequestMapping(value = "payCallback", method = RequestMethod.GET)
    public String payCallback(HttpServletRequest request) {
        String returnUrl = "www.zhigaokao.cn";
        try {
            request.setCharacterEncoding("UTF-8");

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();

            String s;
            while ((s = reader.readLine()) != null) {
                builder.append(s);
            }
            reader.close();

            JSONObject payResult = JSON.parseObject(builder.toString());

            LOGGER.info("====pay /payCallback payResult: "+payResult);

            if( null != payResult) {
                long orderNo = payResult.getLong("orderNo");
                returnUrl = payResult.getString("returnUrl");
                Map<String,Object> dataMap = new HashMap();
                dataMap.put("orderNo", orderNo);
                Orders order =(Orders) ordersService.queryOne(dataMap);
                if(order !=null&&order.getPayStatus()==0){
                    Orders update = new Orders();
                    update.setId(orderNo);
                    update.setPayStatus(1);
                    update.setStatus(1);
                    update.setLastModDate(System.currentTimeMillis());
                    ordersService.update(update);
                }
            }
        } catch (Exception e) {
            LOGGER.error("error",e);
        }
        return "redirect:"+ returnUrl;
    }

}
