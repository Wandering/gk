package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkPolicy;
import cn.thinkjoy.zgk.remote.IGkPolicyService;
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
 * 政策解读controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/policy")
public class GkPolicyController extends BaseApiController<GkPolicy> {

    /**行默认**/
    private static int ROWSDEFAULT=4;
    /**类型默认**/
    private static int TYPEDEFAULT=0;
    @Autowired
    IGkPolicyService gkPolicyService;

    /**
     * 政策解读摘要列表
     * @return
     */
    @ApiDesc(value = "获取政策解读摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getPolicyList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkPolicy> getPolicyList(@ApiParam(param="queryparam", desc="标题模糊查询") @RequestParam(required = false) String queryparam,
                                               @ApiParam(param="page", desc="页数") @RequestParam(defaultValue = "1",required = false) Integer page,
                                               @ApiParam(param="rows", desc="每页条数") @RequestParam(defaultValue = "4",required = false) Integer rows){
        //默认参数设置
        Map<String,Object> map = new HashMap<>();
        if(queryparam!=null && !"".equals(queryparam)) {
            QueryUtil.setMapOp(map, "title", "LIKE", "%" + queryparam + "%");
        }
        return gkPolicyService.getGkPolicyList(map,page,rows);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "根据主键获取政策解读详情", owner = "杨永平")
    @RequestMapping(value = "/getPolicyInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkPolicy getPolicyInfo(@ApiParam(param="id", desc="高考日程主键ID",required = true) @RequestParam("id")String id){
        this.idIsNull(id);
        GkPolicy gkPolicy=gkPolicyService.getGkPolicyInfo(new HashMap<String, Object>(),id);
        return isNull(gkPolicy);
    }
}
