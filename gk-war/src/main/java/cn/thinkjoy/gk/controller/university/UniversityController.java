package cn.thinkjoy.gk.controller.university;

/**
 * Created by wpliu on 15/9/25.
 */

import cn.thinkjoy.gk.IUniversityService;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.dto.*;
import cn.thinkjoy.gk.query.UniversityQuery;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUniversityService iUniversityService;
    /**
     * 获取初始化信息
     * @return
     */
    @RequestMapping(value = "/getInitInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitInfo(){
       Map<String,Object> responseMap=new HashMap<String, Object>();
        List<Map>  provinces=iUniversityService.getProvinces();
        List<Map>  universityType=iUniversityService.getUniversityType();
        List<Map>  universityBatch=iUniversityService.getUniversiyBatch();
        List<Map>  universityFeature=iUniversityService.getuniversityFeature();
        responseMap.put("provinces",provinces);
        responseMap.put("universityType",universityType);
        responseMap.put("universityBatch",universityBatch);
        responseMap.put("universityFeature", universityFeature);
        return  responseMap;
    }

    /**
     * 获取大学列表
     * @param universityQuery
     * @return
     */
    @RequestMapping(value = "/getUniversityList",method = RequestMethod.POST)
    @ResponseBody
    public UniversityResponseDto getUniversityList(UniversityQuery universityQuery){
        UniversityResponseDto universityResponseDto=new UniversityResponseDto();
        List<UniversityDto> universityDtos=new ArrayList<UniversityDto>();
        universityDtos=iUniversityService.getUniversityList(universityQuery);
        Integer universityCount=iUniversityService.getUniversityCount(universityQuery);
        universityResponseDto.setSchoolList(universityDtos);
        universityResponseDto.setCurrentPage(universityQuery.getPageNo()+1);
        universityResponseDto.setSchoolCount(universityCount);
        return  universityResponseDto;
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
        universityDto=iUniversityService.getUniversityDetail(schoolCode);
        return universityDto;
    }

    /**
     * 获取院校招生情况
     * @return
     */
    @RequestMapping(value = "/getEnrollInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getEnrollInfo(){
//        String schoolCode=request.getParameter("code");
//        Map<String,Object> map=new HashMap<String, Object>();
//        List<EnrollResponseDto>  enrollResponseDtoList=new ArrayList<EnrollResponseDto>();
//        EnrollResponseDto lastEnrollResponseDto=new EnrollResponseDto();
//        lastEnrollResponseDto.setTitle("2015招生情况");
//        EnrollResponseDto lastLastEnrollResponseDto=new EnrollResponseDto();
//        enrollResponseDto.setTitle("2015");
//        List<EnrollInfo>  lastenrollInfos=iUniversityService.getEnrollInfoByYear(2014);
//        List<EnrollInfo>  enrollInfos=iUniversityService.getEnrollInfoByYear(2015);
//        EnrollResponseDto enrollResponseDto=new EnrollResponseDto();
//        map.put("enrollInfo","");
        return null;
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
