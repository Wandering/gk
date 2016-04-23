package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkAreaBatch;
import cn.thinkjoy.zgk.dto.GkVideoDTO;
import cn.thinkjoy.zgk.remote.IGkAreaBatchService;
import cn.thinkjoy.zgk.remote.IGkVideoService;
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
 * 高考视频controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/areabatch")
public class GkAreaBatchController extends BaseApiController<GkAreaBatch> {

    @Autowired
    private IGkAreaBatchService gkAreaBatchService;


    /**
     * 获取详情
     * @return
     */
    @ApiDesc(value = "根据区域ID获取详情页面", owner = "杨永平")
    @RequestMapping(value = "/getGkAreaBatchInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkAreaBatch getGkAreaBatchInfo(@ApiParam(param="areaId", desc="区域id",required = true) @RequestParam("areaId") String areaId){
        this.idIsNull(areaId);
        GkAreaBatch gkAreaBatch=gkAreaBatchService.getGkAreaBatchInfo(new HashMap<String, Object>(),areaId);
        return isNull(gkAreaBatch);
    }

}
