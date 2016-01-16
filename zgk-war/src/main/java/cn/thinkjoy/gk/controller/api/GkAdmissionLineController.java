package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 历年分数线controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/admissionline")
public class GkAdmissionLineController extends BaseApiController{

    /**行默认**/
    private static int ROWSDEFAULT=4;
    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;

    /**
     * 获取批次线分页方法
     * @return
     */
    @ApiDesc(value = "获取分数线", owner = "杨永平")
    @RequestMapping(value = "/getGkAdmissionLineList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page getGkAdmissionLineList(@ApiParam(param="queryparam", desc="标题模糊查询") @RequestParam("queryparam") String queryparam,
                                               @ApiParam(param="year", desc="年份") @RequestParam("year") String year,
                                               @ApiParam(param="name", desc="院校名称") @RequestParam("name") String name,
                                               @ApiParam(param="areaId", desc="页数") @RequestParam("areaId") String areaId,
                                               @ApiParam(param="property", desc="院校特征") @RequestParam("property") String property,
                                               @ApiParam(param="batch", desc="批次") @RequestParam("batch") Integer batch,
                                               @ApiParam(param="type", desc="科类") @RequestParam("type") Integer type,
                                               @ApiParam(param="page", desc="页数") @RequestParam("page") Integer page,
                                               @ApiParam(param="rows", desc="每页条数") @RequestParam("rows") Integer rows){
        //默认参数设置
        Map<String,Object> map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
//        年份
        if(!"".equals(year)) {
            QueryUtil.setMapOp(map, "enrollingyear", "=", year);
        }
//        院校名称 模糊
        if(!"".equals(name)) {
            QueryUtil.setMapOp(map, "universityname", "like", "%" + name + "%");
        }
//        地区
        if(!"".equals(areaId)) {
            QueryUtil.setMapOp(map, "universityareaid", "=", areaId);
        }
//        特征
        if(!"".equals(property)) {
            QueryUtil.setMapOp(map, "universityproperty", "like", "%" + property + "%");
        }
//        批次
        if(type!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", type);
        }
//        文史/理工
        if(type!=null) {
            QueryUtil.setMapOp(map, "enrollinguniversityMajorType", "=", type);
        }
//        设置默认值
        this.setDefault(rows,ROWSDEFAULT);
        return gkAdmissionLineService.getGkAdmissionLineList(map,page,rows);
    }

}
