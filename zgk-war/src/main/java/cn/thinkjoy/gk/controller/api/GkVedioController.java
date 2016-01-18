package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.zgk.dto.GkVideoDTO;
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
@RequestMapping(value = "/video")
public class GkVedioController extends BaseApiController<GkVideoDTO> {

    @Autowired
    private IGkVideoService gkVideoService;

    /**
     * 获取视频列表
     * @return
     */
    @ApiDesc(value = "获取高考学堂和视频列表", owner = "杨永平")
    @RequestMapping(value = "/getGkVideoList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkVideoDTO> getScheduleList(@ApiParam(param="isIgnore", desc="是否拉取视频详情") @RequestParam(defaultValue = "false",required = false) Boolean boo,
                                               @ApiParam(param="page", desc="当前页数") @RequestParam(defaultValue = "1",required = false) Integer page,
                                               @ApiParam(param="rows", desc="每页行数") @RequestParam(defaultValue = "4",required = false) Integer rows){
        Map<String,Object> map = new HashMap<>();
        return gkVideoService.getGkVideoList(map,page,rows);
    }

    /**
     * 获取详情
     * @return
     */
    @ApiDesc(value = "根据ID获取视频详情页面", owner = "杨永平")
    @RequestMapping(value = "/getGkVideoInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkVideoDTO getGkVideoInfo(@ApiParam(param="id", desc="主键ID",required = true) @RequestParam("id") String id){
        if("".equals(id)){
            throw new BizException(ERRORCODE.IDISNOTNULL.getCode(),ERRORCODE.IDISNOTNULL.getMessage());
        }
        GkVideoDTO gkVideoDTO=gkVideoService.getGkVideoInfo(id);
        return isNull(gkVideoDTO);
    }

    /**
     * 获取日程详情
     * @return
     */
    @ApiDesc(value = "访问量统计自增方法", owner = "杨永平")
    @RequestMapping(value = "/hitInc",method = RequestMethod.GET)
    @ResponseBody
    public void hitInc(@ApiParam(param="id", desc="主键ID",required = true) @RequestParam("id") String id){
        if("".equals(id)){
            throw new BizException(ERRORCODE.IDISNOTNULL.getCode(),ERRORCODE.IDISNOTNULL.getMessage());
        }
        try {
            gkVideoService.hitInc(id);
        }catch (BizException e){
            throw e;
        }
    }
}
