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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param name
     * @param score
     * @param name
     * @return
     */
    @RequestMapping(value = "/predictProbability",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> predictProbability(@RequestParam(value = "universityName", defaultValue = "") String name,
                                                  @RequestParam(value = "score", defaultValue = "") String score,
                                                  @RequestParam(value = "type", defaultValue = "") String type)
    {

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

    public Integer predictionAchievement(){
        /**当前分数**/
        Integer currScores=null;
        /**三年平均分**/
        Integer averageScore=null;
        /**三年平均分分差**/
        Integer difference=null;

//        1+B/A 四颗星
        Integer Star4=averageScore/difference+1;
//        [1,1+B/A]三颗星
        Integer Star3=averageScore/difference;
//        [1-B/A,1-2B/A] 二颗星
//        Integer Star3=averageScore/difference;
//        [1-2B/A,1-B/A] 一颗星

//        C/（A+B）>1+B/A
        Integer analogueScale=currScores/(averageScore+difference);

//        switch (){
//        }

        return analogueScale;
    }

//    public Integer predictionAchievement(){
//
////        C=数个人分
////
////        A=院校平均录取最低分
////
////        B=录取平均分分差
//
//        /**当前分数**/
//        Integer currScores=null;
//        /**院校平均录取最低分**/
//        Integer averageScore=null;
//        /**录取平均分分差**/
//        Integer difference=null;
//
////        C/（A+B）>1+B/A
//        Integer analogueScale=currScores/(averageScore+difference);
//
//        return analogueScale;
//    }

}
