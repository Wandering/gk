package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.domain.BizStatusEnum;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.annotation.VipMethonTag;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.util.UserContext;
import cn.thinkjoy.zgk.remote.IUniversityService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by liusven on 16/1/13.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/predict")
public class PredictController extends BaseApiController {

    @Autowired
    private IForecastService forecastService;
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
        return universityService.getUniversityByName(name);
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
        if(score<=0 || score > 999)
        {
            throw new BizException("error", "请输入正确的分数!");
        }
        if(null==name || "".equals(name))
        {
            throw new BizException("error", "请输入院校名称!");
        }
        List<Map<String, String>> universityList = universityService.getUniversityByName(name);
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
        Map<String, Object> params = new HashMap<>();
        params.put("universityName", uName);
        params.put("score", score);
        params.put("type", type);
        params.put("areaId", getAreaId());
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = universityService.getPredictProbability(params);
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
//        if(!flag){
//            throw new BizException(ERRORCODE.HASPREDICT.getCode(),ERRORCODE.HASPREDICT.getMessage());
//        }
        //end
        if(score<=0 || score > 999)
        {
            throw new BizException("error", "请输入正确的分数!");
        }
        if(null==name || "".equals(name))
        {
            throw new BizException("error", "请输入院校名称!");
        }
        List<Map<String, String>> universityList = universityService.getUniversityByName(name);
        if(universityList.size()==0)
        {
            throw new BizException("error", "请输入正确的院校名称!");
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
                }
            }
        }
        if("".equals(uName))
        {
            throw new BizException("error", "请输入正确的院校名称!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("universityName", uName);
        params.put("score", score);
        params.put("type", type);
        params.put("areaId", getAreaId());
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = universityService.getPredictProbability(params);
        } catch (Exception e) {
            setBatch(score, type, resultMap);
            resultMap.put("probability", 0);
            resultMap.put("type", type);
        }
        resultMap.put("universityName", uName);
        resultMap.put("score", score);

        addFrecast(resultMap,uId,uName,score,type);


        //预测完成，添加计数
        userInfoExService.updateUserCanTargetByUid(Long.parseLong(this.getAccoutId()));
        //end
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
            resultMap = universityService.getPredictUniversityInfo(params);
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
            if(!("-".equals(minScore)||"0".equals(minScore))){
                t1++;
                i1+=Integer.valueOf(minScore);
            }
            if(!("-".equals(avgScore)||"0".equals(avgScore))){
                t2++;
                i2+=Integer.valueOf(avgScore);
            }
        }
        Map<String,Object>  dataMap=new HashMap<>();
        dataMap.put("lowestScore",i1/t1);
        dataMap.put("averageScore",i2/t2);
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
}