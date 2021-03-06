package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高考日程controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/schedule")
public class GkScheduleController extends BaseApiController<GkSchedule> {

    @Autowired
    IGkScheduleService gkScheduleService;

    /**
     * 获取日程摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取高考日程摘要列表", owner = "杨永平")
    @RequestMapping(value = "/getScheduleList.do",method = RequestMethod.GET)
    @ResponseBody
    public List<GkScheduleDTO> getScheduleList(@ApiParam(param="rows", desc="条数",required = false) @RequestParam(defaultValue = "3",required = false) Integer rows,
                                               @ApiParam(param="hasList", desc="是否展示列表",required = false) @RequestParam(defaultValue = "true",required = false) Boolean hasList,
                                               @ApiParam(param="month", desc="月份",required = false) @RequestParam(required = false) Integer month,
                                               @ApiParam(param="isIndex", desc="是否事首页",required = false) @RequestParam(required = false) Boolean isIndex,
                                               @ApiParam(param="scheduleRows", desc="条数",required = false) @RequestParam(required = false) Integer scheduleRows){
        Map<String,Object> map = new HashMap<>();
        if(scheduleRows!=null) {
            map.put("scheduleRows", scheduleRows);
        }
        if(month!=null) {
            map.put("showMonth", month);
        }
        if(isIndex){
            map.put("startMonth","2015-9");
        }
        map.put("boo", hasList);
        return gkScheduleService.getScheduleList(map,rows);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "根据主键获取高考日程详情", owner = "杨永平")
    @RequestMapping(value = "/getScheduleInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public GkSchedule getScheduleInfo(@ApiParam(param="id", desc="高考日程主键ID",required = true) @RequestParam("id") String id){
        this.idIsNull(id);
        GkSchedule gkSchedule=gkScheduleService.getScheduleInfo(new HashMap<String, Object>(),id);
        return isNull(gkSchedule);
    }

//    private String getCurrGKYear(){
//        Calendar calendar = Calendar.getInstance();
//        Integer year=Calendar.YEAR;
//        Integer month=Calendar.MONTH;
//        if(year<year){
//
//        }
//    }
}
