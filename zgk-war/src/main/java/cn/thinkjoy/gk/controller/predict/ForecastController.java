/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastController.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value="/forecast")
public class ForecastController extends BaseApiController{



    @Autowired
    private IForecastService forecastService;
    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;


    /**
     *获取当前用户的成绩明细
     * @return
     */
    @RequestMapping(value = "/getPerformanceDetail",method = RequestMethod.GET)
    @ResponseBody
    public Object getPerformanceDetail(){
//        UserContext.getCurrentUser();
//        System.out.println(UserContext.getCurrentUser());

        return forecastService.findAll();
    }

    /**
     *最后测试成绩
     * @return
     */
    @RequestMapping(value = "/getLastoFrecast",method = RequestMethod.GET)
    @ResponseBody
    public Object getLastoFrecast(){
//        UserContext.getCurrentUser();
//        System.out.println(UserContext.getCurrentUser());

        Map<String,Object> map = new HashMap<>();
        return forecastService.queryOne(map);
    }

    /**
     *用户定位院校历年分数线
     * @return
     */
    @RequestMapping(value = "/getFormerYearsAdmission",method = RequestMethod.GET)
    @ResponseBody
    public Object getFormerYearsAdmission(@RequestParam String universityid,@RequestParam(required = false) Integer batch){
//        UserContext.getCurrentUser();
//        System.out.println(UserContext.getCurrentUser());
        idIsNull(universityid);
        Map<String,Object> map = new HashMap<>();
        QueryUtil.setMapOp(map,"universityid","=",universityid);
        if(batch==null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }
        BizData4Page bizData4Page=gkAdmissionLineService.getGkAdmissionLineList(map,null,3);
        return isNull(bizData4Page.getRows());
    }
}
