package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Orders;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.service.IOrdersService;
import cn.thinkjoy.gk.service.IUserVipService;
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
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("")
public class PayCallbackController {

    private static final Logger LOGGER= LoggerFactory.getLogger(PayCallbackController.class);

    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private IUserVipService userVipService;

    @RequestMapping(value = "payCallback", method = RequestMethod.POST)
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

            LOGGER.info("====pay /payCallback· payResult: "+payResult);

            if( payResult !=null ) {
                Map<String,Object> dataMap = new HashMap();
                dataMap.put("orderNo", payResult.getString("orderNo"));
                Orders order =(Orders) ordersService.queryOne(dataMap);

                if(order !=null&&order.getPayStatus()==0){
                    LOGGER.info("====pay /orders/createOrder status: success");
                    long userId = payResult.getLong("userId");
//                        int month = Integer.parseInt(payResult.getMonth());
//                        vipService.orderVip(Long.valueOf(payResult.getOrderNo()), userId, "VIP10001", month);
                    Calendar c = Calendar.getInstance();
                    try{
//                        UserVip uv = (UserVip)userVipService.findOne("id", userId);
//                        Long endDate = uv.getEndDate();
//                        if(null==endDate){
//                            endDate = System.currentTimeMillis();
//                        }
                        int month = c.get(Calendar.MONTH) + 1;
                        if(month>=9) {
                            c.add(Calendar.YEAR, 1);
                        }
                        c.set(Calendar.MONTH, 8);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        UserVip userVip = new UserVip();
                        userVip.setId(userId);
                        userVip.setStatus(1);
                        userVip.setEndDate(c.getTimeInMillis());
                        userVipService.update(userVip);
//                        boolean flag = userVipService.updateUserVip(userId, 1, calendar.getTimeInMillis());
//                        LOGGER.info("====pay /orders/createOrder updatePresell result : "+flag);
                    } catch (Exception e) {
                        LOGGER.info("====pay /payCallback updatePresell error : "+e.getMessage());
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

//    public static void main(String[] args) {
//        Calendar c = Calendar.getInstance();
//
////        System.out.println(month);
////        c.setTimeInMillis(System.currentTimeMillis());
//        c.add(Calendar.YEAR, 1);
//        c.set(Calendar.MONTH,8);
//        c.set(Calendar.DAY_OF_MONTH,1);
//        System.out.println(DateUtil.DateToString(new Date(System.currentTimeMillis()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
//        System.out.println(DateUtil.DateToString(new Date(c.getTimeInMillis()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
//
//    }
}
