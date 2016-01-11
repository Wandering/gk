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
import cn.thinkjoy.gk.util.ConditionsUtil;
import cn.thinkjoy.zgk.remote.*;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
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
    private IUserCollectExService userCollectExService;
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

    @Autowired
    private cn.thinkjoy.zgk.remote.IUniversityService iremoteUniversityService;

    /**
     * 智高考院校信息列表
     * @return
     */
    @RequestMapping(value = "/getRemoteUniversityList",method = RequestMethod.GET)
    @ResponseBody
    public List getUniversityList(@RequestParam(value = "universityName",required = false)String universityName,
                                  @RequestParam(value = "areaid",required = false)String areaid,//省份
                                  @RequestParam(value = "type",required = false)Integer type,//院校分类
                                  @RequestParam(value = "educationLevel",required = false)Integer educationLevel,//学历层次
                                  @RequestParam(value = "property",required = false)String property,//院校特征
                                  @RequestParam(value = "offset",required = false,defaultValue = "0")Integer offset,
                                  @RequestParam(value = "rows",required = false,defaultValue = "10")Integer rows){
        Map<String,Object> condition=Maps.newHashMap();
        condition.put("groupOp","and");
        if (StringUtils.isNotBlank(universityName))
            ConditionsUtil.setCondition(condition,"name","like","%"+universityName+"%");
        if (StringUtils.isNotBlank(areaid))
            ConditionsUtil.setCondition(condition,"areaid","=",areaid);
        if (type!=null)
            ConditionsUtil.setCondition(condition,"type","=",type.toString());
        if (educationLevel!=null)
            ConditionsUtil.setCondition(condition,"educationLevel","=",educationLevel.toString());
        if (StringUtils.isNotBlank(property))
            ConditionsUtil.setCondition(condition,"property","like","%"+property+"%");
        String orederBy=null;
        String sqlOrderEnumStr="asc";
        List<Map<String,Object>> getUniversityList=iremoteUniversityService.getUniversityList(condition, offset, rows, orederBy, sqlOrderEnumStr, null);
        //如果用户已登录
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null!=userAccountPojo) {
            long userId = userAccountPojo.getId();
            //需要在收藏表中拼接收藏状态字段
            for (Map<String, Object> university : getUniversityList) {
                Map<String,Object> param=Maps.newHashMap();
//                param.put("userId",54);
                param.put("userId",userId);
                param.put("projectId",university.get("id"));
                param.put("type",1);
                university.put("isCollect",userCollectExService.isCollect(param));
            }
        }
        return getUniversityList;
    }

    @RequestMapping(value = "getRemoteUniversityById",method = RequestMethod.GET)
    @ResponseBody
    public Object getUniversityDTOById(@RequestParam(value = "universityId",required = true)long universityId){
        return iremoteUniversityService.getUniversityById(universityId);
    }

    /**
     * 智高考获取省份列表
     * @return
     */
    @RequestMapping(value = "getRemoteProvinceList",method = RequestMethod.GET)
    @ResponseBody
    public List getProvinceList(){
        return iremoteUniversityService.getProvinceName();
    }

    /**
     * 智高考获取字典表通用接口
     * @param type
     * @return
     */
    @RequestMapping(value = "getRemoteDataDictList",method = RequestMethod.GET)
    @ResponseBody
    public List getDataDictList(@RequestParam(value = "type",required = true)String type){
        return iremoteUniversityService.getDataDictListByType(type);
    }

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
        List<Integer> yearList=universityExService.getRecentlyEnrollInfoByYear(schoolId,areaId);
        for (Integer year:yearList){
            List<EnrollInfo>  enrollInfos=iUniversityService.getEnrollInfoByYear(year,schoolId,areaId);
            EnrollResponseDto lastEnrollResponseDto=new EnrollResponseDto();
            lastEnrollResponseDto.setTitle(year+"招生情况");
            lastEnrollResponseDto.setInfos(enrollInfos);
            enrollResponseDtoList.add(lastEnrollResponseDto);
        }
        map.put("enrollInfo",enrollResponseDtoList);
        return map;
    }

    /**
     *获取院校招生计划
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
        // 获取有数据的最近三年
        List<Integer> yearList=universityExService.getRecentlyPlanInfosByYear(schoolId,batch,areaId);
        for (Integer year:yearList){
            EntrollPlan entrollPlan=new EntrollPlan();
            List<PlanInfo> planInfos=iUniversityService.getPlanInfosByYear(year,schoolId,batch,areaId);
            entrollPlan.setTitle(year+"年招生计划");
            entrollPlan.setPlanInfos(planInfos);
            entrollPlans.add(entrollPlan);
        }
        entrollPlanDto.setEnrollPlan(entrollPlans);

        return  entrollPlanDto;
    }

    /**
     * 招生简章
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getEntroIntro",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEntroIntro(@RequestParam(value = "universityId",required = true)long universityId){
        Map<String,Object> entroIntroMap= Maps.newHashMap();
        String entroIntro=iUniversityService.getUniversityEnrollIntro(String.valueOf(universityId));
        entroIntroMap.put("entroIntro",entroIntro);
        return entroIntroMap;
    }

    /**
     * 院校简介
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getUniversityIntro",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUniversityIntro(@RequestParam(value = "universityId",required = true)long universityId){
        Map<String,Object> universityIntroMap= Maps.newHashMap();
        String  universityIntro=iUniversityService.getUniversityIntro(String.valueOf(universityId));
        universityIntroMap.put("universityIntro",universityIntro);
        return universityIntroMap;
    }

    /**
     * 专业录取分数查询
     * @param universityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMajoredScoreLineList",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getMajoredScoreLineList(@RequestParam(value = "universityId",required = true)long universityId) throws Exception{
        long areaId=getAreaCookieValue();
        return iUniversityService.getMajoredScoreLinePojoList(universityId,areaId);
    }

    /**
     * 获取开设专业
     * @param universityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOpenMajoredPojoList",method = RequestMethod.GET)
    @ResponseBody
    public List<OpenMajoredPojo> getOpenMajoredPojoList(@RequestParam(value = "universityId",required = true)long universityId) throws Exception{
        long areaId=getAreaCookieValue();
        return iUniversityService.getOpenMajoredPojoList(universityId,areaId);
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
