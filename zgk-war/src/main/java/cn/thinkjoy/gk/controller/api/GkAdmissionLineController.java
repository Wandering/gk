package cn.thinkjoy.gk.controller.api;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.controller.api.base.BaseApiController;
import cn.thinkjoy.gk.domain.GkAdmissionLine;
import cn.thinkjoy.gk.pojo.UniversityEnrollingDTO;
import cn.thinkjoy.gk.service.information.service.ex.IUniversityEnrollingExService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 历年分数线controller
 * Created by admin on 2016/1/4.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/admissionline")
public class GkAdmissionLineController extends BaseApiController {
    public int TOKEN_EXPIRE_TIME = 60*60;
    /**行默认**/
    private static int ROWSDEFAULT=4;
    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;
    @Autowired
    private cn.thinkjoy.zgk.remote.IUniversityService iremoteUniversityService;

    @Autowired
    private IUniversityEnrollingExService universityEnrollingExService;
    /**
     * 获取批次线分页方法
     * @return
     */
    @ApiDesc(value = "获取分数线", owner = "杨永平")
    @RequestMapping(value = "/getGkAdmissionLineList.do",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkAdmissionLine> getGkAdmissionLineList(@ApiParam(param="queryparam", desc="标题模糊查询",required = false) @RequestParam(required = false) String queryparam,
                                                                @ApiParam(param="year", desc="年份",required = false) @RequestParam(required = false) String year,
                                                                @ApiParam(param="areaId", desc="页数",required = false) @RequestParam(required = false) String areaId,
                                                                @ApiParam(param="property", desc="院校特征",required = false) @RequestParam(required = false) String property,
                                                                @ApiParam(param="batch", desc="批次",required = false) @RequestParam(required = false) Integer batch,
                                                                @ApiParam(param="type", desc="科类",required = false) @RequestParam(defaultValue = "1",required = false) Integer type,
                                                                @ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                                                @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "10",required = false) Integer rows){

        //默认参数设置
        Map<String,Object> map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
//        年份
        if(year!=null &&!"".equals(year)) {
            QueryUtil.setMapOp(map, "enrollingyear", "=", year);
        }
//        院校名称 模糊
        if(queryparam!=null &&!"".equals(queryparam)) {
            QueryUtil.setMapOp(map, "universityname", "like", "%" + queryparam + "%");
        }
//        地区
        if(areaId!=null &&!"".equals(areaId)) {
            QueryUtil.setMapOp(map, "universityareaid", "=", areaId);
        }
//        特征
        if(property!=null &&!"".equals(property)) {
            QueryUtil.setMapOp(map, "universityproperty", "like", "%" + property + "%");
        }
//        批次
        if(batch!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }

        Long area=this.getAreaId();
        if(area!=null) {
            QueryUtil.setMapOp(map,"enrollingareaid","=",area);
        }
//        文史/理工
         QueryUtil.setMapOp(map, "entype", "=", type);
        map.put("orderBy","rank IS NULL,rank,year");
        map.put("sortBy","asc");



//        BizData4Page<GkAdmissionLine> bizData4Page=gkAdmissionLineService.getGkAdmissionLineList(map,page,rows);
        BizData4Page<GkAdmissionLine> bizData4Page= doPage(map,universityEnrollingExService,page,rows);

        for(GkAdmissionLine gkAdmissionLine:bizData4Page.getRows()){
            String[] propertys2= null;
            Map<String,Object> propertyMap=new HashMap();
            if(StringUtils.isNotEmpty(gkAdmissionLine.getProperty().toString())){
                propertys2=gkAdmissionLine.getProperty().split(",");
                Map<String,Object> propertysMap =getPropertys();

                for(String str:propertys2){
                    Iterator<String> propertysIterator=propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()){
                        String key = propertysIterator.next();
                        String value=propertysMap.get(key).toString();
                        if(str.indexOf(value)>-1){
                            propertyMap.put(key,value);
                        }
                    }
                }
            }
            gkAdmissionLine.setPropertys(propertyMap);
        }


        return bizData4Page;
    }
    /**
     * 获取批次线分页方法
     * @return
     */
    @ApiDesc(value = "获取年份", owner = "杨永平")
    @RequestMapping(value = "/getYears.do",method = RequestMethod.GET)
    @ResponseBody
    public List getYears(){
        return universityEnrollingExService.getYear();
    }

    private Map<String,Object> getPropertys(){
        List<Map<String,Object>> list=null;
        Map<String,Object> propertysMap=new HashMap<>();

        String key="universityPropertys";
        RedisRepository redisRepository= RedisUtil.getInstance();
        boolean flag=redisRepository.exists(key);
        if(flag){
            propertysMap= JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        }else {
            list=iremoteUniversityService.getDataDictListByType("FEATURE");
            for(Map<String,Object> map:list){
                propertysMap.put(map.get("dictId").toString(),map.get("name").toString());
            }
            redisRepository.set(key,JSON.toJSON(propertysMap),TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return propertysMap;
    }

    /**
     * 将父级domain转换重写实现
     * @param conditions
     * @return
     */
    @Override
    protected Object enhanceStateTransition(List conditions) {
        return domain2GkAdmissionLine(conditions);
    }


    /**
     * api需要domainList和admindomainList转换
     * @param universityEnrollingDTOs
     * @return
     */
    private List<GkAdmissionLine> domain2GkAdmissionLine(List<UniversityEnrollingDTO> universityEnrollingDTOs){
        if(universityEnrollingDTOs==null)return null;
        List<GkAdmissionLine> gkAdmissionLines = new ArrayList<>();
        for(UniversityEnrollingDTO universityEnrollingDTO:universityEnrollingDTOs){
            gkAdmissionLines.add(domain2GkAdmissionLine(universityEnrollingDTO));
        }
        return gkAdmissionLines;
    }

    /**
     * api需要domain和admindomain转换
     * @param universityEnrollingDTO
     * @return
     */
    private GkAdmissionLine domain2GkAdmissionLine(UniversityEnrollingDTO universityEnrollingDTO){
        GkAdmissionLine gkAdmissionLine=new GkAdmissionLine();
        gkAdmissionLine.setId(universityEnrollingDTO.getUniversityId());
        gkAdmissionLine.setName(universityEnrollingDTO.getName());
        gkAdmissionLine.setAverageScore(universityEnrollingDTO.getAverageScore());
        gkAdmissionLine.setBatchname(universityEnrollingDTO.getBatchname());
        gkAdmissionLine.setHighestScore(universityEnrollingDTO.getHighestScore());
        gkAdmissionLine.setLowestScore(universityEnrollingDTO.getLowestScore());
        gkAdmissionLine.setProperty(universityEnrollingDTO.getProperty());
        gkAdmissionLine.setTypename(universityEnrollingDTO.getTypename());
        gkAdmissionLine.setYear(universityEnrollingDTO.getYear());
        gkAdmissionLine.setSubjection(universityEnrollingDTO.getSubjection());
        return gkAdmissionLine;
    }
}
