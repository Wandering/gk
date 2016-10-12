package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.dao.selcourse.ISelMajorDao;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.selcourse.ISelMajorService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import cn.thinkjoy.gk.util.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/21.
 */
@Service
@RequestMapping("selMajorController")
public class SelMajorController extends ZGKBaseController{

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
                                           @RequestParam(value = "province",required = false)String province,
                                           @RequestParam(value = "batch",required = false)String batch,
                                           @RequestParam(value = "subName",required = false)String subName,
                                           @RequestParam(value="offset",required = false,defaultValue = "0")String offset,
                                           @RequestParam(value="rows",required = false,defaultValue = "10")String rows){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("majorCode",majorCode);
        if(StringUtils.isNotBlank(province)) {
            map.put("province",province);
        }
        if(StringUtils.isNotBlank(batch)) {
            map.put("batch",batch);
        }
        if(StringUtils.isNotBlank(subName)) {
            map.put("subName",subName);
        }
        map.put("offset",offset);
        map.put("rows",rows);
        UserAccountPojo userAccountPojo = UserContext.getCurrentUser();
        //尝试获取用户信息
        if(userAccountPojo==null){
            map.put("isLeftJoin",false);
        }else {
            //加入用户登录,将用户ID和用户收藏信息加入
            map.put("userId",userAccountPojo.getId());
            map.put("isLeftJoin",true);
        }
        returnMap.put("majorList", iSelMajorService.selectMajorList(map));
        returnMap.put("count",iSelMajorService.selectMajorListCount(map));
        return returnMap;
    }


    @RequestMapping("getMajorByWords")
    @ResponseBody
    public Map<String,Object> getMajorByWords(@RequestParam(value="queryValue")String queryValue){
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("majorList", iSelMajorService.selectMajorByWords(queryValue));
        return returnMap;
    }

    // TODO 数据缺失，暂时使用静态数据
    @RequestMapping("getMajorById")
    @ResponseBody
    public Map<String,Object> getMajorById(@RequestParam(value="majorId")String majorId){
        Map<String,Object> returnMap=new HashMap<>();

        SelMajorPojo selMajorPojo=new SelMajorPojo();
        selMajorPojo.setMajorId(majorId);
        selMajorPojo.setMajorName("车辆工程");
        selMajorPojo.setFiveSalary("￥2343");
        selMajorPojo.setJobRank("3");
        List<SexPercentPojo> sexPercentList=new ArrayList<>();
        SexPercentPojo sexPercentPojo=new SexPercentPojo();
        sexPercentPojo.setSexName("男");
        sexPercentPojo.setPercent("80%");
        sexPercentList.add(sexPercentPojo);
        SexPercentPojo sexPercentPojo2=new SexPercentPojo();
        sexPercentPojo2.setSexName("女");
        sexPercentPojo2.setPercent("20%");
        sexPercentList.add(sexPercentPojo2);
        selMajorPojo.setSexPercent(sexPercentList);
        returnMap.put("major", selMajorPojo);
//        returnMap.put("major", iSelMajorService.selectMajorById(majorId));
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
