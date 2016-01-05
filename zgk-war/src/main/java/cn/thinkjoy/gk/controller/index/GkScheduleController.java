package cn.thinkjoy.gk.controller.index;

import cn.thinkjoy.gk.common.GkControllerUtil;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.domain.GkHot;
import cn.thinkjoy.zgk.domain.GkSchedule;
import cn.thinkjoy.zgk.dto.GkScheduleDTO;
import cn.thinkjoy.zgk.remote.IGkHotService;
import cn.thinkjoy.zgk.remote.IGkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/schedule")
public class GkScheduleController {

    @Autowired
    IGkScheduleService gkScheduleService;

    /**
     * 获取日程摘要列表 四个
     * @return
     */
    @RequestMapping(value = "/getScheduleList",method = RequestMethod.GET)
    @ResponseBody
    public List<GkScheduleDTO> getScheduleList(String areaId,Integer num){
        areaId=GkControllerUtil.setDefault(areaId,"330000");
        num=GkControllerUtil.setDefault(num,3);
        return gkScheduleService.getScheduleList(areaId, num);
    }

    /**
     * 获取日程详情
     * @return
     */
    @RequestMapping(value = "/getScheduleInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkSchedule getScheduleInfo(String id){
        return gkScheduleService.getScheduleInfo(id);
    }
}
