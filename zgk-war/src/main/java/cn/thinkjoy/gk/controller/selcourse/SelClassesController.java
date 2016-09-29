package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("subject1",subject1);
        map.put("subject2",subject2);
        map.put("subject3",subject3);
        returnMap.put("number", iSelClassesService.selectSchoolAndMajorNumberBySubjects(map));
        return returnMap;
    }

    @RequestMapping("getMajorNumberByBatch")
    @ResponseBody
    public Map<String,Object> getMajorNumberByBatch(@RequestParam("subject1")String subject1,@RequestParam("subject2")String subject2,@RequestParam("subject3")String subject3){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("subject1",subject1);
        map.put("subject2",subject2);
        map.put("subject3",subject3);
        returnMap.put("batchList", iSelClassesService.selectMajorNumberByBatch(map));
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


}
