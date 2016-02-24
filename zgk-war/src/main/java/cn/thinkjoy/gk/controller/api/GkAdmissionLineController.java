package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkAdmissionLine;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 历年分数线controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/admissionline")
public class GkAdmissionLineController extends BaseApiController {

    /**行默认**/
    private static int ROWSDEFAULT=4;
    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;

    /**
     * 获取批次线分页方法
     * @return
     */
    @ApiDesc(value = "获取分数线", owner = "杨永平")
    @RequestMapping(value = "/getGkAdmissionLineList.do",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkAdmissionLine> getGkAdmissionLineList(@ApiParam(param="queryparam", desc="标题模糊查询",required = false) @RequestParam(required = false) String queryparam,
                                               @ApiParam(param="year", desc="年份",required = false) @RequestParam(required = false) String year,
                                               @ApiParam(param="areaId", desc="页数",required = false) @RequestParam(required = false) String areaId,
                                               @ApiParam(param="property", desc="院校特征",required = false) @RequestParam(required = false) String property,
                                               @ApiParam(param="batch", desc="批次",required = false) @RequestParam(required = false) Integer batch,
                                               @ApiParam(param="type", desc="科类",required = false) @RequestParam(defaultValue = "1",required = false) Integer type,
                                               @ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                               @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "10",required = false) Integer rows){

        //默认参数设置
        Map<String,Object> map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
//        年份
        if(year!=null &&!"".equals(year)) {
            QueryUtil.setMapOp(map, "enrollingyear", "=", year);
        }
//        院校名称 模糊
        if(queryparam!=null &&!"".equals(queryparam)) {
            QueryUtil.setMapOp(map, "universityname", "like", "%" + queryparam + "%");
        }
//        地区
        if(areaId!=null &&!"".equals(areaId)) {
            QueryUtil.setMapOp(map, "universityareaid", "=", areaId);
        }
//        特征
        if(property!=null &&!"".equals(property)) {
            QueryUtil.setMapOp(map, "universityproperty", "like", "%" + property + "%");
        }
//        批次
        if(batch!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }
//        文史/理工
         QueryUtil.setMapOp(map, "entype", "=", type);

        return gkAdmissionLineService.getGkAdmissionLineList(map,page,rows);
    }
    /**
     * 获取批次线分页方法
     * @return
     */
    @ApiDesc(value = "获取年份", owner = "杨永平")
    @RequestMapping(value = "/getYears.do",method = RequestMethod.GET)
    @ResponseBody
    public List getYears(){
        List list = new ArrayList();
        Calendar a=Calendar.getInstance();
        int year=a.get(Calendar.YEAR);
//        list.add(year);
        list.add(year-1);
        list.add(year-2);
        list.add(year-3);
        list.add(year-4);
        return list;
    }

}
