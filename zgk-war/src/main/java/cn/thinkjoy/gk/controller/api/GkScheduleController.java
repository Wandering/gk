package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.domain.GkSchedule;
import cn.thinkjoy.zgk.dto.GkScheduleDTO;
import cn.thinkjoy.zgk.remote.IGkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/schedule")
public class GkScheduleController extends BaseApiController{

    @Autowired
    IGkScheduleService gkScheduleService;

    /**
     * 获取日程摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取高考日程摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getScheduleList",method = RequestMethod.GET)
    @ResponseBody
    public List<GkScheduleDTO> getScheduleList(@ApiParam(param="rows", desc="热点摘要条数") @RequestParam("rows") Integer rows){
        rows=setDefault(rows,3);
        return gkScheduleService.getScheduleList(rows);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "根据主键获取高考日程详情", owner = "杨永平")
    @RequestMapping(value = "/getScheduleInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkSchedule getScheduleInfo(@ApiParam(param="id", desc="高考日程主键ID",required = true) @RequestParam("id") String id){
        if("".equals(id)){
            throw new BizException(ERRORCODE.IDISNOTNULL.getCode(),ERRORCODE.IDISNOTNULL.getMessage());
        }
        GkSchedule gkSchedule=gkScheduleService.getScheduleInfo(id);
        if(gkSchedule==null){
            throw new BizException(ERRORCODE.RESOURCEISNULL.getCode(),ERRORCODE.RESOURCEISNULL.getMessage());
        }
        return gkSchedule;
    }
}
