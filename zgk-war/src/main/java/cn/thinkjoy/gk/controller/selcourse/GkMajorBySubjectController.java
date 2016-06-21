package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IZGK3in7Service;
import cn.thinkjoy.zgk.domain.BizData4Page;
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
 * 高考热点/头条controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/subjectByMajor")
public class GkMajorBySubjectController {

    @Autowired
    private IZGK3in7Service zgk3in7Service;



    @RequestMapping(value = "/getUniversityByArea",method = RequestMethod.GET)
    @ResponseBody
    public Object getUniversityByArea(@RequestParam(defaultValue = "1",required = false) Integer page,
                                      @RequestParam(defaultValue = "10",required = false) Integer rows,
                                      String areaId,String universityName){
        Map<String,Object> map=new HashMap<>();
        map.put("universityName",universityName);
        map.put("areaId",areaId);
        return createBizData4Page(map,page,rows);
    }

    public BizData4Page createBizData4Page(Map<String, Object> conditions,Integer curPage,Integer rows){
        List mainData = zgk3in7Service.getUniversityByArea(conditions);
        int records = zgk3in7Service.countUniversity(conditions);
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(curPage);
        bizData4Page.setRecords(records);
        int total = records / rows;
        int mod = records % rows;
        int pagesize=0;
        if(mainData!=null) {
            pagesize = mainData.size();
        }
        if(mod > 0){
            total = total + 1;
        }
        bizData4Page.setPagesize(pagesize);
        bizData4Page.setTotal(total);
        return bizData4Page;
    }

    protected BizData4Page doPage(Map<String, Object> conditions,Integer page,Integer rows){
        conditions.put("offset",(page - 1) * rows);
        conditions.put("rows",rows);
        conditions.put("sortBy","asc");
        conditions.put("orderBy", "id");
        return createBizData4Page(conditions,page,rows);
    }


    @RequestMapping(value = "/getMajorByUniversityId",method = RequestMethod.GET)
    @ResponseBody
    public Object getMajorByUniversityId(@RequestParam String universityId,String majorName){
        Map<String,Object> map = new HashMap<>();
        map.put("universityId",universityId);
        map.put("majorName",majorName);
        return zgk3in7Service.getMajorByUniversityId(map);
    }

    @RequestMapping(value = "/getSubjectByMajor",method = RequestMethod.GET)
    @ResponseBody
    public Object getSubjectByMajor(@RequestParam String majorId){
        Map<String,Object> map = new HashMap<>();
        map.put("majorId",majorId);
        return zgk3in7Service.getSubjectByMajor(map);
    }

}
