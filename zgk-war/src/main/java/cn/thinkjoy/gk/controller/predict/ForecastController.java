/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ForecastController.java 2016-01-22 11:48:22 $
 */

package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.domain.BizStatusEnum;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.gk.annotation.VipMethonTag;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import cn.thinkjoy.zgk.remote.IUniversityService;
import com.alibaba.fastjson.JSON;
import cn.thinkjoy.gk.domain.Forecast;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.text.resources.FormatData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value="/forecast")
public class ForecastController extends BaseApiController{
    @Autowired
    private IForecastService forecastService;
    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;

    @Autowired
    private IUniversityService universityService;

    /**
     *获取当前用户的成绩明细
     * @return
     */
    @RequestMapping(value = "/getPerformanceDetail",method = RequestMethod.GET)
    @ResponseBody
    @VipMethonTag
    public Object getPerformanceDetail( @ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                         @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "10",required = false) Integer rows){


    //实际接口
//        if(page==null || rows==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", this.getAccoutId());
            Map<String,Object> results = new HashMap<>();
            List forecasts=forecastService.queryList(map, "lastModDate", "desc");
            List<Map<String,Object>> forecastsList = null;
            forecastsList=(List<Map<String,Object>>)JSON.parse(JSON.toJSONString(forecasts));
            if(forecastsList!=null) {
                for (Map<String, Object> forecastMap : forecastsList) {
                    getWanting(forecastMap);
                }
            }
            results.put("forecasts",forecastsList);
            results.put("chart",chart(forecasts));
            return results;
//        }else {
//            Map<String, Object> map = new HashMap<>();
//            Map<String,Object> results = new HashMap<>();
//            QueryUtil.setMapOp(map,"userId","=",this.getAccoutId());
//            BizData4Page<Forecast> bizData4Page=doPage(map,forecastService,page,rows);
//            results.put("forecasts",bizData4Page);
//            results.put("chart",chart(bizData4Page.getRows()));
//            return results;
//        }
    }

    /**
     *最后测试成绩
     * @return
     */
    @RequestMapping(value = "/getLastoFrecast",method = RequestMethod.GET)
    @ResponseBody
    @VipMethonTag
    public Object getLastoFrecast(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",this.getAccoutId());
        Forecast forecast=(Forecast)forecastService.queryOne(map,"lastModDate", SqlOrderEnum.DESC);
//        Forecast forecast=(Forecast)null;
        Map<String,Object> forecastMap=(Map<String,Object>)JSON.parse(JSON.toJSONString(forecast));
        if(forecastMap!=null) {
            getWanting(forecastMap);
            setFillingNumber(forecastMap);
        }else {
            forecastMap=new HashMap<>();
        }
        return forecastMap;
    }



    /**
     *用户定位院校历年分数线
     * @return
     */
    @RequestMapping(value = "/getFormerYearsAdmission",method = RequestMethod.GET)
    @ResponseBody
    @VipMethonTag
    public Object getFormerYearsAdmission(@RequestParam String universityid,@RequestParam(required = false) Integer batch){
        idIsNull(universityid);
        Map<String,Object> map = new HashMap<>();
        QueryUtil.setMapOp(map,"universityid","=",universityid);
        if(batch!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }
        Object o=gkAdmissionLineService.getGkAdmissionLineList(map,null,3);
        BizData4Page bizData4Page = (BizData4Page)o;
        return isNull(bizData4Page.getRows());
    }


    /**
     *添加用户定位信息
     * @return
     */
    @RequestMapping(value = "/addFrecast")
    @ResponseBody
    @VipMethonTag
    public boolean addFrecast(){
        //TODO   支持多对象保存
        Map<String, Object> dataMap = Maps.newHashMap();

        String prop = null;
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            dataMap.put(prop, request.getParameter(prop));
            if("lowestScore".equals(prop)){
                String lowestScore=request.getParameter(prop);
                if("NaN".equals(lowestScore)){
                    dataMap.put(prop, 0);
                }
            } if("averageScore".equals(prop)){
                String lowestScore=request.getParameter(prop);
                if("NaN".equals(lowestScore)){
                    dataMap.put(prop, 0);
                }
            }
        }
        dataMap.put("creator", this.getAccoutId());
        dataMap.put("createDate", System.currentTimeMillis());
        dataMap.put("lastModifier", this.getAccoutId());
        dataMap.put("lastModDate", System.currentTimeMillis());
        if(dataMap.get("status") == null || ((String)dataMap.get("status")).trim().length() == 0){
            dataMap.put("status", BizStatusEnum.N.getCode());
        }

//        typeId 科类ID(文史理工)
//        universityId 院校ID
//        universityName 院校名称
//        achievement 成绩
//        lowestScore 最低分
//        averageScore 平均分
        Map<String,Object> map = new HashMap<>();

        map.put("typeId","科类ID");
        map.put("universityName","院校名称");
        map.put("achievement","成绩");
        map.put("lowestScore","最低分");
        map.put("averageScore", "平均分");
        //参数校验
        this.paramCheck(map,dataMap);
        //模拟测试数据
        dataMap.put("userId", this.getAccoutId());
        try {
            List<Map<String,Object>> list=universityService.getUniversityByName(dataMap.get("universityName").toString());
            Map<String,Object> map1= list.get(0);
            if(!"".equals(map1.get("id"))&&dataMap.get("universityName").equals(map1.get("label"))) {
                dataMap.put("universityId", map1.get("id"));
            }else {
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("error","获取院校信息失败院校不存在或者存在多个学校");
        }
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


    private Map<String,Object> chart(List<Forecast> list){
        if(list==null){
            return null;
        }
        Map<String,Object> map= new HashMap<>();

        //存放学校
        Map<String,Object> legendMap=new HashMap<>();
        Set<String> names=new HashSet<>();
        legendMap.put("data",names);
        //存放时间
        Map<String,Object> xAxisMap=new HashMap<>();
        StringBuilder dateStrings=new StringBuilder();
        DateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd");
        //存放学校对应分数
        Map<String,StringBuffer> seriesMap=new HashMap<>();
        List<Map<String,Object>> seriesList=new ArrayList<>();

        for(Forecast forecast:list){
            names.add(forecast.getUniversityName());
            dateStrings.append(dateFormat.format(new Date(forecast.getLastModDate()))).append(",");
            if(!seriesMap.containsKey(forecast.getUniversityName())) {
                StringBuffer stringBuffer=new StringBuffer(forecast.getAchievement().toString());
                seriesMap.put(forecast.getUniversityName(),stringBuffer);
            }else {
                seriesMap.get(forecast.getUniversityName()).append(",").append(forecast.getAchievement().toString());
            }
        }
        xAxisMap.put("data",dateStrings.toString().split(","));
        Map<String,Object> serie=null;
        Iterator<String> iterator=seriesMap.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String value=seriesMap.get(key).toString();
            serie = new HashMap<>();
            serie.put("name",key);
            serie.put("stack","总量");
            serie.put("type","line");
            serie.put("data",value.split(","));
            seriesList.add(serie);
        }
        //存放学校
        map.put("legend", legendMap);

        //存放时间
        map.put("xAxis",xAxisMap);

        //存放学校对应分数
        map.put("series", seriesList);
        return map;
    }

    private void getWanting(Map<String,Object> forecastMap){
        Integer averageScore=Integer.parseInt(forecastMap.get("averageScore").toString());
        Integer lowestScore=Integer.parseInt(forecastMap.get("lowestScore").toString());
        Integer achievement=Integer.parseInt(forecastMap.get("achievement").toString());
        Integer wanting=0;
        if(averageScore==0){
            if(lowestScore==0){
                forecastMap.put("wanting","-");
            }else {
                wanting=lowestScore-achievement;
                forecastMap.put("wanting",wanting);
                forecastMap.put("countType","lowestScore");
            }
        }else {
            wanting=averageScore-achievement;
            forecastMap.put("wanting",wanting);
            forecastMap.put("countType","averageScore");
        }
    }

    private void setFillingNumber(Map<String,Object> forecastMap){
        forecastMap.put("fillingNumber",forecastService.getFillingNumber(forecastMap.get("universityId").toString()));
    }
}
