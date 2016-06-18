package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.service.IZGK3in7Service;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.remote.IGkPhoneService;
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
public class GkSubjectByMajorController {

    @Autowired
    private IZGK3in7Service zgk3in7Service;



    @RequestMapping(value = "/querySubjectByMajor",method = RequestMethod.GET)
    @ResponseBody
    public Object querySubjectByMajor( @RequestParam(defaultValue = "1",required = false) Integer page,
                                    @RequestParam(defaultValue = "10",required = false) Integer rows,
                                 String[] subjectItem,String areaId,String unversityName){
        Map<String,Object> map = new HashMap<>();
        if(subjectItem!=null) {
            map.put("subjectItemList", combineAlgorithm(subjectItem));
        }
        if(areaId!=null){
            map.put("areaId", areaId);
        }
        if(unversityName!=null){
            map.put("unversityName", unversityName);
        }
        return doPage(map,page,rows);
    }

    public BizData4Page createBizData4Page(Map<String, Object> conditions,Integer curPage,Integer rows){
        List mainData = zgk3in7Service.queryPage(conditions);
        int records = zgk3in7Service.count(conditions);
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

    private List<List<String>> combineAlgorithm(String[] str){

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));


        List<String> mapList=null;

        List<List<String>> lists=new ArrayList<>();
        for (int i = 1; i <= nBit; i++) {
            mapList=new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    mapList.add(str[j]);
                }
            }
            lists.add(mapList);
        }
        return lists;
    }
}
