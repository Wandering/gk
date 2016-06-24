package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.SubjectsUtil;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IUserFavorites3in7Service;
import cn.thinkjoy.gk.service.IZGK3in7Service;
import cn.thinkjoy.zgk.domain.BizData4Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 高考热点/头条controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/favorites")
public class GkFavoritesController {

    @Autowired
    private IUserFavorites3in7Service userFavorites3in7Service;



    @RequestMapping(value = "/insertFavorites",method = RequestMethod.POST)
    @ResponseBody
    public Object insertFavorites(@RequestParam String majorId,@RequestParam String type,String[] subjects){
        Map<String,Object> map = new HashMap<>();
        map.put("majorId",majorId);
        map.put("createDate",System.currentTimeMillis());
        if(type.equals(2)&&subjects==null){
            throw new BizException("error","课程组合不能为空！");
        }
        if(subjects!=null){
            Arrays.sort(subjects);
            StringBuffer buffer=new StringBuffer();
            for(String string:subjects){
                buffer.append(string).append(" - ");
            }
            if(buffer.length()>0) {
                buffer.delete(buffer.length()-3, buffer.length());
            }
            map.put("subjects",buffer.toString());
        }
        map.put("type",type);
        return userFavorites3in7Service.insertFavorites(map);
    }


    @RequestMapping(value = "/getFavoritesByMajor",method = RequestMethod.GET)
    @ResponseBody
    public Object getFavoritesByMajor(){
        Map<String,Object> map = new HashMap<>();
        return userFavorites3in7Service.getFavoritesByMajor(map);
    }

    @RequestMapping(value = "/getFavoritesBySubjectKey",method = RequestMethod.GET)
    @ResponseBody
    public Object getFavoritesBySubjectKey(){
        Map<String,Object> map = new HashMap<>();
        return userFavorites3in7Service.getFavoritesBySubjectKey(map);
    }

    @RequestMapping(value = "/getFavoritesBySubject",method = RequestMethod.GET)
    @ResponseBody
    public Object getFavoritesBySubject( @RequestParam(defaultValue = "1",required = false) Integer page,
                                         @RequestParam(defaultValue = "10",required = false) Integer rows,
                                         @RequestParam String subjects){
        Map<String,Object> map = new HashMap<>();
        map.put("subjects",subjects);
        return doPage(map,page,rows);
    }

    @RequestMapping(value = "/removeFavorites",method = RequestMethod.POST)
    @ResponseBody
    public Object removeFavorites(@RequestParam String id,@RequestParam String type){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("type",type);
        return userFavorites3in7Service.deleteById(map);
    }
    @RequestMapping(value = "/removeBySubjects",method = RequestMethod.POST)
    @ResponseBody
    public Object removeBySubjects(@RequestParam String subjects){
        Map<String,Object> map = new HashMap<>();
        map.put("subjects",subjects);
        return userFavorites3in7Service.deleteBySubjects(map);
    }

    public BizData4Page createBizData4Page(Map<String, Object> conditions,Integer curPage,Integer rows){
        List mainData = userFavorites3in7Service.getFavoritesBySubject(conditions);
        int records = userFavorites3in7Service.count(conditions);
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
}
