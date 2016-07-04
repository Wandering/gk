package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.domain.BizStatusEnum;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.annotation.VipMethonTag;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.domain.Forecast;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.ModelUtil;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.gk.service.IUniversityService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by liusven on 16/1/13.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/predict")
public class PredictController extends BaseApiController {
    public int TOKEN_EXPIRE_TIME = 60 * 60;
    @Autowired
    private IForecastService forecastService;

    // TODO gaokao360-admin提供的 universityService
    // TODO 后续优化:全部用本工程内的 universityService 和 universityExService
    @Autowired
    private cn.thinkjoy.zgk.remote.IUniversityService gk360UniversityService;

    @Autowired
    private IUniversityService universityService;
    @Autowired
    private IUserInfoExService userInfoExService;


    /**
     * 根据名称模糊查询院校接口
     * @param name
     * @return
     */
    @RequestMapping(value = "/university",method = RequestMethod.GET)
    @ResponseBody
    public List getUniversityByName(@RequestParam(value = "universityName", defaultValue = "") String name)
    {
        return gk360UniversityService.getUniversityByName(name);
    }

    /**
     * 录取难易预测接口
     * @param name
     * @param score
     * @param name
     * @return
     */
    @RequestMapping(value = "/predictProbability")
    @ResponseBody
    @VipMethonTag
    public Map<String, Object> predictProbability(@RequestParam(value = "universityName") String name,
                                                  @RequestParam(value = "score") int score,
                                                  @RequestParam(value = "type") String type)
    {
        if(score <= 0 || score > 999)
        {
            ModelUtil.throwException(ERRORCODE.SCORE_ERROR);
        }
        if(null == name || "".equals(name))
        {
            ModelUtil.throwException(ERRORCODE.SCHOOL_NAME_ERROR);
        }

        List<Map<String, String>> universityList = gk360UniversityService.getUniversityByName(name);
        if(universityList.size()==0)
        {
            throw new BizException("error", "请输入正确的院校名称!");
        }
        String uName = "";
        if(universityList.size()==1)
        {
            uName = universityList.get(0).get("label");
        }
        if(universityList.size()>1)
        {
            for (Map<String, String> map : universityList) {
                if(name.equals(map.get("label")))
                {
                    uName = map.get("label");
                }
            }
        }
        if("".equals(uName))
        {
            throw new BizException("error", "请输入正确的院校名称!");
        }

//        University university = (University) gk360UniversityService.findOne("name",name);
//        if(university == null){
//            ModelUtil.throwException(ERRORCODE.SCHOOL_NAME_ERROR);
//        }

        Map<String, Object> params = new HashMap<>();
        params.put("universityName", name);
        params.put("score", score);
        params.put("type", type);
        params.put("areaId", getAreaId());
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = gk360UniversityService.getPredictProbability(params);
        } catch (Exception e) {
            setBatch(score, type, resultMap);
            resultMap.put("probability", 0);
            resultMap.put("type", type);
        }
        resultMap.put("universityName", name);
        resultMap.put("score", score);
        return resultMap;
    }

    /**
     * 目标定位
     * @param name
     * @param score
     * @param name
     * @return
     */
    @RequestMapping(value = "/tallyPredictProbability")
    @ResponseBody
    @VipMethonTag
    public Map<String, Object> tallyPredictProbability(@RequestParam(value = "universityName") String name,
                                                       @RequestParam(value = "score") int score,
                                                       @RequestParam(value = "type") String type)
    {
        //判断是否今天定位过
        boolean flag = userInfoExService.isPredictByUid(Long.parseLong(this.getAccoutId()));
        if(!flag){
            ModelUtil.throwException(ERRORCODE.HASPREDICT);
        }
        //end
        if(score <= 0 || score > 999)
        {
            ModelUtil.throwException(ERRORCODE.SCORE_ERROR);
        }
        if(null == name || "".equals(name))
        {
            ModelUtil.throwException(ERRORCODE.SCHOOL_NAME_ERROR);
        }

        List<Map<String, String>> universityList = gk360UniversityService.getUniversityByName(name);
        if(universityList.size() == 0)
        {
            ModelUtil.throwException(ERRORCODE.SCHOOL_NAME_ERROR);
        }
        String uName = "";
        String uId = "";
        if(universityList.size()==1)
        {
            uName = universityList.get(0).get("label");
            uId = String.valueOf(universityList.get(0).get("id"));
        }
        if(universityList.size()>1)
        {
            for (Map<String, String> map : universityList) {
                if(name.equals(map.get("label")))
                {
                    uName = map.get("label");
                    uId = String.valueOf(universityList.get(0).get("id"));
                }
            }
        }
        if("".equals(uName))
        {
            throw new BizException("error", "请输入正确的院校名称!");
        }

//        University university = (University) universityService.findOne("name",name);
//        if(university == null){
//            ModelUtil.throwException(ERRORCODE.SCHOOL_NAME_ERROR);
//        }
        Map<String,Object> resultMap=getUniversityPredict(name,score,type);

        //保存预测结果
        addFrecast(resultMap,uId,name,score,type);


        //由于压测需要，特别添加用户Id=363，账号18291920831用户为无限目标定位用户
        if (Long.parseLong(this.getAccoutId())!=363L) {
            //预测完成，添加计数
            userInfoExService.updateUserCanTargetByUid(Long.parseLong(this.getAccoutId()));
        }
        //end
        //附加分差和预测人数
        getWanting(resultMap);
        setFillingNumber(resultMap);
        //附加分差和预测人数-end
        return resultMap;
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    private void setBatch(@RequestParam(value = "score") int score, @RequestParam(value = "type") String type, Map<String, Object> resultMap) {
        if("1".equals(type))
        {
            if(score>=626)
            {
                resultMap.put("batch", "一本");
            }
            else if(score>=472)
            {
                resultMap.put("batch", "二本");
            }
            else if(score>=400)
            {
                resultMap.put("batch", "三本");
            }else{
                resultMap.put("batch", "专科");
            }
        }
        else
        {
            if(score>=605)
            {
                resultMap.put("batch", "一本");
            }
            else if(score>=428)
            {
                resultMap.put("batch", "二本");
            }
            else if(score>=380)
            {
                resultMap.put("batch", "三本");
            }else{
                resultMap.put("batch", "专科");
            }
        }
    }

    /**
     * 预测院校接口
     * @param score
     * @param type
     */
    @RequestMapping(value = "/predictSchoolList")
    @ResponseBody
    @VipMethonTag
    public Map<String, Object> predictSchoolList(@RequestParam(value = "score", defaultValue = "") int score,
                                                 @RequestParam(value = "type", defaultValue = "") String type)
    {
        if(score<=0 || score > 999)
        {
            throw new BizException("error", "请输入正确的分数!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("score", score);
        params.put("type", type);
        params.put("areaId", getAreaId());
        Map<String, Object> resultMap = new LinkedHashMap<>();
        try {
            resultMap = gk360UniversityService.getPredictUniversityInfo(params);

            for(Integer i=4;i>0;i--) {
                if(resultMap.get(i.toString())==null)continue;
                Map<String, Object> dataMap = (Map<String, Object>) resultMap.get(i.toString());
                if(dataMap.get("list")==null)continue;
                List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                for (Map<String, Object> map1 : list) {
                    Map<String, Object> propertyMap = new HashMap();
                    if (StringUtils.isNotEmpty((String) map1.get("feature"))) {
                        String[] propertys = map1.get("feature").toString().split(",");
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
                    map1.put("propertys", propertyMap);
                }

            }

        } catch (Exception e) {
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, Object> mp1 = new HashMap<>();
            mp1.put("count", list.size());
            mp1.put("list", list);
            mp1.put("star", 4);
            resultMap.put("4", mp1);
            List<Map<String, String>> list2 = new ArrayList<>();
            Map<String, Object> mp2 = new HashMap<>();
            mp2.put("count", list2.size());
            mp2.put("list", list2);
            mp2.put("star", 3);
            resultMap.put("3", mp2);
            List<Map<String, String>> list3 = new ArrayList<>();
            Map<String, Object> mp3 = new HashMap<>();
            mp3.put("count", list3.size());
            mp3.put("list", list3);
            mp3.put("star", 2);
            resultMap.put("2", mp3);
            List<Map<String, String>> list4 = new ArrayList<>();
            Map<String, Object> mp4 = new HashMap<>();
            mp4.put("count", list4.size());
            mp4.put("list", list4);
            mp4.put("star", 1);
            resultMap.put("1", mp4);
        }
        return resultMap;
    }


    private boolean addFrecast(Map<String, Object> resultMap,String uId,String uName,int score,String type){
        //保存预测情况
        List<Map<String, Object>> list1=(List<Map<String, Object>>)resultMap.get("historyList");
        Iterator<Map<String, Object>> iterator=list1.iterator();
        Map<String, Object> map1=null;
        int i1=0;
        int i2=0;
        int t1=0;
        int t2=0;
        while (iterator.hasNext()){
            map1= iterator.next();
            String minScore =map1.get("minScore").toString();
            String avgScore =map1.get("avgScore").toString();
            if(isLegalScore(minScore)){
                t1++;
                i1+=Integer.valueOf(minScore);
            }
            if(isLegalScore(avgScore)){
                t2++;
                i2+=Integer.valueOf(avgScore);
            }
        }
        Map<String,Object>  dataMap=new HashMap<>();
        int sum1=0;
        if(i1!=0 && t1!=0) {
            sum1 = i1 / t1;
        }
        int sum2=0;
        if(i2!=0 && t2!=0) {
            sum2 = i2 / t2;
        }
        dataMap.put("lowestScore",sum1);
        dataMap.put("averageScore",sum2);
        dataMap.put("achievement",score);
        dataMap.put("universityName",uName);
        dataMap.put("universityId",uId);
        dataMap.put("typeId",type);
        //TODO   支持多对象保存
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

    private boolean isLegalScore(String avgScoreStr) {
        return StringUtils.isNotEmpty(avgScoreStr) && isNumber(avgScoreStr) && !"0".equals(avgScoreStr);
    }


    /**
     * 根据分数获取目标定位院校预测
     * @param uName
     * @param score
     * @param type
     * @return
     */
    private Map<String,Object> getUniversityPredict(String uName,int score,String type){
        Map<String, Object> params = new HashMap<>();
        params.put("universityName", uName);
        params.put("score", score);
        params.put("type", type);
        params.put("areaId", getAreaId());
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = gk360UniversityService.getPredictProbability(params);
        } catch (Exception e) {
            setBatch(score, type, resultMap);
            resultMap.put("probability", 0);
            resultMap.put("type", type);
        }
        resultMap.put("universityName", uName);
        resultMap.put("score", score);
        return resultMap;
    }


    /**
     * 获取当前用户最后一次预测结果
     * @return
     */
    @RequestMapping(value = "/predictResults")
    @ResponseBody
    @VipMethonTag
    public Map<String, Object> predictResults(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",this.getAccoutId());
        cn.thinkjoy.gk.domain.Forecast forecast = (cn.thinkjoy.gk.domain.Forecast) forecastService.queryOne(map, "lastModDate", SqlOrderEnum.DESC);
        if(forecast==null){
            throw new BizException(ERRORCODE.RESOURCEISNULL.getCode(),ERRORCODE.RESOURCEISNULL.getMessage());
        }
        Map<String,Object> resultMap=getUniversityPredict(forecast.getUniversityName(),forecast.getAchievement(),forecast.getTypeId().toString());
        getWanting(resultMap);
        setFillingNumber(resultMap);
        return resultMap;
    }


    private void getWanting(Map<String,Object> forecastMap){
        Map<String,Object> lastforecastMap=getLastoFrecast();
        if(lastforecastMap!=null && lastforecastMap.containsKey("averageScore")) {

            Integer averageScore = Integer.parseInt(lastforecastMap.get("averageScore").toString());
            Integer lowestScore = Integer.parseInt(lastforecastMap.get("lowestScore").toString());
            Integer achievement = Integer.parseInt(lastforecastMap.get("achievement").toString());
            Integer wanting = 0;
            if (averageScore == 0) {
                if (lowestScore == 0) {
                    forecastMap.put("wanting", "-");
                } else {
                    wanting = lowestScore - achievement;
                    forecastMap.put("wanting", wanting);
                    forecastMap.put("countType", "lowestScore");
                }
            } else {
                wanting = averageScore - achievement;
                forecastMap.put("wanting", wanting);
                forecastMap.put("countType", "averageScore");
            }
        }
    }

    private void setFillingNumber(Map<String,Object> forecastMap){
        Map<String,Object> lastforecastMap=getLastoFrecast();
        if(lastforecastMap!=null && lastforecastMap.containsKey("universityId")) {
            forecastMap.put("fillingNumber", forecastService.getFillingNumber(lastforecastMap.get("universityId").toString()));
        }
    }

    public Map<String,Object> getLastoFrecast(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",this.getAccoutId());
        cn.thinkjoy.gk.domain.Forecast forecast=(cn.thinkjoy.gk.domain.Forecast)forecastService.queryOne(map,"lastModDate", SqlOrderEnum.DESC);
//        Forecast forecast=(Forecast)null;
        Map<String,Object> forecastMap=(Map<String,Object>) JSON.parse(JSON.toJSONString(forecast));
        return forecastMap;
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
            list = gk360UniversityService.getDataDictListByType("FEATURE");
            for (Map<String, Object> map : list) {
                propertysMap.put(map.get("dictId").toString(), map.get("name").toString());
            }
            redisRepository.set(key, JSON.toJSON(propertysMap), TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return propertysMap;
    }


    @ApiDesc(value = "查询用户历史目标定位详情", owner = "杨国荣")
    @RequestMapping(value = "/queryPredictHistory",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryPredictHistory(){

        List<Forecast> forecasts = forecastService.findList(
                "userId",
                getAccoutId(),
                "createDate",
                SqlOrderEnum.DESC);

        List<Map<String,Object>> maps = Lists.newArrayList();

        if(forecasts.size() == 0){
            return maps;
        }

        Forecast forecastTemp = forecasts.get(0);

        for(Forecast forecast : forecasts){
            Map<String,Object> map = Maps.newHashMap();
            map.put("requestTime",forecast.getCreateDate());
            map.put("lowestScore",forecastTemp.getLowestScore());
            map.put("userScore",forecast.getAchievement());
            maps.add(map);
        }

        return maps;
    }
}