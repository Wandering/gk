package cn.thinkjoy.gk.controller.index;

import cn.thinkjoy.ITestService.ITestService;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.zgk.domain.GkHot;
import cn.thinkjoy.zgk.remote.IGkHotService;
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
@RequestMapping(value = "/gkhot")
public class GkHotController {

    @Autowired
    IGkHotService gkHotService;

    /**
     * 获取热点摘要列表 四个
     * @return
     */
    @RequestMapping(value = "/getGkHotList",method = RequestMethod.GET)
    @ResponseBody
    public List<GkHot> getGkHotList(String areaId,String type,Integer num){
        return gkHotService.getGkHotList(areaId,type,num);
    }

    /**
     * 获取热点详情
     * @return
     */
    @RequestMapping(value = "/getGkHotInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkHot getGkHotInfo(String id){
        return gkHotService.getGkHotInfo(id);
    }
}
