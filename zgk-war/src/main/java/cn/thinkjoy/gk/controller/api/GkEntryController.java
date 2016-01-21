package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkEntry;
import cn.thinkjoy.zgk.domain.GkSchedule;
import cn.thinkjoy.zgk.dto.GkScheduleDTO;
import cn.thinkjoy.zgk.remote.IGkEntryService;
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
 * 高考日程controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/entry")
public class GkEntryController extends BaseApiController<GkEntry> {

    @Autowired
    IGkEntryService gkEntryService;

    /**
     * 获取日程摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取高考日程摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getGkEntryList.do",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page getGkEntryList(@ApiParam(param="rows", desc="条数",required = false) @RequestParam(defaultValue = "5",required = false) Integer rows,
                                               @ApiParam(param="page", desc="页",required = false) @RequestParam(defaultValue = "1",required = false) Integer page){
        Map<String,Object> map = new HashMap<>();
        return gkEntryService.getGkEntryList(map,page,rows);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "根据主键获取高考词条详情", owner = "杨永平")
    @RequestMapping(value = "/getGkEntryInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public GkEntry getGkEntryInfo(@ApiParam(param="id", desc="主键ID",required = true) @RequestParam("id") String id){
        this.idIsNull(id);
        GkEntry gkEntry = gkEntryService.getGkEntryInfo(id);
        return isNull(gkEntry);
    }
}
