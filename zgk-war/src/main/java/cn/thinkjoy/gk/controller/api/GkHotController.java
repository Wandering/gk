package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkHot;
import cn.thinkjoy.zgk.remote.IGkHotService;
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
 * 高考热点/头条controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/gkhot")
public class GkHotController extends BaseApiController<GkHot> {

    @Autowired
    IGkHotService gkHotService;

    /**
     * 获取热点摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取热点摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getGkHotList.do",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkHot> getGkHotList(@ApiParam(param="type", desc="热点类型",required = false) @RequestParam(defaultValue = "0",required = false) Integer type,
                                    @ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                    @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "4",required = false) Integer rows){
        Map<String,Object> map = new HashMap<>();
        QueryUtil.setMapOp(map, "type", "=", type);
        return gkHotService.getGkHotList(map,page,rows);
    }

    /**
     * 获取热点详情
     * @return
     */
    @ApiDesc(value = "获取热点详细信息", owner = "杨永平")
    @RequestMapping(value = "/getGkHotInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public GkHot getGkHotInfo(@ApiParam(param="id", desc="热点主键ID",required = true) @RequestParam String id){
        this.idIsNull(id);
        GkHot gkHot=gkHotService.getGkHotInfo(new HashMap<String, Object>(),id);
        return isNull(gkHot);
    }
}
