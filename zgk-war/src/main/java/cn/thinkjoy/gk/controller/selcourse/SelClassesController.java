package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.gk.pojo.MajorBatchNumberPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.Bases;
import cn.thinkjoy.gk.pojo.MajorTop3Pojo;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import cn.thinkjoy.gk.util.RedisIsSaveUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.gk.util.UserContext;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 * 按课程查询专业
 */
@Controller
@RequestMapping("selClassesController")
public class SelClassesController {

    @Autowired
    private ISelClassesService iSelClassesService;

    @RequestMapping("getSchoolAndMajorNumber")
    @ResponseBody
    public Map<String,Object> getSchoolAndMajorNumber(@RequestParam("subject1")String subject1,@RequestParam("subject2")String subject2,@RequestParam("subject3")String subject3){
        String key = "zgk_university:_subject1:" + subject1 + "_subject2:" + subject2 + "_subject3" + subject3 +":getSchoolAndMajorNumber";
        Object object = RedisIsSaveUtil.existsKey(key);
        Map<String,Object> returnMap=new HashMap<>();
        if (object==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("subject1", subject1);
            map.put("subject2", subject2);
            map.put("subject3", subject3);
            object=iSelClassesService.selectSchoolAndMajorNumberBySubjects(map);
            RedisUtil.getInstance().set(key, JSONArray.toJSON(object));
        }
        returnMap.put("number", object);
        return returnMap;
    }

    @RequestMapping("getMajorNumberByBatch")
    @ResponseBody
    public Map<String,Object> getMajorNumberByBatch(@RequestParam("subject1")String subject1,@RequestParam("subject2")String subject2,@RequestParam("subject3")String subject3){
        String key = "zgk_university:_subject1:" + subject1 + "_subject2:" + subject2 + "_subject3" + subject3 +":getMajorNumberByBatch";
        Object object = RedisIsSaveUtil.existsKey(key);
        Map<String, Object> returnMap = new HashMap<>();
        if (object == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("subject1", subject1);
            map.put("subject2", subject2);
            map.put("subject3", subject3);
            object=iSelClassesService.selectMajorNumberByBatch(map);
            RedisUtil.getInstance().set(key, JSONArray.toJSON(object));
        }
        returnMap.put("batchList", object);
        return returnMap;
    }

    @RequestMapping("getMajorList")
    @ResponseBody
    public Map<String,Object> getMajorList(@RequestParam(value="subject1")String subject1,
                                           @RequestParam(value="subject2")String subject2,
                                           @RequestParam(value="subject3")String subject3,
                                           @RequestParam(value="queryId",required = false)String queryId,
                                           @RequestParam(value="queryType",required = false)String queryType,
                                           @RequestParam(value="provinceCode",required = false)String provinceCode,
                                           @RequestParam(value="batch",required = false)String batch,
                                           @RequestParam(value="type",required = false)String type,
                                           @RequestParam(value="offset",required = false,defaultValue = "0")String offset,
                                           @RequestParam(value="rows",required = false,defaultValue = "10")String rows){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("subject1",subject1);
        map.put("subject2",subject2);
        map.put("subject3",subject3);
        map.put("offset",offset);
        map.put("rows",rows);
        if(StringUtils.isNotBlank(queryType)) {
            if(queryType.equals("1")) {
                map.put("universityId", queryId);
            }
            if(queryType.equals("2")) {
                map.put("majorId", queryId);
            }
        }
        if(StringUtils.isNotBlank(provinceCode)) {
            map.put("provinceCode", provinceCode);
        }
        if(StringUtils.isNotBlank(batch)) {
            map.put("batch", batch);
        }
        if(StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        UserAccountPojo userAccountPojo = UserContext.getCurrentUser();
        //尝试获取用户信息
        if(userAccountPojo==null){
            map.put("isLeftJoin",false);
        }else {
            //加入用户登录,将用户ID和用户收藏信息加入
            map.put("userId",userAccountPojo.getId());
            map.put("isLeftJoin",true);
        }
        returnMap.put("majorList", iSelClassesService.selectMajorList(map));
        returnMap.put("count",iSelClassesService.selectMajorListCount(map));
        return returnMap;
    }

    @RequestMapping("getUniversityOrMajorByWords")
    @ResponseBody
    public Map<String,Object> getUniversityOrMajorByWords(@RequestParam(value="queryValue")String queryValue){
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("universityOrMajorList", iSelClassesService.selectUniversityOrMajorByWords(queryValue));
        return returnMap;
    }

    @RequestMapping("getMajorTop3")
    @ResponseBody
    public Map<String,Object> getMajorTop3(){
        Map<String,Object> returnMap=new HashMap<>();
        Bases[] bases=iSelClassesService.selectMajorTop3();
        returnMap.put("majorTop3",bases);
        return returnMap;
    }



}
