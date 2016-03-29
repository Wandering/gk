package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/report")
public class SmartReportController extends ZGKBaseController {
    private static final Logger LOGGER= LoggerFactory.getLogger(SmartReportController.class);
    @Resource
    IUniversityInfoService iUniversityInfoService;
    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityMajorEnrollingService iUniversityMajorEnrollingService;
    @Resource
    IReportResultService iReportResultService;
    /**
     * 获取批次及批次控制线信息
     * @param score
     * @param cate
     * @param province
     * @param modelMap
     * @return
     */
    @RequestMapping(value ="/get/batch",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> report(@RequestParam(value = "score",required = false) Integer score,
                                     @RequestParam(value = "cate",required = false) Integer cate,
                                         @RequestParam(value = "province",required = false) String province,
                                         ModelMap modelMap) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        LOGGER.info("=======批次及批次控制线信息 Start=======");
        LOGGER.info("分数:" + score);
        LOGGER.info("科类:" + cate);
        LOGGER.info("省份:" + province);
        List<BatchView> batchViews = iSystemParmasService.selectSystemParmas(cate, score, province);
        LOGGER.info("该学生达标:" + batchViews.size() + "个批次");
        Map resultMap = new HashMap();
        resultMap.put("batchViews", batchViews);
        LOGGER.info("=======批次及批次控制线信息 End========");
        return resultMap;
    }

    /**
     * 根据院校ID获取专业信息
     * @return
     */
    @RequestMapping(value = "/get/specialty",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getSpecialty(@RequestParam(value = "uId") Integer uId
                                           ,@RequestParam(value = "cate") Integer cate) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map parmasMap = new HashMap();
        parmasMap.put("universityId", uId);
        parmasMap.put("majorType", cate);
        List<SpecialtyView> universityMajoyEnrollingPlans = iUniversityMajorEnrollingService.selectList(parmasMap);
        Map resultMap = new HashMap();
        resultMap.put("specialtys", universityMajoyEnrollingPlans);
        return resultMap;
    }

    /**
     * 志愿报告清单
     * @return
     */
    @RequestMapping(value = "/get/info",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserReport(@RequestParam(value = "score") Integer score) throws IOException {


        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map map = new HashMap();
        map.put("userId", userAccountPojo.getId());
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        map.put("size", 1);
        map.put("score",score);
        map.put("userName", userAccountPojo.getName());
        ReportInfoView reportInfoView = iReportResultService.getReportInfoView(map);
        Map resultMap = new HashMap();
        resultMap.put("reportInfoView", reportInfoView);
        return resultMap;
    }
    /**
     * 智能填报主入口
     * 获取用户  输入分数、位次、文理 过滤院校清单
     * @return
     */
    @RequestMapping(value = "/main",method=RequestMethod.GET)
    @ResponseBody
    public List reportMain(
                             @RequestParam(value = "batch") Integer batch,
                             @RequestParam(value = "score") Integer score,
                             @RequestParam(value = "province") String province,
                             @RequestParam(value = "categorie") Integer categorie,
                             @RequestParam(value="precedence") Integer precedence,
                             @RequestParam(value="first") Integer first,
                             @RequestParam(value = "version") Integer version) {

//        UserAccountPojo userAccountPojo = getUserAccountPojo();
//        Integer vipStatus = userAccountPojo.getVipStatus();
//
//        if(vipStatus==null||vipStatus==0){
//            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
//        }

        LOGGER.info("=======智能填报主入口 Start=======");
        LOGGER.info("分数:" + score);
        LOGGER.info("科类:" + categorie);
        LOGGER.info("省份:" + province);
        LOGGER.info("批次:" + batch);
        LOGGER.info("用户输入位次:"+precedence);
        LOGGER.info("专业OR院校:"+first);
        LOGGER.info("version:"+version);
//        String tbName = ReportUtil.getTableName(province, categorie, batch, (precedence > 0 ? true : false));
//
//        LOGGER.info("tableName:" + tbName);
//        Map map = new HashMap<>();
//        map.put("tableName", tbName);
////        map.put("code", "'" + province + "'");  //db
//        map.put("province", province);//key
//        map.put("majorType", categorie);


        List<UniversityInfoView> universityInfoViewList=iUniversityInfoService.selectUniversityInfoViewByVersion(version,score,categorie,province,batch,precedence,first);

//        if (precedence > 0) {
//            Integer result=iReportResultService.getPrecedence(tbName, precedence);
//            map.put("precedence", result);
//
//        }else {
//            LOGGER.info("==线差计算 Start==");
//            //根据分数及控制线 计算线差
//            Integer lineDiff = iUniversityInfoService.getLineDiff(batch, score, categorie, province);
//            LOGGER.info("线差为:" + lineDiff);
//            LOGGER.info("==线差计算 End==");
//            map.put("scoreDiff", lineDiff);
//        }

//        List<UniversityInfoView> universityInfoViewList = iUniversityInfoService.selectUniversityInfo(map);

        LOGGER.info("最终输出:" + universityInfoViewList.size() + "个院校清单");
        LOGGER.info("=======智能填报主入口 End=======");
        return universityInfoViewList;
    }

    /**
     * 智能报告保存
     * @param reportResult
     * @return
     */
    @RequestMapping(value = "/save",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> reportSave(ReportResult reportResult) {
        //reportResult.setUserId(Integer.valueOf(super.getAccoutId()));
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        boolean isReasonable=iReportResultService.reportIsReasonable(reportResult.getReportResultJson());
        reportResult.setUserId( Integer.valueOf(userAccountPojo.getId().toString()));
        reportResult.setCreateTime(System.currentTimeMillis());
        reportResult.setReasonable((isReasonable?(byte)1:(byte)0));
        Integer result = iReportResultService.insertSelective(reportResult);
        Map map = new HashMap();

        map.put("result", result);
        return map;
    }

    /**
     * 智能报告保存
     * @param reportResult
     * @return
     */
    @RequestMapping(value = "/test",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> reportJson() throws IOException {
//        ReportInfoView reportInfoView=new ReportInfoView();
//        ReportResultView reportResult=new ReportResultView();
//        reportResult.setUserId(123);
//        reportResult.setUserName("测试");
//        reportResult.setComplete(true);
//        reportResult.setReasonable(true);
//        reportResult.setScore(450);
//        reportResult.setMajorType(1);
//        reportResult.setReportResultJson("");
//        reportResult.setPrecedence(29);
//        reportResult.setControllLine("550-400-300");
//        reportInfoView.setReportResultView(reportResult);
//
//
//        List<ReportUniversityView> reportUniversityViews=new ArrayList<>();
//        for(int i=0;i<2;i++) {
//
//            ReportUniversityView reportUniversityView = new ReportUniversityView();
//            reportUniversityView.setUniversityName(i == 0 ? "北京大学" : "复旦大学");
//            reportUniversityView.setRange(true);
//            reportUniversityView.setAverageScoreAvg(i == 0 ? 429 : 500);
//            reportUniversityView.setEnrollingNumber(19);
//            reportUniversityView.setEnrollRate(BigDecimal.valueOf(80));
//            reportUniversityView.setLowestScoreAvg(i == 0 ? 450 : 600);
//            reportUniversityView.setProperty("985/211");
//            reportUniversityView.setRanking(28);
//            reportUniversityView.setRankTrend("1-2");
//            reportUniversityView.setScoreUseRate(BigDecimal.valueOf(67));
//            reportUniversityView.setEnrollRate(BigDecimal.valueOf(76));
//            reportUniversityView.setSequence(2);
//            reportUniversityViews.add(reportUniversityView);
//
//        }
//        reportInfoView.setReportUniversityViewList(reportUniversityViews);
//
        //院校

//        SelfReportResultView selfReportResultView=new SelfReportResultView();
//
//        List<SelfReportMajorView> selfReportMajorViews=new ArrayList<>();
//        SelfReportMajorView selfReportMajorView=new SelfReportMajorView();
//        selfReportMajorView.setId(176);
//        selfReportMajorView.setName("德语");
//        SelfReportMajorView selfReportMajorView1=new SelfReportMajorView();
//        selfReportMajorView1.setId(177);
//        selfReportMajorView1.setName("机械设计制造及其自动化");
//        selfReportMajorViews.add(selfReportMajorView);
//
//
//        List<SelfReportUniversityView> selfReportUniversityViews=new ArrayList<>();
//
//        SelfReportUniversityView selfReportUniversityView=new SelfReportUniversityView();
//        selfReportUniversityView.setId(10);
//        selfReportUniversityView.setName("四川大学锦江学院");
//        selfReportUniversityView.setSelfReportMajorViewList(selfReportMajorViews);
//        selfReportUniversityView.setAverageScore(129);
//        selfReportUniversityView.setEnrollingNumber(23);
//        selfReportUniversityView.setIsComplied(0);
//        selfReportUniversityView.setSubjection("教育部");
//        selfReportUniversityView.setEnrollRate(BigDecimal.valueOf(0.3));
//        selfReportUniversityView.setType(1);
//
//        selfReportUniversityViews.add(selfReportUniversityView);
//
//        selfReportResultView.setSequence(3);
//        selfReportResultView.setSelfReportUniversityViewList(selfReportUniversityViews);

        String latlngs =new String("[{\"selfReportUniversityViewList\": [{\"averageScore\": 129,\"enrollRate\": 0.3,\"enrollingNumber\": 23,\"id\": 10,\"isComplied\": 0,\"name\": \"四川大学锦江学院\",\"selfReportMajorViewList\": [{\"id\": 176,\"name\": \"德语\"}],\"subjection\": \"教育部\",\"type\": 1}],\"sequence\": 3}]");
        ObjectMapper mapper = new ObjectMapper();
        List<SelfReportResultView> selfReportResultView= mapper.readValue(latlngs, List.class);
//        List<SelfReportResultView> selfReportResultViewList= JsonUtil.deserialize(latlngs,List<SelfReportResultView>);
        Map map = new HashMap();
                      map.put("result",selfReportResultView);
//        map.put("result", selfReportResultView);
        return map;
    }


}
