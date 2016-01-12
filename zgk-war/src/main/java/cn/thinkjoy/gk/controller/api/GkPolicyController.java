package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.GkPolicy;
import cn.thinkjoy.zgk.domain.GkSchedule;
import cn.thinkjoy.zgk.dto.GkScheduleDTO;
import cn.thinkjoy.zgk.remote.IGkPolicyService;
import cn.thinkjoy.zgk.remote.IGkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/policy")
public class GkPolicyController extends BaseApiController{

    /**行默认**/
    private static int ROWSDEFAULT=4;
    /**类型默认**/
    private static int TYPEDEFAULT=0;
    @Autowired
    IGkPolicyService gkPolicyService;

    /**
     * 获取日程摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取政策解读摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getPolicyList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page getScheduleList(@ApiParam(param="type", desc="标题模糊查询")String queryparam,
                                               @ApiParam(param="page", desc="页数")Integer page,
                                               @ApiParam(param="rows", desc="每页条数")Integer rows){
        //默认参数设置
        Map<String,Object> map = new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","createDate");
        map.put("sortBy","desc");
        QueryUtil.setMapOp(map,"title","LIKE","%"+queryparam+"%");
        return gkPolicyService.getGkPolicyList(map,page,rows);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "根据主键获取政策解读详情", owner = "杨永平")
    @RequestMapping(value = "/getPolicyInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkPolicy getPolicyInfo(@ApiParam(param="id", desc="高考日程主键ID")@RequestParam("id")String id){
        return gkPolicyService.getGkPolicyInfo(id);
    }
}
