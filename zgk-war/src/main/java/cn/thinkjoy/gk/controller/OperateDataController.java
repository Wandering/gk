package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.IOperateDataService;
import cn.thinkjoy.gk.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 17/1/9.
 *
 * 运营数据查询控制类
 */
@Controller
@RequestMapping(value = "/operate")
public class OperateDataController {

    @Autowired
    private IOperateDataService operateDataService;

    @ResponseBody
    @RequestMapping(value = "/getAliData",method = RequestMethod.GET)
    public List<Map<String,String>> getAliData(@RequestParam("startTime") String startTime,
                                               @RequestParam("endTime") String endTime,
                                               @RequestParam("hour") int hour,
                                               @RequestParam("minute") int minute){

        Date date = new Date();
        // 为避免各个客户端时间误差,分钟数+-2
        if (DateUtil.getHour(date) != hour || Math.abs(DateUtil.getMinute(date) - minute) > 2){
            ModelUtil.throwException(ERRORCODE.AUTHENTICATION_FAIL);
        }

        if(startTime == null || endTime == null){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }

        return operateDataService.getAliData(startTime,endTime);
    }

}
