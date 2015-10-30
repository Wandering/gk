package cn.thinkjoy.gk.controller;

/**
 * Created by wpliu on 15/9/25.
 */

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.query.UniversityQuery;
import cn.thinkjoy.gk.service.*;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学院信息控制类
 *
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/university")
public class UniversityController extends BaseController {

    public static final Logger LOGGER= LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    private IExUniversityService iUniversityService;
    @Autowired
    private IUniversityDictService universityDictService;
    @Autowired
    private IUniversityExService universityExService;
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private IDataDictService dataDictService;


    /**
     * 获取初始化信息
     * @return
     */
    @RequestMapping(value = "/getInitInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitInfo(){
        Map<String,Object> responseMap=new HashMap<String, Object>();
        List<ProvincePojo> provincePojos=new ArrayList<>();
        List<Map<String, Object>> universityType=new ArrayList<>();
        List<Map<String,Object>>  universityBatch=new ArrayList<>();
        List<Map<String,Object>>  universityFeature=new ArrayList<>();

        List<Province> provinces=provinceService.findAll();
        for(Province province:provinces){
            ProvincePojo provincePojo=new ProvincePojo();
            provincePojo.setId(province.getId());
            provincePojo.setName(province.getName());
            provincePojos.add(provincePojo);
        }
        Map<String,Object> map=new HashMap<>();//院校类型
        map.put("type","PROPERTY");//院校类型
        universityType=dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityTypeList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityType.add(inMap);
//        }
        map.put("type","BATCHTYPE");//批次类型
        universityBatch=dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityBatchList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityBatch.add(inMap);
//        }
        map.put("type","FEATURE");//院校特征
        universityFeature=dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityFeatureList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityFeature.add(inMap);
//        }
        responseMap.put("provinces",provincePojos);
        responseMap.put("universityType",universityType);
        responseMap.put("universityBatch",universityBatch);
        responseMap.put("universityFeature", universityFeature);
        return  responseMap;
    }

    /**
     * 获取大学列表
     * @param universityQuery
     * @return
     */
    @RequestMapping(value = "/getUniversityList",method = RequestMethod.GET)
    @ResponseBody
    public Page<UniversityDto> getUniversityList(UniversityQuery universityQuery){
        Page<UniversityDto> page=new Page<>();
        Map<String,Object> map=new HashMap<>();

        //构造院校批次参数 一批本科
        Integer universityBatchId=universityQuery.getUniversityBatchId();
        List<Integer> universityBatchParam=new ArrayList<>();//构造出来的参数
        if(null==universityBatchId ||universityBatchId.intValue()==0){
            universityBatchParam=null;
        }else{
            map.put("type","BATCHTYPE");//批次类型
            List<UniversityDict> universityBatchList=universityDictService.queryList(map,"id","asc");
            for(UniversityDict universityDict:universityBatchList){
                if((universityBatchId.intValue() & universityDict.getDictId().intValue()) !=0){
                    universityBatchParam.add(universityDict.getDictId());
                }
            }
        }

        //构造院校特征参数 985,211
        List<Integer> universityFeatureParam=new ArrayList<>();//构造出来的参数
        String universityFeature=universityQuery.getUniversityFeatureId();
        String[] strArray=universityFeature.split(",");
        if("0".equals(universityFeature)||null==strArray ){
            universityFeatureParam=null;
        }else{
            Integer universityFeatureId=0;
            for(String inString:strArray){
                universityFeatureId= universityFeatureId+Integer.parseInt(inString);
            }
            map.put("type","FEATURE");//院校特征类型
            List<UniversityDict> universityFeatureList=universityDictService.queryList(map,"id","asc");
            for(UniversityDict universityDict:universityFeatureList){
                if((universityFeatureId.intValue() & universityDict.getDictId().intValue()) >= universityFeatureId.intValue() ){
                        universityFeatureParam.add(universityDict.getDictId());
                }
            }
        }



        //院校类型
//        Integer universityTypeId=universityQuery.getUniversityTypeId();
//        List<Integer> universitytTypeParam=new ArrayList<>();//构造出来的参数
//        if(null==universityTypeId ||universityTypeId.intValue()==0){
//            universitytTypeParam=null;
//        }else{
//            map.put("type","PROPERTY");//院校类型
//            List<UniversityDict> universityBatchList=universityDictService.queryList(map,"id","asc");
//            for(UniversityDict universityDict:universityBatchList){
//                if((universityTypeId.intValue() & universityDict.getDictId().intValue()) !=0){
//                    universitytTypeParam.add(universityDict.getDictId());
//                }
//            }
//        }
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("province",universityQuery.getProvinceName());
        queryParams.put("universtiyType",universityQuery.getUniversityTypeName());
        queryParams.put("batch",universityBatchParam);
        queryParams.put("feature",universityFeatureParam);
        queryParams.put("start",(universityQuery.getPageNo()-1)*universityQuery.getPageSize());
        queryParams.put("end",universityQuery.getPageSize());
        queryParams.put("searchName",universityQuery.getSearchName());
        UniversityResponseDto universityResponseDto=new UniversityResponseDto();
        List<UniversityDto> universityDtos=new ArrayList<UniversityDto>();
        universityDtos=iUniversityService.getUniversityList(queryParams);
        Integer universityCount=iUniversityService.getUniversityCount(queryParams);
        page.setList(universityDtos);
        page.setCount(universityCount);
        return  page;
    }

    /**
     * 根据Id获取大学详情
     * @return
     */
    @RequestMapping(value = "/getUniversityDetail",method = RequestMethod.GET)
    @ResponseBody
    public UniversityDto getUniversityDetail(){
        UniversityDto universityDto=new UniversityDto();
        String schoolId=request.getParameter("id");
        String code=request.getParameter("code");
        String batch =request.getParameter("batch");
        Map<String,Object> map= new HashMap<String, Object>();
        map.put("id",schoolId);
        map.put("code",code);
        map.put("batch",batch);
        universityDto=iUniversityService.getUniversityDetail(map);
        return universityDto;
    }

    /**
     * 获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEnrollInfo()throws Exception{
        long areaId=getAreaCookieValue();
//        long areaId=450000L;
        String schoolId=request.getParameter("id");
        Map<String,Object> map=new HashMap<String, Object>();
        List<EnrollResponseDto>  enrollResponseDtoList=new ArrayList<EnrollResponseDto>();
        List<EnrollInfo>  lastenrollInfos=iUniversityService.getEnrollInfoByYear(2014,schoolId,areaId);
        List<EnrollInfo>  enrollInfos=iUniversityService.getEnrollInfoByYear(2013,schoolId,areaId);
        EnrollResponseDto lastEnrollResponseDto=new EnrollResponseDto();
        lastEnrollResponseDto.setTitle("2013招生情况");
        lastEnrollResponseDto.setInfos(enrollInfos);
        EnrollResponseDto enrollResponseDto=new EnrollResponseDto();
        enrollResponseDto.setTitle("2014招生情况");
        enrollResponseDto.setInfos(lastenrollInfos);
        enrollResponseDtoList.add(enrollResponseDto);
        enrollResponseDtoList.add(lastEnrollResponseDto);
        map.put("enrollInfo",enrollResponseDtoList);
        return map;
    }

    /**
     *获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollPlan",method = RequestMethod.GET)
    @ResponseBody
    public EntrollPlanDto getEnrollPlan() throws Exception{
        String schoolId=request.getParameter("id");
        String batch=request.getParameter("batch");
        long areaId=getAreaCookieValue();
        EntrollPlanDto entrollPlanDto=new EntrollPlanDto();
        List<EntrollPlan> entrollPlans=new ArrayList<EntrollPlan>();
        switch (batch){
            case "1": batch="一批本科";
                  break;
            case "2": batch="二批本科";
                  break;
            case "3":batch="三批本科";
                  break;
            case "4":batch="高职（专科）";
                 break;
            default: batch="";

        }
        EntrollPlan entrollPlan=new EntrollPlan();
        EntrollPlan lastEntrollPlan=new EntrollPlan();
        List<PlanInfo> planInfos=iUniversityService.getPlanInfosByYear(2015,schoolId,batch,areaId);
        List<PlanInfo> lastPlanInfos=iUniversityService.getPlanInfosByYear(2014,schoolId,batch,areaId);
        entrollPlan.setTitle("2015年招生计划");
        entrollPlan.setPlanInfos(planInfos);
        lastEntrollPlan.setTitle("2014年招生计划");
        lastEntrollPlan.setPlanInfos(lastPlanInfos);
        /**
         * 招生简介
         */
        String entroIntro=iUniversityService.getUniversityEnrollIntro(schoolId);
        /**
         * 大学介绍
         */
        String  universityIntro=iUniversityService.getUniversityIntro(schoolId);
        entrollPlans.add(entrollPlan);
        entrollPlans.add(lastEntrollPlan);
        entrollPlanDto.setEntroIntro(entroIntro);
        entrollPlanDto.setUniversityIntro(universityIntro);
        entrollPlanDto.setEnrollPlan(entrollPlans);

        return  entrollPlanDto;
    }


    /**
     * 获取院校详情
     * @return
     */
    @RequestMapping(value = "/universityDetail",method = RequestMethod.GET)
    @ResponseBody
    public UniversityDetailDto universityDetail(@RequestParam(value="code",required=false) String code,
                                                @RequestParam(value="type",required=false) Integer type,
                                                @RequestParam(value="year",required=false) Integer year,
                                                @RequestParam(value="batch",required=false) String batch) throws Exception{


        if(StringUtils.isBlank(code)
                ||StringUtils.isBlank(batch)
                ||null==type
                ||null==year){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

//        String str = null;
//
//        try {
//            str = new String(batch.getBytes("ISO-8859-1"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),ERRORCODE.PARAM_ERROR.getMessage());
//        }
        long areaId=getAreaCookieValue();
        UniversityDetailDto universityDetailDto = null;
        try{
            universityDetailDto = universityExService.getUniversityDetail(code,batch,type,year,areaId);
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }

        if(universityDetailDto==null){
            return null;
        }

        int enrollNum = universityDetailDto.getEnrollNum();

        int planNum = universityDetailDto.getPlanNum();

        int num = enrollNum-planNum;

        if(num>0){
            universityDetailDto.setEnrollIntro("实际招生超过计划招生数!");
        }else if(num==0){
            universityDetailDto.setEnrollIntro("实际招生和计划招生数相等!");
        }else{
            universityDetailDto.setEnrollIntro("计划招生超过实际招生数!");
        }

        return universityDetailDto;
    }
}
