/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastController.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.domain.BizStatusEnum;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.UserContext;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.domain.Forecast;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;
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
    //实际接口
        Map<String,Object> map=new HashMap<>();
        map.put("userId", this.getAccoutId());
        return forecastService.queryList(map, "lastModDate", "desc");
    }

    /**
     *最后测试成绩
     * @return
     */
    @RequestMapping(value = "/getLastoFrecast",method = RequestMethod.GET)
    @ResponseBody
    public Object getLastoFrecast(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",this.getAccoutId());
        return forecastService.queryOne(map);
    }

    /**
     *用户定位院校历年分数线
     * @return
     */
    @RequestMapping(value = "/getFormerYearsAdmission",method = RequestMethod.GET)
    @ResponseBody
    public Object getFormerYearsAdmission(@RequestParam String universityid,@RequestParam(required = false) Integer batch){
        idIsNull(universityid);
        Map<String,Object> map = new HashMap<>();
        QueryUtil.setMapOp(map,"universityid","=",universityid);
        if(batch!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }
        BizData4Page bizData4Page=gkAdmissionLineService.getGkAdmissionLineList(map,null,3);
        return isNull(bizData4Page.getRows());
    }


    /**
     *添加用户定位信息
     * @return
     */
    @RequestMapping(value = "/addFrecast",method = RequestMethod.POST)
    @ResponseBody
    public boolean addFrecast(){
        //TODO   支持多对象保存
        Map<String, Object> dataMap = Maps.newHashMap();

        String prop = null;
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            dataMap.put(prop, request.getParameter(prop));
        }
        dataMap.put("creator", UserContext.getCurrentUser().getId());
        dataMap.put("createDate", System.currentTimeMillis());
        dataMap.put("lastModifier", UserContext.getCurrentUser().getId());
        dataMap.put("lastModDate", System.currentTimeMillis());
        if(dataMap.get("status") == null || ((String)dataMap.get("status")).trim().length() == 0){
            dataMap.put("status", BizStatusEnum.N.getCode());
        }

        //模拟测试数据
        dataMap.put("userId",this.getAccoutId());
        if("1".equals(dataMap.get("typeId")))
        {
            dataMap.put("type","文史");
        }else {
            dataMap.put("type","理工");
        }
        try {
            forecastService.insertMap(dataMap);
        }catch (Exception e){
            throw new BizException(ERRORCODE.ADDEXCEPTION.getCode(),ERRORCODE.ADDEXCEPTION.getMessage());
        }
        return true;
    }

}
