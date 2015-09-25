package cn.thinkjoy.gk.controller.university;

/**
 * Created by wpliu on 15/9/25.
 */

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.dto.EntrollPlanDto;
import cn.thinkjoy.gk.dto.UniversityDto;
import cn.thinkjoy.gk.query.UniversityQuery;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学院信息控制类
 *
 */
@Controller("universityController")
@RequestMapping("/university")
public class UniversityController extends BaseController {

    public static final Logger LOGGER= LoggerFactory.getLogger(UniversityController.class);

    /**
     * 获取初始化信息
     * @return
     */
    @RequestMapping(value = "/getInitInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitInfo(){
       Map<String,Object> responseMap=new HashMap<String, Object>();
        responseMap.put("provinces","");
        responseMap.put("universityType","");
        responseMap.put("universityBatch","");
        responseMap.put("universityFeature", "");
        return  responseMap;
    }

    /**
     * 获取大学列表
     * @param universityQuery
     * @return
     */
    @RequestMapping(value = "/getUniversityList",method = RequestMethod.POST)
    @ResponseBody
    public List<UniversityDto> getUniversityList(UniversityQuery universityQuery){
        List<UniversityDto> universityDtos=new ArrayList<UniversityDto>();

        return  universityDtos;
    }

    /**
     * 获取大学详情
     * @return
     */
    @RequestMapping(value = "/getUniversityDetail",method = RequestMethod.POST)
    @ResponseBody
    public UniversityDto getUniversityDetail(){
        UniversityDto universityDto=new UniversityDto();
          String schoolCode=request.getParameter("code");
        return universityDto;
    }

    /**
     * 获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getEnrollInfo(){
        String schoolCode=request.getParameter("code");
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("enrollInfo","");
        return map;
    }

    /**
     *获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollPlan",method = RequestMethod.POST)
    @ResponseBody
    public EntrollPlanDto getEnrollPlan(){
        String schoolCode=request.getParameter("code");
        EntrollPlanDto entrollPlanDto=new EntrollPlanDto();
        return  entrollPlanDto;
    }
}
