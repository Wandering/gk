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

    @RequestMapping("/payCallback")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {

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

        } catch (IOException e) {
            LOGGER.error("error",e);
        }

        return "user/vip-service";

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
