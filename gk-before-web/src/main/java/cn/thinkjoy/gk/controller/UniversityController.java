package cn.thinkjoy.gk.controller;

/**
 * Created by wpliu on 15/9/25.
 */

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.dto.UniversityDTO;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.query.UniversityQuery;
import cn.thinkjoy.gk.service.*;
import cn.thinkjoy.gk.util.ConditionsUtil;
import cn.thinkjoy.gk.util.RedisIsSaveUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.dto.UniversityPlanChartDTO;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 学院信息控制类
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/university")
public class UniversityController extends ZGKBaseController {
    public int TOKEN_EXPIRE_TIME = 60 * 60;
    private static final Logger LOGGER = LoggerFactory.getLogger("SPECLOGGER");
//    public static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

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

    @Autowired
    private IUniversityInfoService universityInfoService;
    /**
     * 智高考院校信息列表
     *
     * @return
     */
    @RequestMapping(value = "/getRemoteUniversityList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUniversityList(@RequestParam(value = "universityName", required = false) String universityName,
                                                 @RequestParam(value = "areaid", required = false) String areaid,//省份
                                                 @RequestParam(value = "type", required = false) Integer type,//院校分类
                                                 @RequestParam(value = "educationLevel", required = false) Integer educationLevel,//学历层次
                                                 @RequestParam(value = "property", required = false) String property,//院校特征
                                                 @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                 @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("groupOp", "and");
        if (StringUtils.isNotBlank(universityName))
            ConditionsUtil.setCondition(condition, "name", "like", "%" + universityName + "%");
        if (StringUtils.isNotBlank(areaid))
            ConditionsUtil.setCondition(condition, "areaid", "=", areaid);
        if (type != null)
            ConditionsUtil.setCondition(condition, "type", "=", type.toString());
        if (educationLevel != null)
            ConditionsUtil.setCondition(condition, "educationLevel", "=", educationLevel.toString());
        if (StringUtils.isNotBlank(property))
            ConditionsUtil.setCondition(condition, "property", "like", "%" + property + "%");
        String orederBy = "rank";
        String sqlOrderEnumStr = "asc";
        Map<String, Object> selectorpage = Maps.newHashMap();
        selectorpage.put("photoUrl", 1);
        selectorpage.put("id", 1);
        selectorpage.put("name", 1);
        selectorpage.put("property", 1);
        selectorpage.put("province", 1);
        selectorpage.put("rank", 1);
        selectorpage.put("subjection", 1);
        selectorpage.put("typeName", 1);
        selectorpage.put("url", 1);
        long start=System.currentTimeMillis();
//        List<Map<String, Object>> getUniversityList = iremoteUniversityService.getUniversityList(condition, offset, rows, orederBy, sqlOrderEnumStr, selectorpage);
//        int count = iremoteUniversityService.getUniversityCount(condition);
        List<UniversityDTO> getUniversityList =  universityExService.getUniversityList(condition, offset, rows, orederBy, sqlOrderEnumStr, selectorpage);
        int count = universityExService.getUniversityCount(condition);
        long end=System.currentTimeMillis();
        long dubbo=end-start;
        LOGGER.info("dubbo time:"+dubbo);
        //如果用户已登录
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        for (UniversityDTO university : getUniversityList) {
            String[] propertys = new String[1];
            if (university.getProperty() != null) {
                propertys[0] = university.getProperty().toString();
            }
            String[] propertys2 = null;
            Map<String, Object> propertyMap = new HashMap();
            if (StringUtils.isNotEmpty(university.getProperty().toString())) {
                propertys2 = propertys[0].toString().split(",");
                Map<String, Object> propertysMap = getPropertys();

                for (String str : propertys2) {
                    Iterator<String> propertysIterator = propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()) {
                        String key = propertysIterator.next();
                        String value = propertysMap.get(key).toString();
                        if (str.indexOf(value) > -1) {
                            propertyMap.put(key, value);
                        }
                    }
                }
            }

            university.setPropertys(propertyMap);
            university.setIsCollect(0);
            if (null != userAccountPojo) {
                long userId = userAccountPojo.getId();
                //需要在收藏表中拼接收藏状态字段
                Map<String, Object> param = Maps.newHashMap();
//                param.put("userId",54);
                param.put("userId", userId);
                param.put("projectId", university.getId());
                param.put("type", 1);
                university.setIsCollect(userCollectExService.isCollect(param));
            }
        }
        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("universityList", getUniversityList);
        returnMap.put("count", count);
        return returnMap;
    }

    @RequestMapping(value = "getRemoteUniversityById", method = RequestMethod.GET)
    @ResponseBody
    public Object getUniversityDTOById(@RequestParam(value = "universityId", required = true) long universityId) {
        Object object = null;
        String key1 = "zgk_uy:" + universityId;
        Object object1 = RedisIsSaveUtil.existsKey(key1);
        if (object1 == null || "null".equals(object1)) {
            Map<String, Object> map = (Map<String, Object>) iremoteUniversityService.getUniversityById(universityId);
            Map<String, Object> propertyMap = new HashMap();
            if (StringUtils.isNotEmpty(map.get("property").toString())) {
                String[] propertys = map.get("property").toString().toString().split(",");
                Map<String, Object> propertysMap = getPropertys();

                for (String str : propertys) {
                    Iterator<String> propertysIterator = propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()) {
                        String key = propertysIterator.next();
                        String value = propertysMap.get(key).toString();
                        if (str.indexOf(value) > -1) {
                            propertyMap.put(key, value);
                        }
                    }
                }
            }
            map.put("propertys", propertyMap);
            RedisUtil.getInstance().set(key1, JSON.toJSONString(map));
            return map;
        }
        return JSON.parseObject(object1.toString(), Object.class);
    }

    /**
     * 获取开设专业列表
     *
     * @param universityId
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "getRemoteUniversityMajorListByUniversityId", method = RequestMethod.GET)
    @ResponseBody
    public Map getUniversityMajorListByUniversityId(@RequestParam(value = "universityId", required = true) long universityId,
                                                    @RequestParam(value = "majorFeature", required = true) String majorFeature,
                                                    @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                    @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) throws UnsupportedEncodingException {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("groupOp", "and");
        ConditionsUtil.setCondition(condition, "universityId", "=", String.valueOf(universityId));
        Map<String, Object> selectorpage = Maps.newHashMap();
        selectorpage.put("majorId", 1);
        selectorpage.put("majorName", 1);
        selectorpage.put("educationLevel", 1);
        selectorpage.put("gainDegree", 1);
//        selectorpage.put("majorRank",1);
        selectorpage.put("salaryRank", 1);
        selectorpage.put("jobRank", 1);

        //此接口读取静态数据，不做缓存处理
        List ll = iremoteUniversityService.queryPage("universityMajorExService", condition, offset, rows, "majorRank", "asc", selectorpage);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("universityId", String.valueOf(universityId));
        paramMap.put("majorFeature", majorFeature);
        Map<String, Object> condition2 = Maps.newHashMap();
        condition2.put("groupOp", "and");
        ConditionsUtil.setCondition(condition2, "id", "=", String.valueOf(universityId));
        Map<String, Object> selectorpage2 = Maps.newHashMap();
        selectorpage2.put("featureMajor", 1);
        List featureMajorList;
        RedisRepository resUtil = RedisUtil.getInstance();
        String resKey = "zgk_uy:" + universityId + "_ot:" + offset + "_rs:" + rows +"_mf"+majorFeature+ ":major";
        if (resUtil.exists(resKey)) {
            featureMajorList = (List) JSONUtils.parse(resUtil.get(resKey).toString());
        } else {
            featureMajorList = universityInfoService.getUniversityMajors(paramMap);
            resUtil.set(resKey, JSONUtils.toJSONString(featureMajorList));
        }
        Map<String, List> returnMap = Maps.newHashMap();
        returnMap.put("majorList", ll);
        returnMap.put("featureMajorList", featureMajorList);

        return returnMap;
    }

    /**
     * 招生计划列表获取
     *
     * @param universityId
     * @param year
     * @param batch
     * @param universityMajorType
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "getUniversityMajorEnrollingPlanList", method = RequestMethod.GET)
    @ResponseBody
    public List getUniversityMajorEnrollingPlanList(@RequestParam(value = "universityId", required = true) long universityId,
                                                    @RequestParam(value = "year", required = false) String year,//年份
                                                    @RequestParam(value = "batch", required = false) Integer batch,//批次
                                                    @RequestParam(value = "universityMajorType", required = false) String universityMajorType,//科类
                                                    @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                    @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("universityId", String.valueOf(universityId));
        if (StringUtils.isNotBlank(year)) {
            condition.put("year", year);
        }
        if (batch != null) {
            condition.put("batch", batch);
        }
        if (StringUtils.isNotBlank(universityMajorType)) {
            condition.put("majorType", universityMajorType);
        }
        condition.put("areaId",getAreaId().toString());
        condition.put("offset", offset);
        condition.put("rows", rows);
        List ll = iremoteUniversityService.getUniversityMajorEnrollingPlanList(condition);
        return ll;
    }

    /**
     * 院校专业录取数据
     *
     * @param universityId
     * @param year
     * @param batch
     * @param universityMajorType
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "getUniversityMajorEnrollingSituationList", method = RequestMethod.GET)
    @ResponseBody
    public List getUniversityMajorEnrollingSituationList(@RequestParam(value = "universityId", required = true) long universityId,
                                                         @RequestParam(value = "year", required = true) String year,//年份
                                                         @RequestParam(value = "batch", required = true) int batch,//批次
                                                         @RequestParam(value = "universityMajorType", required = true) String universityMajorType,//科类
                                                         @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                         @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {

        String userKey = request.getParameter("userKey");
        String key = "zgk_pe:" + userKey + "_uy:" + universityId + "_yr:" + year + "_me:" + universityMajorType + "_bh:" + batch + "_ot:" + offset + "_rs:" + rows + ":enrollingSituationDetailsList";
        Object object = RedisIsSaveUtil.existsKey(key);
        if (object == null) {
            Map<String, Object> condition = Maps.newHashMap();
            condition.put("groupOp", "and");
            ConditionsUtil.setCondition(condition, "universityId", "=", String.valueOf(universityId));
            ConditionsUtil.setCondition(condition, "year", "=", year);
            ConditionsUtil.setCondition(condition, "batch", "=", String.valueOf(batch));
            ConditionsUtil.setCondition(condition, "areaId", "=", getAreaId().toString());
            ConditionsUtil.setCondition(condition, "majorType", "=", universityMajorType);
            Map<String, Object> selectorpage = Maps.newHashMap();
            selectorpage.put("majorId", 1);
            selectorpage.put("majorName", 1);
            selectorpage.put("majorType", 1);
            selectorpage.put("year", 1);
            selectorpage.put("batch", 1);
            selectorpage.put("admissionFeature", 1);
            selectorpage.put("realEnrollingNumber", 1);
            selectorpage.put("universityMajorType", 1);
            selectorpage.put("highestScore", 1);
            selectorpage.put("highestPrecedence", 1);
            selectorpage.put("lowestScore", 1);
            selectorpage.put("lowestPrecedence", 1);
            selectorpage.put("averageScore", 1);
            selectorpage.put("averagePrecedence", 1);
            List ll = iremoteUniversityService.queryPage("universityMajorEnrollingExService", condition, offset, rows, "id", "asc", selectorpage);
            RedisUtil.getInstance().set(key, JSONArray.toJSON(ll));
            return ll;
        }
        return JSONArray.parseArray(object.toString());
    }

    /**
     * 院校录取情况图表接口
     *
     * @param universityId
     * @return
     */
    @RequestMapping(value = "queryUniversityEnrollingChart", method = RequestMethod.GET)
    @ResponseBody
    public List queryUniversityEnrollingChart(@RequestParam(value = "universityId", required = true) long universityId,
                                              @RequestParam(value = "majorType", required = true) long majorType,
                                              @RequestParam(value = "batch", required = true) long batch) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("universityId", universityId);
        map.put("majorType", majorType);
        map.put("batch", batch);
        map.put("areaId", getAreaId());
        String userKey = request.getParameter("userKey");
        String key = "zgk_pe:" + userKey + "_uy:" + universityId + "_me:" + majorType + "_bh:" + batch + ":enrollingSituationDetailsList";
        Object object = RedisIsSaveUtil.existsKey(key);
        if (object == null) {
            List list = iremoteUniversityService.queryUniversityEnrollingChart(map);
            RedisUtil.getInstance().set(key, JSONArray.toJSON(list));
            return list;
        }
        return JSONArray.parseArray(object.toString());
    }

    /**
     * 院校招生计划图表数据接口
     *
     * @param universityId
     * @return
     */
    @RequestMapping(value = "queryUniversityPlanChart", method = RequestMethod.GET)
    @ResponseBody
    public List queryUniversityPlanChart(@RequestParam(value = "universityId", required = true) long universityId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("universityId", universityId);
        List<UniversityPlanChartDTO> universityPlanChartDTOList = iremoteUniversityService.queryUniversityPlanChart(map);
        return universityPlanChartDTOList;
    }

    /**
     * 智高考获取省份列表
     *
     * @return
     */
    @RequestMapping(value = "getRemoteProvinceList", method = RequestMethod.GET)
    @ResponseBody
    public List getProvinceList() {
        String userKey = request.getParameter("userKey");
        String key = "zgk_university:" + userKey + ":getRemoteProvinceList";
        Object object = RedisIsSaveUtil.existsKey(key);
        if (object == null) {
            List list=iremoteUniversityService.getProvinceName();
            RedisUtil.getInstance().set(key, JSONArray.toJSON(list));
            return list;
        }
        return JSONArray.parseArray(object.toString());
    }

    /**
     * 智高考获取字典表通用接口
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "getRemoteDataDictList", method = RequestMethod.GET)
    @ResponseBody
    public List getDataDictList(@RequestParam(value = "type", required = true) String type) {
        String userKey = request.getParameter("userKey");
        String key = "zgk_university:" + userKey + "_type:" + type + ":getRemoteDataDictList";
        Object object = RedisIsSaveUtil.existsKey(key);
        if (object == null) {
            List list=iremoteUniversityService.getDataDictListByType(type);
            RedisUtil.getInstance().set(key, JSONArray.toJSON(list));
            return list;
        }
        return JSONArray.parseArray(object.toString());
    }

    /**
     * 获取初始化信息
     *
     * @return
     */
    @RequestMapping(value = "/getInitInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getInitInfo() {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        List<ProvincePojo> provincePojos = new ArrayList<>();
        List<Map<String, Object>> universityType = new ArrayList<>();
        List<Map<String, Object>> universityBatch = new ArrayList<>();
        List<Map<String, Object>> universityFeature = new ArrayList<>();

        List<Province> provinces = provinceService.findAll();
        for (Province province : provinces) {
            ProvincePojo provincePojo = new ProvincePojo();
            provincePojo.setId(province.getId());
            provincePojo.setName(province.getName());
            provincePojos.add(provincePojo);
        }
        Map<String, Object> map = new HashMap<>();//院校类型
        map.put("type", "PROPERTY");//院校类型
        universityType = dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityTypeList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityType.add(inMap);
//        }
        map.put("type", "BATCHTYPE");//批次类型
        universityBatch = dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityBatchList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityBatch.add(inMap);
//        }
        map.put("type", "FEATURE");//院校特征
        universityFeature = dataDictService.queryDictList(map);
//        for(UniversityDict universityDict:universityFeatureList){
//            Map<String,Object> inMap=new HashMap<>();
//            inMap.put("name",universityDict.getName());
//            inMap.put("id",universityDict.getDictId());
//            universityFeature.add(inMap);
//        }
        responseMap.put("provinces", provincePojos);
        responseMap.put("universityType", universityType);
        responseMap.put("universityBatch", universityBatch);
        responseMap.put("universityFeature", universityFeature);
        return responseMap;
    }

    /**
     * 获取大学列表
     *
     * @param universityQuery
     * @return
     */
    @RequestMapping(value = "/getUniversityList", method = RequestMethod.GET)
    @ResponseBody
    public Page<UniversityDto> getUniversityList(UniversityQuery universityQuery) {
        Page<UniversityDto> page = new Page<>();
        Map<String, Object> map = new HashMap<>();

        //构造院校批次参数 一批本科
        Integer universityBatchId = universityQuery.getUniversityBatchId();
        List<Integer> universityBatchParam = new ArrayList<>();//构造出来的参数
        if (null == universityBatchId || universityBatchId.intValue() == 0) {
            universityBatchParam = null;
        } else {
            map.put("type", "BATCHTYPE");//批次类型
            List<UniversityDict> universityBatchList = universityDictService.queryList(map, "id", "asc");
            for (UniversityDict universityDict : universityBatchList) {
                if ((universityBatchId.intValue() & universityDict.getDictId().intValue()) != 0) {
                    universityBatchParam.add(universityDict.getDictId());
                }
            }
        }

        //构造院校特征参数 985,211
        List<Integer> universityFeatureParam = new ArrayList<>();//构造出来的参数
        String universityFeature = universityQuery.getUniversityFeatureId();
        String[] strArray = universityFeature.split(",");
        if ("0".equals(universityFeature) || null == strArray) {
            universityFeatureParam = null;
        } else {
            Integer universityFeatureId = 0;
            for (String inString : strArray) {
                universityFeatureId = universityFeatureId + Integer.parseInt(inString);
            }
            map.put("type", "FEATURE");//院校特征类型
            List<UniversityDict> universityFeatureList = universityDictService.queryList(map, "id", "asc");
            for (UniversityDict universityDict : universityFeatureList) {
                if ((universityFeatureId.intValue() & universityDict.getDictId().intValue()) >= universityFeatureId.intValue()) {
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
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("province", universityQuery.getProvinceName());
        queryParams.put("universtiyType", universityQuery.getUniversityTypeName());
        queryParams.put("batch", universityBatchParam);
        queryParams.put("feature", universityFeatureParam);
        queryParams.put("start", (universityQuery.getPageNo() - 1) * universityQuery.getPageSize());
        queryParams.put("end", universityQuery.getPageSize());
        queryParams.put("searchName", universityQuery.getSearchName());
        UniversityResponseDto universityResponseDto = new UniversityResponseDto();
        List<UniversityDto> universityDtos = new ArrayList<UniversityDto>();
        universityDtos = iUniversityService.getUniversityList(queryParams);
        Integer universityCount = iUniversityService.getUniversityCount(queryParams);
        page.setList(universityDtos);
        page.setCount(universityCount);
        return page;
    }

    /**
     * 根据Id获取大学详情
     *
     * @return
     */
    @RequestMapping(value = "/getUniversityDetail", method = RequestMethod.GET)
    @ResponseBody
    public UniversityDto getUniversityDetail() {
        UniversityDto universityDto = new UniversityDto();
        String schoolId = request.getParameter("id");
        String code = request.getParameter("code");
        String batch = request.getParameter("batch");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", schoolId);
        map.put("code", code);
        map.put("batch", batch);
        universityDto = iUniversityService.getUniversityDetail(map);
        return universityDto;
    }

    /**
     * 获取院校招生情况
     *
     * @return
     */
    @RequestMapping(value = "/getEnrollInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEnrollInfo() throws Exception {
        long areaId = getAreaId();
//        long areaId=450000L;
        String schoolId = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        List<EnrollResponseDto> enrollResponseDtoList = new ArrayList<EnrollResponseDto>();
        List<Integer> yearList = universityExService.getRecentlyEnrollInfoByYear(schoolId, areaId);
        for (Integer year : yearList) {
            List<EnrollInfo> enrollInfos = iUniversityService.getEnrollInfoByYear(year, schoolId, areaId);
            EnrollResponseDto lastEnrollResponseDto = new EnrollResponseDto();
            lastEnrollResponseDto.setTitle(year + "招生情况");
            lastEnrollResponseDto.setInfos(enrollInfos);
            enrollResponseDtoList.add(lastEnrollResponseDto);
        }
        map.put("enrollInfo", enrollResponseDtoList);
        return map;
    }

    /**
     * 获取院校招生计划
     *
     * @return
     */
    @RequestMapping(value = "/getEnrollPlan", method = RequestMethod.GET)
    @ResponseBody
    public EntrollPlanDto getEnrollPlan() throws Exception {
        String schoolId = request.getParameter("id");
        String batch = request.getParameter("batch");
        long areaId = getAreaId();
        EntrollPlanDto entrollPlanDto = new EntrollPlanDto();
        List<EntrollPlan> entrollPlans = new ArrayList<EntrollPlan>();
        switch (batch) {
            case "1":
                batch = "一批本科";
                break;
            case "2":
                batch = "二批本科";
                break;
            case "3":
                batch = "三批本科";
                break;
            case "4":
                batch = "高职（专科）";
                break;
            default:
                batch = "";

        }
        // 获取有数据的最近三年
        List<Integer> yearList = universityExService.getRecentlyPlanInfosByYear(schoolId, batch, areaId);
        for (Integer year : yearList) {
            EntrollPlan entrollPlan = new EntrollPlan();
            List<PlanInfo> planInfos = iUniversityService.getPlanInfosByYear(year, schoolId, batch, areaId);
            entrollPlan.setTitle(year + "年招生计划");
            entrollPlan.setPlanInfos(planInfos);
            entrollPlans.add(entrollPlan);
        }
        entrollPlanDto.setEnrollPlan(entrollPlans);

        return entrollPlanDto;
    }

    /**
     * 招生简章
     *
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getEntroIntro", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEntroIntro(@RequestParam(value = "universityId", required = true) long universityId) {
        Map<String, Object> entroIntroMap = Maps.newHashMap();
        String entroIntro = iUniversityService.getUniversityEnrollIntro(String.valueOf(universityId));
        entroIntroMap.put("entroIntro", entroIntro);
        return entroIntroMap;
    }

    /**
     * 院校简介
     *
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getUniversityIntro", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUniversityIntro(@RequestParam(value = "universityId", required = true) long universityId) {
        Map<String, Object> universityIntroMap = Maps.newHashMap();
        String universityIntro = iUniversityService.getUniversityIntro(String.valueOf(universityId));
        universityIntroMap.put("universityIntro", universityIntro);
        return universityIntroMap;
    }

    /**
     * 专业录取分数查询
     *
     * @param universityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMajoredScoreLineList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMajoredScoreLineList(@RequestParam(value = "universityId", required = true) long universityId) throws Exception {
        long areaId = getAreaId();
        return iUniversityService.getMajoredScoreLinePojoList(universityId, areaId);
    }

    /**
     * 获取开设专业
     *
     * @param universityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOpenMajoredPojoList", method = RequestMethod.GET)
    @ResponseBody
    public List<OpenMajoredPojo> getOpenMajoredPojoList(@RequestParam(value = "universityId", required = true) long universityId) throws Exception {
        long areaId = getAreaId();
        return iUniversityService.getOpenMajoredPojoList(universityId, areaId);
    }


    /**
     * 获取院校详情
     *
     * @return
     */
    @RequestMapping(value = "/universityDetail", method = RequestMethod.GET)
    @ResponseBody
    public UniversityDetailDto universityDetail(@RequestParam(value = "code", required = false) String code,
                                                @RequestParam(value = "type", required = false) Integer type,
                                                @RequestParam(value = "year", required = false) Integer year,
                                                @RequestParam(value = "batch", required = false) String batch) throws Exception {


        if (StringUtils.isBlank(code)
                || StringUtils.isBlank(batch)
                || null == type
                || null == year) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }

//        String str = null;
//
//        try {
//            str = new String(batch.getBytes("ISO-8859-1"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),ERRORCODE.PARAM_ERROR.getMessage());
//        }
        long areaId = getAreaId();
        UniversityDetailDto universityDetailDto = null;
        try {
            universityDetailDto = universityExService.getUniversityDetail(code, batch, type, year, areaId);
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        if (universityDetailDto == null) {
            return null;
        }
        int enrollNum = universityDetailDto.getEnrollNum();

        int planNum = universityDetailDto.getPlanNum();

        int num = enrollNum - planNum;

        if (num > 0) {
            universityDetailDto.setEnrollIntro("实际招生超过计划招生数!");
        } else if (num == 0) {
            universityDetailDto.setEnrollIntro("实际招生和计划招生数相等!");
        } else {
            universityDetailDto.setEnrollIntro("计划招生超过实际招生数!");
        }

        return universityDetailDto;
    }


    private Map<String, Object> getPropertys() {
        List<Map<String, Object>> list = null;
        Map<String, Object> propertysMap = new HashMap<>();

        String key = "universityPropertys";
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            propertysMap = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {
            list = iremoteUniversityService.getDataDictListByType("FEATURE");
            for (Map<String, Object> map : list) {
                propertysMap.put(map.get("dictId").toString(), map.get("name").toString());
            }
            redisRepository.set(key, JSON.toJSON(propertysMap), TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return propertysMap;
    }


    @ResponseBody
    @ApiDesc(value = "根据关键词搜索学校基本信息",owner = "杨国荣")
    @RequestMapping(value = "/getUniversityInfoByKeywords", method = RequestMethod.GET)
    public Map<String,String> getUniversityInfoByKeywords(@RequestParam(value = "keywords") String keywords) {
        return universityExService.getUniversityInfoByKeywords(keywords);
    }
}
