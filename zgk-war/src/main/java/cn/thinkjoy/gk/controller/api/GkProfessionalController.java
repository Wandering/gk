package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.domain.GkProfessional;
import cn.thinkjoy.zgk.dto.GkProfessionDTO;
import cn.thinkjoy.zgk.remote.IGkProfessionalService;
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
@RequestMapping(value = "/professional")
public class GkProfessionalController extends BaseApiController<GkProfessionDTO> {

    @Autowired
    private IGkProfessionalService gkProfessionalService;

    /**
     * 获取视频列表
     * @return
     */
    @ApiDesc(value = "获取职业列表", owner = "杨永平")
    @RequestMapping(value = "/getProfessionalList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkProfessional> getProfessionalList(@ApiParam(param="queryparam", desc="是否拉取视频详情") @RequestParam(required = false) String queryparam,
                                                    @ApiParam(param="professionTypeId", desc="大类型") @RequestParam(required = false) Integer professionTypeId,
                                                    @ApiParam(param="professionSubTypeId", desc="子类型") @RequestParam(required = false) Integer professionSubTypeId,
                                               @ApiParam(param="page", desc="当前页数") @RequestParam(defaultValue = "1",required = false) Integer page,
                                               @ApiParam(param="rows", desc="每页行数") @RequestParam(defaultValue = "4",required = false) Integer rows){
        Map<String,Object> map = new HashMap<>();
        QueryUtil.setMapOp(map,"professionName","like",queryparam);
        QueryUtil.setMapOp(map,"professionTypeId","=",professionTypeId);
        QueryUtil.setMapOp(map,"professionSubTypeId","=",professionSubTypeId);
        return gkProfessionalService.getProfessionalList(map, page, rows);
    }

    /**
     * 获取详情
     * @return
     */
    @ApiDesc(value = "根据ID获取职业详情", owner = "杨永平")
    @RequestMapping(value = "/getProfessionalInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkProfessionDTO getProfessionalInfo(@ApiParam(param="id", desc="主键ID",required = true) @RequestParam("id") String id){
        this.idIsNull(id);
        GkProfessionDTO gkProfessionDTO=gkProfessionalService.getProfessionalInfo(id);
        return isNull(gkProfessionDTO);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "获取职业分类", owner = "杨永平")
    @RequestMapping(value = "/getProfessionCategory",method = RequestMethod.GET)
    @ResponseBody
    public void getProfessionCategory(@ApiParam(param="pid", desc="父Id",required = true) @RequestParam("pid") String pid){
        this.idIsNull(pid);
        Map<String,Object> map = new HashMap<>();
        gkProfessionalService.getProfessionCategory(map);
    }
}
