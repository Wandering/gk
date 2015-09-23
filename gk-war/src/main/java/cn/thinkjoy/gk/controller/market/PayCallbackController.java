package cn.thinkjoy.gk.controller.market;

import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.service.IOrdersService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jzli on 15/6/3.
 */
@Controller
@RequestMapping("")
public class PayCallbackController {

    private static final Logger LOGGER= LoggerFactory.getLogger(PayCallbackController.class);

    @Autowired
    private IOrdersService ordersService;

    @RequestMapping("/payCallback")
    public void payCallback(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            request.setCharacterEncoding("UTF-8");

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();

            String s = null;
            while ((s = reader.readLine()) != null) {
                builder.append(s);
            }
            reader.close();

            JSONObject payResult = JSON.parseObject(builder.toString());

            LOGGER.info("====pay /orders/createOrder payResult: "+payResult);

            if( payResult !=null ) {
                Map<String,Object> dataMap = new HashMap();
                dataMap.put("orderNo", payResult.getString("orderNo"));
                Orders order =(Orders) ordersService.queryOne(dataMap);

                if(order !=null&&order.getPayStatus()==0){
                    LOGGER.info("====pay /orders/createOrder status: success");
                    long userId = payResult.getLong("userId");
//                        int month = Integer.parseInt(payResult.getMonth());
//                        vipService.orderVip(Long.valueOf(payResult.getOrderNo()), userId, "VIP10001", month);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, payResult.getIntValue("validValue"));
                    try{
//                        boolean flag = userVipService.updateUserVip(userId, 1, calendar.getTimeInMillis());
//                        LOGGER.info("====pay /orders/createOrder updatePresell result : "+flag);
                    } catch (Exception e) {
                        LOGGER.info("====pay /orders/createOrder updatePresell error : "+e.getMessage());
                    }

                    order.setPayStatus(1);
                    ordersService.update(order);//更新状态
                    result = "success";
                } else {
                    result = "repeat";
                }
            }

        } catch (IOException e) {
            LOGGER.error("error",e);
        }

        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            LOGGER.error("", e);
        }

    }
}
