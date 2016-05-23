package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkHot;
import cn.thinkjoy.zgk.remote.IGkPhoneService;
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
@RequestMapping(value = "/phone")
public class GkPhoneController extends BaseApiController<GkHot> {

    @Autowired
    private IGkPhoneService gkPhoneService;

    /**
     * 获取热点摘要列表 四个
     * @return
     */
    @ApiDesc(value = "获取招办电话列表", owner = "杨永平")
    @RequestMapping(value = "/getGkPhoneList.do",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkHot> getGkPhoneList(@ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                    @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "4",required = false) Integer rows){
        Map<String,Object> map = new HashMap<>();
        return gkPhoneService.getGkPhoneList(map,page,rows);
    }

}
