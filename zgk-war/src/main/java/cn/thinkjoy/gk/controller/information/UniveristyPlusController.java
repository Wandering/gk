package cn.thinkjoy.gk.controller.information;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import cn.thinkjoy.gk.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.*;

/**
 * Created by liusven on 16/5/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/univeristyPlus")
public class UniveristyPlusController extends ZGKBaseController{

    @Autowired
    private IUniversityInfoService universityInfoService;
    private String universityEnrollingConditionsKey = "zgk_uec_key_%s_%s";
    private String majorEnrollingConditionsKey = "zgk_mec_key_%s_%s";
    private String majorPlanConditionsKey = "zgk_mpc_key_%s_%s";
    private String universityEnrollingInfoKey = "zgk_uei_key_%s_%s_%s";

    /**
     * 院校录取信息条件查询
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getUeConditions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> getUecConditions(@RequestParam(value = "universityId", required = true) Long universityId)
    {
        if(null == universityId)
        {
            throw new BizException("000005", "参数不能为空!");
        }
        RedisRepository repository = RedisUtil.getInstance();
        String areaId = getAreaId().toString();
        String key = String.format(universityEnrollingConditionsKey, universityId, areaId);
        if(repository.exists(key))
        {
            return (Map<String, List<String>>) repository.get(key);
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("areaId", areaId);
        paramMap.put("universityId", universityId.toString());
        List<Map<String, Object>> list = universityInfoService.getUniversityEnrollingConditions(paramMap);
        Map<String, List<String>> resultMap = new LinkedHashMap<>();
        for (Map<String, Object> map: list) {
            String majorType =  map.get("majorType")+"";
            List<String> batchList = resultMap.get(majorType);
            String batch = (String) map.get("batch");
            if(null == batchList)
            {
                batchList = new ArrayList<>();
            }
            batchList.add(batch);
            resultMap.put(majorType, batchList);
        }
        repository.set(key, resultMap);
        return  resultMap;
    }

    /**
     * 专业录取信息条件查询
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getMeConditions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, List<String>>> getMecConditions(@RequestParam(value = "universityId",required = true) Long universityId)
    {
        if(null == universityId)
        {
            throw new BizException("000005", "参数不能为空!");
        }
        RedisRepository repository = RedisUtil.getInstance();
        String areaId = getAreaId().toString();
        String key = String.format(majorEnrollingConditionsKey, universityId, areaId);
        if(repository.exists(key))
        {
            return (Map<String, Map<String, List<String>>>) repository.get(key);
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("areaId", String.valueOf(getAreaId()));
        paramMap.put("universityId", universityId.toString());
        List<Map<String, Object>> list = universityInfoService.getMajorEnrollingConditions(paramMap);
        Map<String, Map<String, List<String>>> resultMap = new LinkedHashMap<>();
        for (Map<String, Object> map: list) {
            String year =  map.get("year") + "";
            String majorType =  map.get("majorType") +"";
            String batch =  map.get("batch") + "";
            Map<String, List<String>> majorTypeMap = resultMap.get(year);
            if(null == majorTypeMap)
            {
                majorTypeMap = new LinkedHashMap<>();
            }
            List<String> batchList = majorTypeMap.get(majorType);
            if(null == batchList)
            {
                batchList = new ArrayList<>();
                majorTypeMap.put(majorType, batchList);
            }
            batchList.add(batch);
            resultMap.put(year, majorTypeMap);
        }
        repository.set(key, resultMap);
        return  resultMap;
    }

    /**
     * 专业招生信息条件查询
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getMpConditions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> getMpcConditions(@RequestParam(value = "universityId",required = true) Long universityId)
    {
        RedisRepository repository = RedisUtil.getInstance();
        String areaId = getAreaId().toString();
        String key = String.format(majorPlanConditionsKey, universityId, areaId);
        if(repository.exists(key))
        {
            return (Map<String, List<String>>) repository.get(key);
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("areaId", String.valueOf(getAreaId()));
        paramMap.put("universityId", universityId.toString());
        List<Map<String, Object>> list = universityInfoService.getMajorPlanConditions(paramMap);
        Map<String, List<String>> resultMap = new LinkedHashMap<>();
        for (Map<String, Object> map: list) {
            String year =  map.get("year") + "";
            String majorType =  map.get("majorType")+"";
            List<String> majorTypeList = resultMap.get(year);
            if(null == majorTypeList)
            {
                majorTypeList = new ArrayList<>();
            }
            majorTypeList.add(majorType);
            resultMap.put(year, majorTypeList);
        }
        repository.set(key, resultMap);
        return  resultMap;
    }

    /**
     * 专业招生信息条件查询
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getMpConditions2", method = RequestMethod.GET)
    @ResponseBody
    public Object getMpcConditions2(@RequestParam(value = "universityId",required = true) Long universityId)
    {
        RedisRepository repository = RedisUtil.getInstance();
        String areaId = getAreaId().toString();
        String key = String.format(majorPlanConditionsKey, universityId, areaId,"getMpcConditions2");
        if(repository.exists(key))
        {
            return (Map<String, List<String>>) repository.get(key);
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("areaId", String.valueOf(getAreaId()));
        paramMap.put("universityId", universityId.toString());
        List<Map<String, Object>> list = universityInfoService.getMajorPlanConditions2(paramMap);
        Map<String, Map<String,List<String>>> resultMap = new LinkedHashMap<>();
        for (Map<String, Object> map: list) {
            String year =  map.get("year").toString();
            String majorType =  map.get("majorType").toString();
            String batch = null;
            if (map.containsKey("batch")) {
                batch = map.get("batch").toString();
            }

            Map<String,List<String>> yearMap=resultMap.get(year);
            if (yearMap==null){
                yearMap=new LinkedHashMap<>();
            }
            List<String> majorTypeList=yearMap.get(majorType);
            if (majorTypeList==null){
                majorTypeList=new LinkedList<>();
            }
            if (StringUtils.isNotBlank(batch)) {
                majorTypeList.add(batch);
            }
            yearMap.put(majorType,majorTypeList);
            resultMap.put(year,yearMap);

        }
        repository.set(key, resultMap);
        return  resultMap;
    }


    /**
     * 院校历年录取数,map中key为年份,value为"文科录取数:理科录取数:录取总数"拼成的字符串
     * @param universityId
     * @param userKey
     * @param batch
     * @return
     */
    @RequestMapping(value = "/getUniversityEnrollingInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getUniversityEnrollingInfo(
            @RequestParam(value = "universityId",required = true) Long universityId,
            @RequestParam(value = "userKey",required = true) String userKey,
            @RequestParam(value = "batch",required = true) String batch) throws IOException {
        RedisRepository repository = RedisUtil.getInstance();
        String areaId = getAreaId().toString();
        String key = String.format(universityEnrollingInfoKey, universityId, areaId, batch);
        if(repository.exists(key))
        {
            return (List<Map<String, Object>>) repository.get(key);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("universityId", String.valueOf(universityId));
        paramMap.put("areaId", areaId);
        paramMap.put("batch", batch);
        List<Map<String,Object>> resultList = universityInfoService.getUniversityEnrollingInfo(paramMap);
        repository.set(key, resultList);
        return  resultList;
    }
}
