package cn.thinkjoy.gk.controller.university;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.IMajoredService;
import cn.thinkjoy.gk.service.IUniversityDictService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/25.
 */
@Controller("majoredController")
@RequestMapping("/majored")
public class MajoredController extends BaseController {

    public static  final Logger log= LoggerFactory.getLogger(MajoredController.class);

    @Autowired
     private IMajoredService  iMajoredService;
    @Autowired
    private IUniversityDictService iUniversityDictService;
    @Autowired
    private IDataDictService iDataDictService;
    /**
     * 获取初始化信息
     * @return
     */
    @RequestMapping(value = "/getInitInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitInfo(){
        Map<String,Object> responseMap=new HashMap<String, Object>();
        Map<String,Object> params=new HashMap<>();
        params.put("type","BATCHTYPE");
        List<Map<String,Object>> universityBatchList=iDataDictService.queryDictList(params);
        /**
         * 封装学科门类 本科
         */
        List<SubjectTypeDto> bkSubjectTypeDtos=new ArrayList<>();
        List<Map<String,Object>>  bkSubjectTypes =iMajoredService.getMajoreByParentId(1l);
        for(Map<String,Object> map:bkSubjectTypes){
            SubjectTypeDto subjectTypeDto= new SubjectTypeDto();
            subjectTypeDto.setId((Long)map.get("id"));
            subjectTypeDto.setName(map.get("name").toString());
            List<Map<String,Object>> majoredMaps=iMajoredService.getMajoreByParentId(subjectTypeDto.getId());
            subjectTypeDto.setMajoredType(majoredMaps);
            bkSubjectTypeDtos.add(subjectTypeDto);
        }

        /**
         *封装学科门类 专科
         */
        List<SubjectTypeDto> zkSubjectTypeDtos=new ArrayList<>();
        List<Map<String,Object>>  zkSubjectTypes =iMajoredService.getMajoreByParentId(2l);
        for(Map<String,Object> map:zkSubjectTypes){
            SubjectTypeDto subjectTypeDto= new SubjectTypeDto();
            subjectTypeDto.setId((Long)map.get("id"));
            subjectTypeDto.setName(map.get("name").toString());
            List<Map<String,Object>> majoredMaps=iMajoredService.getMajoreByParentId(subjectTypeDto.getId());
            subjectTypeDto.setMajoredType(majoredMaps);
            zkSubjectTypeDtos.add(subjectTypeDto);
        }
        List<BatchTypeDto> batchTypeDtos=new ArrayList<>();
        for(Map<String,Object> universityDict:universityBatchList){
            BatchTypeDto batchTypeDto=new BatchTypeDto();
            batchTypeDto.setId(Integer.valueOf(universityDict.get("id").toString()));
            batchTypeDto.setName(universityDict.get("name").toString());
            if(batchTypeDto.getName().contains("本科")){
                batchTypeDto.setSubjectType(bkSubjectTypeDtos);
            }else{
                batchTypeDto.setSubjectType(zkSubjectTypeDtos);
            }
            batchTypeDtos.add(batchTypeDto);
        }
        responseMap.put("batchType",batchTypeDtos);
        return responseMap;
    }

    /**
     * 搜索专业信息
     * @param query
     * @return
     */
    @RequestMapping(value = "/searchMajored",method = RequestMethod.POST)
    @ResponseBody
    public Page<SubjectDto> searchMajored(MajoredQuery query){
        Page<SubjectDto> page=new Page<>();
         List<SubjectDto> majoredDtos= iMajoredService.searchMajored(query);
        Integer count=iMajoredService.searchMajoredCount(query);
        page.setCount(count);
        page.setList(majoredDtos);
        return  page;
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
        majoredDto=iMajoredService.getMajoredByCode(majoredCode);
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
        MajoredDto majoredDto=new MajoredDto();
        String majoredCode=request.getParameter("code");
        majoredDto=iMajoredService.getMajoredByCode(majoredCode);
        majoredDetailDto.setMainCourse(majoredDto.getMainCourse());
        majoredDetailDto.setSimilarMajor(majoredDto.getSimilarMajor());
        majoredDetailDto.setWorkGuide(majoredDto.getWorkGuide());
        Map<String,Object> map=new HashMap<>();
        map.put("type","PROPERTY");//院校类型
        List<UniversityDict> universityTypeList=iUniversityDictService.queryList(map,"id","asc");
        List<OpenUniversity> openUniversities=new ArrayList<>();
        for(UniversityDict universityDict:universityTypeList){
            OpenUniversity openUniversity=new OpenUniversity();
            openUniversity.setUniversityType(universityDict.getName());
            List<Map<String,Object>> universityLs=iMajoredService.getUniversityByCode(majoredCode,universityDict.getName());
            openUniversity.setUniversityLs(universityLs);
            openUniversities.add(openUniversity);
        }
        majoredDetailDto.setOpenUniversity(openUniversities);
        return majoredDetailDto;
    }

}
