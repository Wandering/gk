package cn.thinkjoy.gk.controller.university;

/**
 * Created by wpliu on 15/9/25.
 */

import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.IExUniversityService;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.query.UniversityQuery;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUniversityDictService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学院信息控制类
 *
 */
@Controller("universityController")
@RequestMapping("/university")
public class UniversityController extends BaseController {

    public static final Logger LOGGER= LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    private IExUniversityService iUniversityService;
    @Autowired
    private IUniversityDictService universityDictService ;

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
    @RequestMapping(value = "/getUniversityList",method = RequestMethod.POST)
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
                if((universityFeatureId.intValue() & universityDict.getDictId().intValue()) !=0){
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
     * 获取大学详情
     * @return
     */
    @RequestMapping(value = "/getUniversityDetail",method = RequestMethod.POST)
    @ResponseBody
    public UniversityDto getUniversityDetail(){
        UniversityDto universityDto=new UniversityDto();
        String schoolCode=request.getParameter("code");
        universityDto=iUniversityService.getUniversityDetail(schoolCode);
        return universityDto;
    }

    /**
     * 获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getEnrollInfo(){
        String schoolCode=request.getParameter("code");
        Map<String,Object> map=new HashMap<String, Object>();
        List<EnrollResponseDto>  enrollResponseDtoList=new ArrayList<EnrollResponseDto>();
        List<EnrollInfo>  lastenrollInfos=iUniversityService.getEnrollInfoByYear(2014,schoolCode);
        List<EnrollInfo>  enrollInfos=iUniversityService.getEnrollInfoByYear(2013,schoolCode);
        EnrollResponseDto lastEnrollResponseDto=new EnrollResponseDto();
        lastEnrollResponseDto.setTitle("2013招生情况");
        lastEnrollResponseDto.setInfos(lastenrollInfos);
        EnrollResponseDto enrollResponseDto=new EnrollResponseDto();
        enrollResponseDto.setTitle("2014招生情况");
        enrollResponseDto.setInfos(enrollInfos);
        enrollResponseDtoList.add(enrollResponseDto);
        enrollResponseDtoList.add(lastEnrollResponseDto);
        map.put("enrollInfo",enrollResponseDtoList);
        return map;
    }

    /**
     *获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollPlan",method = RequestMethod.POST)
    @ResponseBody
    public EntrollPlanDto getEnrollPlan(){
        String schoolCode=request.getParameter("code");
        EntrollPlanDto entrollPlanDto=new EntrollPlanDto();
        List<EntrollPlan> entrollPlans=new ArrayList<EntrollPlan>();

        EntrollPlan entrollPlan=new EntrollPlan();
        EntrollPlan lastEntrollPlan=new EntrollPlan();
        List<PlanInfo> planInfos=iUniversityService.getPlanInfosByYear(2015,schoolCode);
        List<PlanInfo> lastPlanInfos=iUniversityService.getPlanInfosByYear(2014,schoolCode);
        entrollPlan.setTitle("2015年招生计划");
        entrollPlan.setPlanInfos(planInfos);
        lastEntrollPlan.setTitle("2014年招生计划");
        lastEntrollPlan.setPlanInfos(lastPlanInfos);
        /**
         * 招生简介
         */
        String entroIntro=iUniversityService.getUniversityEnrollIntro(schoolCode);
        /**
         * 大学介绍
         */
        String  universityIntro=iUniversityService.getUniversityEnrollIntro(schoolCode);

        entrollPlanDto.setEntroIntro(entroIntro);
        entrollPlanDto.setUniversityIntro(universityIntro);
        entrollPlanDto.setEnrollPlan(entrollPlans);

        return  entrollPlanDto;
    }
}
