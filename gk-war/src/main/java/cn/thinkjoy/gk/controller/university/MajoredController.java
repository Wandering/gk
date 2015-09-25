package cn.thinkjoy.gk.controller.university;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.dto.MajoredDetailDto;
import cn.thinkjoy.gk.dto.MajoredDto;
import cn.thinkjoy.gk.dto.MajoredResponseDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wpliu on 15/9/25.
 */
@Controller("majoredController")
@RequestMapping("/majored")
public class MajoredController extends BaseController {

    public static  final Logger log= LoggerFactory.getLogger(MajoredController.class);

    /**
     * 获取初始化信息
     * @return
     */
    @RequestMapping(value = "/getInitInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitInfo(){
        Map<String,Object> responseMap=new HashMap<String, Object>();

        return responseMap;
    }

    /**
     * 搜索专业信息
     * @param query
     * @return
     */
    @RequestMapping(value = "/searchMajored",method = RequestMethod.POST)
    @ResponseBody
    public MajoredResponseDto searchMajored(MajoredQuery query){
        MajoredResponseDto majoredResponseDto=new MajoredResponseDto();

        return  majoredResponseDto;
    }

    /**
     *获取专业基本信息
     * @return
     */
    @RequestMapping(value = "/getMajoredInfo",method = RequestMethod.POST)
    @ResponseBody
    public MajoredDto getMajoredInfo(){
        MajoredDto majoredDto=new MajoredDto();
        String majoredCode=request.getParameter("code");
        return  majoredDto;
    }

    /**
     * 获取专业详细信息
     * @return
     */
    @RequestMapping(value = "/getMajoredDetail",method = RequestMethod.POST)
    @ResponseBody
    public MajoredDetailDto getMajoredDetail(){
        MajoredDetailDto majoredDetailDto=new MajoredDetailDto();
        String majoredCode=request.getParameter("code");
        return majoredDetailDto;
    }

}
