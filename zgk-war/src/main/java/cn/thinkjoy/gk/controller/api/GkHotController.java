package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.common.QueryUtil;
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
public class GkHotController extends BaseApiController<GkHot>{

    /**行默认**/
    private static int ROWSDEFAULT=4;
    /**类型默认**/
    private static int TYPEDEFAULT=0;
    @Autowired
    IGkHotService gkHotService;

    /**
     * 获取热点摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取热点摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getGkHotList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkHot> getGkHotList(@ApiParam(param="type", desc="热点类型") @RequestParam("type") Integer type,
                                    @ApiParam(param="page", desc="页数") @RequestParam("page") Integer page,
                                    @ApiParam(param="rows", desc="每页条数") @RequestParam("rows") Integer rows){
        Map<String,Object> map = new HashMap<>();
        //默认参数设置
        map.put("groupOp","and");
        map.put("orderBy","createDate");
        map.put("sortBy","desc");
        if(type==null) {
            QueryUtil.setMapOp(map, "type", "=", TYPEDEFAULT);
        }
        if(rows==null) {
            rows=ROWSDEFAULT;
        }
        return gkHotService.getGkHotList(map,page,rows);
    }

    /**
     * 获取热点详情
     * @return
     */
    @ApiDesc(value = "获取热点详细信息", owner = "杨永平")
    @RequestMapping(value = "/getGkHotInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkHot getGkHotInfo(@ApiParam(param="id", desc="热点主键ID",required = true) @RequestParam("id")String id){
        if("".equals(id)){
            throw new BizException(ERRORCODE.IDISNOTNULL.getCode(),ERRORCODE.IDISNOTNULL.getMessage());
        }
        GkHot gkHot=gkHotService.getGkHotInfo(id);
        if(gkHot==null){
            throw new BizException(ERRORCODE.RESOURCEISNULL.getCode(),ERRORCODE.RESOURCEISNULL.getMessage());
        }
        return isNull(gkHot);
    }
}
