package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.IMajoredRankExService;
import cn.thinkjoy.gk.service.IMajoredService;
import cn.thinkjoy.gk.service.IUniversityDictService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by wpliu on 15/9/25.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/majored")
public class MajoredController extends BaseController {

    public static  final Logger log= LoggerFactory.getLogger(MajoredController.class);

    @Autowired
    private IMajoredRankExService majoredRankExService;
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

        List<String> years = new ArrayList<String>();

        years.add("2010");
        years.add("2011");
        years.add("2012");
        years.add("2013");
        years.add("2014");
        years.add("2015");

        responseMap.put("years",years);

        return responseMap;
    }

    /**
     * 搜索专业信息
     * @param query
     * @return
     */
    @RequestMapping(value = "/searchMajored",method = RequestMethod.GET)
    @ResponseBody
    public List<SubjectDto> searchMajored(MajoredQuery query){
//        Page<SubjectDto> page=new Page<>();
        query.setPageNo((query.getPageNo()-1)*query.getPageSize());
        List<SubjectDto> majoredDtos= iMajoredService.searchMajored(query);
//        page.setList(majoredDtos);
        return  majoredDtos;
    }

    /**
     * 搜索专业信息
     * @param query
     * @return
     */
    @RequestMapping(value = "/searchMajoredCount",method = RequestMethod.GET)
    @ResponseBody
    public Integer searchMajoredCount(MajoredQuery query){
//        Page<SubjectDto> page=new Page<>();
//        query.setPageNo((query.getPageNo()-1)*query.getPageSize());
        Integer count=iMajoredService.searchMajoredCount(query);
//        page.setCount(count);
        return  count;
    }




    /**
     *获取专业基本信息
     * @return
     */
    @RequestMapping(value = "/getMajoredInfo",method = RequestMethod.GET)
    @ResponseBody
    public MajoredDto getMajoredInfo(){
        MajoredDto majoredDto=new MajoredDto();
        String majoredCode=request.getParameter("code");
        majoredDto=iMajoredService.getMajoredById(majoredCode);
        return  majoredDto;
    }

    /**
     * 获取专业详细信息
     * @return
     */
    @RequestMapping(value = "/getMajoredDetail",method = RequestMethod.GET)
    @ResponseBody
    public MajoredDetailDto getMajoredDetail(@RequestParam(value="code",required=false) String majoredCode,
                                             @RequestParam(value="startSize",required=false) Integer startSize,
                                             @RequestParam(value="endSize",required=false) Integer endSize) throws Exception {
        if(StringUtils.isEmpty(majoredCode)){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),ERRORCODE.PARAM_ERROR.getMessage());
        }

        if(startSize==null){
            startSize = 0;
        }

        if(endSize==null){
            endSize=0;
        }

        MajoredDetailDto majoredDetailDto=new MajoredDetailDto();
        MajoredDto majoredDto = new MajoredDto();
        majoredDto=iMajoredService.getMajoredById(majoredCode);
        majoredDetailDto.setMainCourse(majoredDto.getMainCourse());
        majoredDetailDto.setSimilarMajor(majoredDto.getSimilarMajored());
        majoredDetailDto.setWorkGuide(majoredDto.getWorkGuide());
//        Map<String,Object> map=new HashMap<>();
//        map.put("type","PROPERTY");//院校类型
//        List<UniversityDict> universityTypeList=iUniversityDictService.queryList(map,"id","asc");
//        List<OpenUniversity> openUniversities=new ArrayList<>();
//        for(UniversityDict universityDict:universityTypeList){
//            OpenUniversity openUniversity=new OpenUniversity();
//            openUniversity.setUniversityType(universityDict.getName());
//            List<Map<String,Object>> universityLs=iMajoredService.getUniversityByCode(majoredCode,universityDict.getName());
//            openUniversity.setUniversityLs(universityLs);
//            openUniversities.add(openUniversity);
//        }
        Calendar a=Calendar.getInstance();

        int year = a.get(Calendar.YEAR);

        long areaId = getAreaCookieValue();

        List<MajoredRankDto> majoredRankDto = majoredRankExService.findOpenUniversity(majoredDto.getName(), year, areaId,startSize,endSize);

        majoredDetailDto.setOpenUniversity(majoredRankDto);

        return majoredDetailDto;
    }

    /**
     * 获取专业信息
     * @return
     */
    @RequestMapping(value = "/majorList",method = RequestMethod.GET)
    @ResponseBody
    public List<MajorDetailPojo> majorList(@RequestParam(value="code",required=false) String code,
                                           @RequestParam(value="year",required=false) Integer year,
                                           @RequestParam(value="batch",required=false) String batch){

        if(StringUtils.isBlank(code)
                ||StringUtils.isBlank(batch)
                ||null==year){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        List<MajorDetailPojo> lists = null;

        try {
            lists = iMajoredService.getMajorDetailList(code, batch, year);
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return lists;
    }


}
