package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import com.alibaba.dubbo.common.utils.StringUtils;
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
import java.util.Collections;
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

    @Resource
    IReportLockService iReportLockService;
    @Resource
    IRiskForecastService iRiskForecastService;
    @Resource
    IScoreConverPrecedenceService iScoreConverPrecedenceService;

    /**
     * 查询用户是否已经填报
     * @return
     */
    @RequestMapping(value = "/exist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getReportByUser() {
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map resultMap = new HashMap();

//        parmasMap.put("proCode",);
//         List<ReportLock> reportLocks=iReportLockService.selectReportLock(parmasMap);
//        if (province.equals("zj") || province.equals("sh")) {
            List<ReportLockView> reportLockViews = iReportResultService.getUserReportLockResultList(Long.valueOf(userAccountPojo.getId()));
            resultMap.put("reports", reportLockViews);
//        } else {
//            UserReportResultView userReportResultView = iReportResultService.getUserReportResultView(Long.valueOf(userAccountPojo.getId()));
//            resultMap.put("report", userReportResultView);
//        }
        return resultMap;
    }
    /**
     * 校验用户位次准确性
     * @param score 分数
     * @param province 省份
     * @param categorie    科类
     * @param batch   批次
     * @param precedence 位次
     * @return
     */
    @RequestMapping(value = "/validPrecedenceScoreMapp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> validPrecedenceScoreMapper(@RequestParam(value = "batch",required = true) String batch,
                                                         @RequestParam(value = "score",required = true) Integer score,
                                                         @RequestParam(value = "province",required = true) String province,
                                                         @RequestParam(value = "categorie",required = true) Integer categorie,
                                                         @RequestParam(value="precedence",required = true) Integer precedence) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        Map resultMap = new HashMap();
        boolean result = iScoreConverPrecedenceService.validPrecedenceScoreMapper(score, province, categorie, batch, precedence);
        resultMap.put("result", result);
        return resultMap;
    }
    /**
     * 锁定分数及位次
     * @return
     */
    @RequestMapping(value = "/reportLockSet",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> InsertReportLock(  @RequestParam(value = "batch",required = true) String batch,
                                                 @RequestParam(value = "score",required = true) Integer score,
                                                 @RequestParam(value = "province",required = true) String province,
                                                 @RequestParam(value = "categorie",required = true) Integer categorie,
                                                 @RequestParam(value="precedence",required = true) Integer precedence,
                                                 @RequestParam(value="extendProper",required = false) String  extendProper) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map map = new HashMap();
        map.put("userId", userAccountPojo.getId().intValue());
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        List<ReportLock> reportLocks=iReportLockService.selectReportLock(map);

        if(reportLocks!=null&&reportLocks.size()>0&&!ReportUtil.IsDifference(province)) {
            Map resultMap = new HashMap();
            resultMap.put("result", true);
            return resultMap;
        }
        ReportLock reportLock = new ReportLock();
        reportLock.setUserId(userAccountPojo.getId().intValue());
        reportLock.setScore(score);
        reportLock.setBatch(batch);
        reportLock.setCreateTime(System.currentTimeMillis());
        reportLock.setMajorType(categorie);
        reportLock.setPrecedence(precedence);
        reportLock.setProvinceCode(province);
        if (!StringUtils.isBlank(extendProper))
            reportLock.setExtendProper(extendProper);


        Integer result = iReportLockService.insertSelective(reportLock);
        Map resultMap = new HashMap();
        resultMap.put("result", result > 0);
        return resultMap;
    }

    /**
     * 获取用户最后一次填报结果
     * @return
     */
    @RequestMapping(value = "/get/result",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getReportResult() throws IOException {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map map = new HashMap();
        map.put("userId", userAccountPojo.getId());
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        map.put("size", 1);
        ReportResult reportResult = iReportResultService.selectModelOne(map);
        ReportLastResultView reportLastResultView = new ReportLastResultView();
        reportLastResultView.setReport((reportResult == null ? false : true));
        reportLastResultView.setCreateTime((reportResult == null ? null : reportResult.getCreateTime()));
        reportLastResultView.setBatch((reportResult == null ? null : reportResult.getBatch()));
        reportLastResultView.setMajorType((reportResult == null ? null : reportResult.getMajorType()));
        reportLastResultView.setProvinceCode((reportResult == null ? null : reportResult.getProvinceCode()));
        Map resultMap = new HashMap();
        resultMap.put("reportObj", reportLastResultView);
        return resultMap;
    }

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
    public Map<String,Object> report(@RequestParam(value = "sap",required = false) Integer sap,
                                     @RequestParam(value = "score",required = false) Integer score,
                                     @RequestParam(value = "cate",required = false) Integer cate,
                                         @RequestParam(value = "province",required = false) String province,
                                         ModelMap modelMap) {
//        sap=201;
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }



        LOGGER.info("=======批次及批次控制线信息 Start=======");
        LOGGER.info("分数||位次:" + sap);
        LOGGER.info("科类:" + cate);
        LOGGER.info("省份:" + province);

        /**
         * 逻辑走向
         */
        Integer loginTrend=getLogic(province, cate);
        LOGGER.info("逻辑:" + loginTrend);
        List<BatchView> batchViews = iSystemParmasService.selectSystemParmas(cate, score,sap, province,loginTrend);

        Collections.sort(batchViews);
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
                                           ,@RequestParam(value = "cate") Integer cate
                                            ,@RequestParam(value = "code") String code
                                            ,@RequestParam(value = "batch") String batch) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        Map parmasMap = new HashMap();
        parmasMap.put("universityId", uId);
        parmasMap.put("majorType", cate);
        parmasMap.put("province", code);
        parmasMap.put("batch", batch);
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
    public Map<String,Object> getUserReport() throws IOException {


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
//        map.put("score",score);
//        map.put("majorType",cate);
//        map.put("province",province);
        map.put("userName", userAccountPojo.getName());
        ReportInfoView reportInfoView = iReportResultService.getReportInfoView(map);
        Map resultMap = new HashMap();
        resultMap.put("reportInfoView", reportInfoView);
        return resultMap;
    }

    /**
     * 输出算法逻辑走向
     * @param province
     * @param cate
     * @return
     */
    @RequestMapping(value = "/get/logic",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getLogic(@RequestParam(value = "province") String province,
                                       @RequestParam(value = "cate",required = false) String cate) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        SystemParmas systemParmas = iSystemParmasService.getThresoldModel(province, ReportUtil.LOGIC_TREND, 1);
        Map resultMap = new HashMap();
        resultMap.put("logic", systemParmas.getConfigValue());
        return resultMap;
    }
    /**
     * 获取智高考排名
     * @return
     */
    @RequestMapping(value = "/get/rank",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getZGKRank(@RequestParam(value = "score") Integer score,
                                         @RequestParam(value = "uid") Integer uid) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        Map map=new HashMap();
        map.put("score",score);
        map.put("universityId",uid);

        Integer rank=iRiskForecastService.selectRiskRankByUniversityId(map);

        Map resultMap = new HashMap();
        resultMap.put("rank", rank);

        return resultMap;
    }
    /**
     * 批次选择 提示
     * @param province 用户省份
     * @param cate 用户科类
     * @param score 用户分数
     * @param chkBatch 用户选择的位次
     * @return
     */
    @RequestMapping(value = "/check/batch",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> checkBatch(@RequestParam(value = "province") String province,
                                         @RequestParam(value = "cate") Integer cate,
                                         @RequestParam(value = "score") Integer score,
                                         @RequestParam(value = "chkBatch") String chkBatch) {
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }
        /**
         * 逻辑走向
         */
        Integer loginTrend = getLogic(province, cate);
        LOGGER.info("逻辑:" + loginTrend);


        UniversityInfoParmasView universityInfoParmasView = new UniversityInfoParmasView();
        universityInfoParmasView.setBatch(chkBatch);
        universityInfoParmasView.setCategorie(cate);
        universityInfoParmasView.setProvince(province);
        universityInfoParmasView.setScore(score);

        CheckBatchMsg checkBatchMsg = iSystemParmasService.checkBatchAlert(universityInfoParmasView);

        Map resultMap = new HashMap();
        resultMap.put("batchAlert", checkBatchMsg);
        return resultMap;
    }


    /**
     * 智能填报主入口
     * 获取用户  输入分数、位次、文理 过滤院校清单
     * @return
     */
    @RequestMapping(value = "/main",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> reportMain(
                             @RequestParam(value = "batch") String batch,
                             @RequestParam(value = "score") Integer score,
                             @RequestParam(value = "province") String province,
                             @RequestParam(value = "categorie") Integer categorie,
                             @RequestParam(value="precedence",required = false) Integer precedence,
                             @RequestParam(value="first") Integer first,
                             @RequestParam(value = "version") Integer version) {

        UserAccountPojo userAccountPojo = getUserAccountPojo();
        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        LOGGER.info("=======智能填报主入口 Start=======");
        LOGGER.info("分数:" + score);
        LOGGER.info("科类:" + categorie);
        LOGGER.info("省份:" + province);
        LOGGER.info("批次:" + batch);
        LOGGER.info("用户输入位次:"+precedence);
        LOGGER.info("专业OR院校:"+first);
        LOGGER.info("version:"+version);

        UniversityInfoParmasView universityInfoParmasView=new UniversityInfoParmasView();
        universityInfoParmasView.setBatch(batch);
        universityInfoParmasView.setCategorie(categorie);
        universityInfoParmasView.setFirst(first);
        universityInfoParmasView.setVersion(version);
        universityInfoParmasView.setPrecedence(precedence);
        universityInfoParmasView.setProvince(province);
        universityInfoParmasView.setScore(score);
//        String tbName = ReportUtil.getTableName(province, categorie, batch, (precedence > 0 ? true : false));
//
//        LOGGER.info("tableName:" + tbName);
//        Map map = new HashMap<>();
//        map.put("tableName", tbName);
////        map.put("code", "'" + province + "'");  //db
//        map.put("province", province);//key
//        map.put("majorType", categorie);
        /**
         * 逻辑走向
         */
        Integer logic= getLogic(province,categorie);
        universityInfoParmasView.setLogic(logic);

        universityInfoParmasView.setAreaId(getAreaId());



        List<UniversityInfoView> universityInfoViewList=iUniversityInfoService.selectUniversityInfoViewByLogic(universityInfoParmasView);

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

        LOGGER.info("=======智能填报主入口 End=======");

        Map map=new HashMap();
        map.put("universityInfoViewList",universityInfoViewList);
        return map;
    }

    /**
     * 难易预测
     * 获取用户  输入分数、位次、文理 过滤院校清单
     * @return
     */
    @RequestMapping(value = "/forecast",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> reportMain(
            @RequestParam(value = "batch") String batch,
            @RequestParam(value = "score") Integer score,
            @RequestParam(value = "province") String province,
            @RequestParam(value = "categorie") Integer categorie,
            @RequestParam(value="uid") Integer uid) {

        ReportForecastView reportForecastView = new ReportForecastView();
        reportForecastView.setBatch( ReportUtil.ConverOldBatch(batch));
        reportForecastView.setScore(score);
        reportForecastView.setProvince(province);
        reportForecastView.setCategorie(categorie);
        reportForecastView.setUid(uid);
        reportForecastView.setPrecedence(iUniversityInfoService.converPreByScoreV2(reportForecastView,ReportUtil.FORECAST_ENROLLING_LOGIC));
        reportForecastView.setScoreDiff(iUniversityInfoService.converScoreDiffByScore(reportForecastView));
        reportForecastView.setJoin(false);

        String enrolling = iUniversityInfoService.getEnrollingByForecast(reportForecastView);
        Map map = new HashMap();
        map.put("enrolling", enrolling);
        return map;
    }

    /**
     * 智能报告保存
     * @param reportResult
     * @return
     */
    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> reportSave(ReportResult reportResult) {
        //reportResult.setUserId(Integer.valueOf(super.getAccoutId()));
        UserAccountPojo userAccountPojo = getUserAccountPojo();


        if (userAccountPojo == null) {
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();

        if (vipStatus == null || vipStatus == 0) {
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(), ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        //合理性评估
        boolean isReasonable = iReportResultService.reportIsReasonable(reportResult);

        //完整性评估   --只要存在专业名称为空的  就是不完整
        boolean isCompl = iReportResultService.reportIsComplete(reportResult);
        reportResult.setUserId(Integer.valueOf(userAccountPojo.getId().toString()));
        reportResult.setCreateTime(System.currentTimeMillis());
        reportResult.setReasonable((isReasonable ? (byte) 1 : (byte) 0));
        reportResult.setComplete((isCompl ? (byte) 1 : (byte) 0));
        Integer result = iReportResultService.insertSelective(reportResult);
        if (result>0) {
            reportResult.setId(Long.valueOf(reportResult.getId()));
            iReportResultService.InsertRiskForecast(reportResult);         //同步信息至动态风险表
        }
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }


    /**
     * 智能报告保存
     * @param
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

    /**
     * 逻辑走向
     * @param proCode
     * @param cate
     * @return
     */
    private Integer getLogic(String proCode,Integer cate) {
        return iSystemParmasService.getLogicTrend(proCode, cate);
    }
}
