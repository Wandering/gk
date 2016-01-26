package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.remote.IUniversityService;
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
public class PredictController {

    @Autowired
    private IUniversityService universityService;

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
    @RequestMapping(value = "/predictProbability",method = RequestMethod.POST)
    @ResponseBody
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
        List universityList = universityService.getUniversityByName(name);
        if(universityList.size()==0 || universityList.size()>1)
        {
            throw new BizException("error", "请输入正确的院校名称!");
        }
        String uName = ((Map<String, String>)universityList.get(0)).get("label");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("batch","一本");
        resultMap.put("probability", 3);
        resultMap.put("universityName", uName);
        resultMap.put("score", score);
        resultMap.put("type", type);
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("year","2015");
        map.put("universityName",uName);
        map.put("batch","一本");
        map.put("enrollingNumber","28");
        map.put("lowScore","635");
        map.put("avgScore","665");
        map.put("highScore","715");
        list.add(map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("year","2014");
        map2.put("universityName",uName);
        map2.put("batch","一本");
        map2.put("enrollingNumber","28");
        map2.put("lowScore","635");
        map2.put("avgScore","665");
        map2.put("highScore","715");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("year","2013");
        map3.put("universityName",uName);
        map3.put("batch","一本");
        map3.put("enrollingNumber","28");
        map3.put("lowScore","635");
        map3.put("avgScore","665");
        map3.put("highScore","715");
        list.add(map3);
        resultMap.put("historyList", list);
        return resultMap;
    }

    /**
     * 预测院校接口
     * @param score
     */
    @RequestMapping(value = "/predictSchoolList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> predictSchoolList(@RequestParam(value = "score", defaultValue = "") int score,
                                                 @RequestParam(value = "type", defaultValue = "") String type)
    {
        if(score<=0 || score > 999)
        {
            throw new BizException("error", "请输入正确的分数!");
        }
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("id","1");
        map.put("universityName","北京大学");
        map.put("province","北京");
        map.put("rank","1");
        map.put("feature","985,211,研,国,自");
        list.add(map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("id","2");
        map2.put("universityName","清华大学");
        map2.put("province","北京");
        map2.put("rank","2");
        map2.put("feature","985,211,研,国,自");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("id","3");
        map3.put("universityName","中国人民大学");
        map3.put("province","北京");
        map3.put("rank","3");
        map3.put("feature","985,211,研,国,自");
        list.add(map3);
        Map<String, Object> mp1 = new HashMap<>();
        mp1.put("count", list.size());
        mp1.put("list", list);
        mp1.put("star", 4);
        resultMap.put("4", mp1);
        List<Map<String, String>> list2 = new ArrayList<>();
        list2.add(map);
        list2.add(map2);
        list2.add(map3);
        Map<String, Object> mp2 = new HashMap<>();
        mp2.put("count", list2.size());
        mp2.put("list", list2);
        mp2.put("star", 3);
        resultMap.put("3", mp2);
        List<Map<String, String>> list3 = new ArrayList<>();
        list3.add(map);
        list3.add(map2);
        list3.add(map3);
        Map<String, Object> mp3 = new HashMap<>();
        mp3.put("count", list3.size());
        mp3.put("list", list3);
        mp3.put("star", 2);
        resultMap.put("2", mp3);
        List<Map<String, String>> list4 = new ArrayList<>();
        list4.add(map);
        list4.add(map2);
        list4.add(map3);
        Map<String, Object> mp4 = new HashMap<>();
        mp4.put("count", list4.size());
        mp4.put("list", list4);
        mp4.put("star", 1);
        resultMap.put("1", mp4);
        return resultMap;
    }
}
