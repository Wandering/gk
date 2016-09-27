package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.dao.selcourse.ISelMajorDao;
import cn.thinkjoy.gk.pojo.EduLevelNumberPojo;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SelSubjectNumberPojo;
import cn.thinkjoy.gk.pojo.SelUniversityPojo;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.selcourse.ISelMajorService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/21.
 */
@Service
@RequestMapping("selMajorController")
public class SelMajorController {

    @Autowired
    private ISelMajorService iSelMajorService;

    @RequestMapping("getSubjectNumber")
    @ResponseBody
    public Map<String,Object> getSubjectNumber(@RequestParam("majorCode")String majorCode){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("majorCode",majorCode);
        returnMap.put("majorNumberList", iSelMajorService.selectSubjectNumber(map));
        return returnMap;
    }

    @RequestMapping("getUniversityBatchNumber")
    @ResponseBody
    public Map<String,Object> getUniversityBatchNumber(@RequestParam("majorCode")String majorCode){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("majorCode",majorCode);
        returnMap.put("schoolbatchNumberList", iSelMajorService.selectUniversityBatchNumber(map));
        return returnMap;
    }

    @RequestMapping("getMajorList")
    @ResponseBody
    public Map<String,Object> getMajorList(@RequestParam("majorCode")String majorCode,
                                           @RequestParam(value="offset",required = false,defaultValue = "0")String offset,
                                           @RequestParam(value="rows",required = false,defaultValue = "10")String rows){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("majorCode",majorCode);
        map.put("offset",offset);
        map.put("rows",rows);
        returnMap.put("majorList", iSelMajorService.selectMajorList(map));
        returnMap.put("count",iSelMajorService.selectMajorListCount(map));
        return returnMap;
    }

    @ResponseBody
    @ApiDesc(owner = "杨国荣",returnDesc = "统计各院校专业课程情况")
    @RequestMapping(value = "/getMajorSubStatistics",method = RequestMethod.GET)
    public List<SelSubjectNumberPojo> getMajorSubStatistics(){

        return iSelMajorService.getMajorSubStatistics();
    }

    @ResponseBody
    @ApiDesc(owner = "杨国荣",returnDesc = "查询各专业薪资排名")
    @RequestMapping(value = "/getMajorSalary",method = RequestMethod.GET)
    public List<MajoredDto> getMajorSalary(@RequestParam("pageNo") int pageNo,
                                           @RequestParam("pageSize") int pageSize){

        return iSelMajorService.getMajorSalary(pageNo,pageSize);
    }

}
