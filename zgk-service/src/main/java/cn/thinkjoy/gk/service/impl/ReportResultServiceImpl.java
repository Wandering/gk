package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IReportResultDao;
import cn.thinkjoy.gk.dao.IRiskForecastDAO;
import cn.thinkjoy.gk.entity.ReportLock;
import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.entity.RiskForecast;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.*;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by douzy on 16/3/16.
 */
@Service
public class ReportResultServiceImpl implements IReportResultService {

    @Resource
    IReportResultDao iReportResultDao;
    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityMajorEnrollingService iUniversityMajorEnrollingService;
    @Resource
    IReportUserInfoService iReportUserInfoService;
    @Resource
    IRiskForecastDAO iRiskForecastDAO;
    @Resource
    IReportLockService iReportLockService;

    /**
     * 保存志愿报告
     *
     * @param reportResult
     * @return
     */
    @Override
    public Integer insertSelective(ReportResult reportResult) {
        return iReportResultDao.insertSelective(reportResult);
    }
    @Override
    public ReportResult  selectModelOne(Map map){
        return iReportResultDao.selectModelOne(map);
    }

    @Override
    public List<ReportResult> selectHistoryList(Map map) {
        return iReportResultDao.selectHistoryList(map);
    }

    /**
     * 评估结果输出
     * @param map
     * @return
     * @throws IOException
     */
    @Override
    public ReportInfoView getReportInfoView(Map map) throws IOException {

//        Integer score= Integer.valueOf(map.get("score").toString());

        ReportInfoView reportInfoView = new ReportInfoView();
        ReportResultView reportResultView = getReportResultView(map);

        reportInfoView.setReportResultView(reportResultView);

        List<SelfReportResultView> selfReportResultViews = getUnSerializableReports(reportResultView.getReportResultJson());
        List<ReportUniversityView> reportUniversityViews = getReportUniversityView(reportResultView.getScore(),selfReportResultViews);
        reportInfoView.setReportUniversityViewList(reportUniversityViews);
        return reportInfoView;
    }
    @Override
    public List<Integer> selectPrecedence(Map map){
        return  iReportResultDao.selectPrecedence(map);
    }

    /**
     * 获取位次信息
     * @return
     */
    @Override
    public Integer getPrecedence(String tableName,Integer precedence) {
        if (precedence <= 0)
            return 0;
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("preceden", precedence);

        List<Integer> preList = selectPrecedence(map);
        if (preList == null || preList.size() <= 0)
            return 0;
        final Integer size = preList.size();
        Integer[] preArr = (Integer[]) preList.toArray(new Integer[size]);
        Integer result = ReportUtil.binarysearchKey(preArr, precedence);
        return result;
    }

    /**
     * 合理性评估
     * @return
     */
    @Override
    public boolean reportIsReasonable(ReportResult reportResult) {
        boolean result = true;
        List<SelfReportResultView> selfReportResultViews = getUnSerializableReports(reportResult.getReportResultJson());

        //获取规则阀值
        SystemParmas systemParmas= iSystemParmasService.getThresoldModel(reportResult.getProvinceCode(), ReportUtil.VOLUNTEER_RANKING_VALUE_KEY,reportResult.getMajorType());
        if(systemParmas==null)
            return false;
         Integer rankingValue= Integer.valueOf(systemParmas.getConfigValue());
        List<Integer> reportSeq=new ArrayList<>();
        for (int i = 0; i < selfReportResultViews.size(); i++) {
            SelfReportResultView prevSelfReportResultView = selfReportResultViews.get(i);

            Integer prevSeq = prevSelfReportResultView.getSelfReportUniversityViewList().getSequence();
            reportSeq.add(prevSeq);
            for (int j = i + 1; j < selfReportResultViews.size(); j++) {
                SelfReportResultView nextSelfReportResultView = selfReportResultViews.get(j);
                Integer nextSeq = nextSelfReportResultView.getSelfReportUniversityViewList().getSequence();
                if (prevSeq > nextSeq) {
                    return false;
                }
            }
        }
        //大于阀值
        if(reportResult.getPrecedence()>=rankingValue) {
            SystemParmas classifySysParmas = iSystemParmasService.getThresoldModel(reportResult.getProvinceCode(), ReportUtil.CLASSIFY_TAG_KEY,reportResult.getMajorType());
            String[] arr = classifySysParmas.getConfigValue().split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);

            for (int i = 0; i < arr.length; i++) {
                if (!reportSeq.contains(i)) {
                    return false;
                }
            }

        }
        return result;
    }

    /**
     * 完整性评估
     * @return
     */
    @Override
    public boolean reportIsComplete(ReportResult reportResult) {
        boolean result = true;
        List<SelfReportResultView> selfReportResultViews = getUnSerializableReports(reportResult.getReportResultJson());
//        /**
//         * 获取专业最大限制数
//         */
//        SystemParmas specialtyParmas = iSystemParmasService.getThresoldModel(reportResult.getProvinceCode(), ReportUtil.SPECIALITY_NUMVER);
//        if (specialtyParmas == null) {
//            return false;
//        }
//        SystemParmas swapParmas = iSystemParmasService.getThresoldModel(reportResult.getProvinceCode(), ReportUtil.SWAP_NUMBER);
//        if (swapParmas == null) {
//            return false;
//        }
//        Integer specialtyNum = Integer.valueOf(specialtyParmas.getConfigValue()),
//                swapNum = Integer.valueOf(swapParmas.getConfigValue());

        for (int i = 0; i < selfReportResultViews.size(); i++) {
            SelfReportResultView prevSelfReportResultView = selfReportResultViews.get(i);
            List<SelfReportMajorView> selfReportMajorViews = prevSelfReportResultView.getSelfReportUniversityViewList().getSelfReportMajorViewList();

            for (SelfReportMajorView selfReportMajorView : selfReportMajorViews) {
                if (StringUtils.isBlank(selfReportMajorView.getName())) {
                    return false;
                }
            }
        }

        return result;
    }

    @Override
    public UserReportResultView getUserReportResultView(Long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        map.put("size", 1);
        ReportResult reportResult = selectModelOne(map);
        UserReportResultView userReportResultView = new UserReportResultView();
        userReportResultView.setExistReport((reportResult == null ? false : true));
        if (userReportResultView.isExistReport()) {
            userReportResultView.setBatch(reportResult.getBatch());
            userReportResultView.setMajorType(reportResult.getMajorType());
            userReportResultView.setPrecedence(reportResult.getPrecedence());
            userReportResultView.setProvinceCode(reportResult.getProvinceCode());
            userReportResultView.setScore(reportResult.getScore());
            userReportResultView.setUserId(reportResult.getUserId());
            userReportResultView.setExtendProper(reportResult.getExtendProper());
        }
        return userReportResultView;
    }

    @Override
    public List<UserReportResultView>  getUserReportResultList(Long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        List<ReportResult> reportResults = selectHistoryList(map);

        List<UserReportResultView> userReportResultViews = new ArrayList<>();
        for (ReportResult reportResult : reportResults) {
            UserReportResultView userReportResultView = new UserReportResultView();
            userReportResultView.setExistReport((reportResult == null ? false : true));
            if (userReportResultView.isExistReport()) {
                userReportResultView.setBatch(reportResult.getBatch());
                userReportResultView.setMajorType(reportResult.getMajorType());
                userReportResultView.setPrecedence(reportResult.getPrecedence());
                userReportResultView.setProvinceCode(reportResult.getProvinceCode());
                userReportResultView.setScore(reportResult.getScore());
                userReportResultView.setUserId(reportResult.getUserId());
                userReportResultView.setExtendProper(reportResult.getExtendProper());
            }
            userReportResultViews.add(userReportResultView);
        }
        return userReportResultViews;
    }
    @Override
    public List<ReportLockView>  getUserReportLockResultList(Long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderBy", "id");
        map.put("sortBy", "desc");
        List<ReportLock> reportLocks=iReportLockService.selectReportLock(map);

        List<ReportLockView> reportLockViews = new ArrayList<>();
        for (ReportLock reportLock : reportLocks) {
            ReportLockView reportLockView = new ReportLockView();
            reportLockView.setExistReport((reportLock == null ? false : true));
            if (reportLockView.isExistReport()) {
                reportLockView.setBatch(reportLock.getBatch());
                reportLockView.setMajorType(reportLock.getMajorType());
                reportLockView.setPrecedence(reportLock.getPrecedence());
                reportLockView.setProvinceCode(reportLock.getProvinceCode());
                reportLockView.setScore(reportLock.getScore());
                reportLockView.setUserId(reportLock.getUserId());
                reportLockView.setExtendProper(reportLock.getExtendProper());
            }
            reportLockViews.add(reportLockView);
        }
        return reportLockViews;
    }

    /**
     * 同步院校及专业信息至动态风险表
     * @param reportResult
     * @return
     */
    @Override
    public boolean InsertRiskForecast(ReportResult reportResult) {
        Integer result = 0;
        List<SelfReportResultView> selfReportResultViews = getUnSerializableReports(reportResult.getReportResultJson());

        for (SelfReportResultView selfReportResultView : selfReportResultViews) {
            RiskForecast riskForecast = new RiskForecast();
            //院校
            SelfReportUniversityView selfReportUniversityView = selfReportResultView.getSelfReportUniversityViewList();
            riskForecast.setUniversityId(Long.valueOf(selfReportUniversityView.getId()));
            riskForecast.setUniversityName(selfReportUniversityView.getName());


            //专业
            List<SelfReportMajorView> selfReportMajorViews = selfReportResultView.getSelfReportUniversityViewList().getSelfReportMajorViewList();
            for (SelfReportMajorView selfReportMajorView : selfReportMajorViews) {
                if (!StringUtils.isBlank(selfReportMajorView.getName())) {
                    riskForecast.setMajorName(selfReportMajorView.getName());
                    riskForecast.setPlanEnrolling(selfReportMajorView.getPlanEnrolling());
                    riskForecast.setCreateTime(System.currentTimeMillis());
                    riskForecast.setReportId(reportResult.getId());
                    riskForecast.setScore(reportResult.getScore());
                    riskForecast.setUserId(reportResult.getUserId());
                    result = iRiskForecastDAO.insert(riskForecast);
                }
            }

        }

        return result > 0;
    }
    /**
     * 评估结果输出----获取报告展示 -- 用户信息部分
     * @return
     */
    private ReportResultView getReportResultView(Map map) throws IOException {
        ReportResultView reportResultView = new ReportResultView();

        ReportResult reportResult = selectModelOne(map);

        Integer majorType = reportResult.getMajorType();
        String proCode = reportResult.getProvinceCode();

        reportResultView.setUserId(reportResult.getUserId());

        //获取批次控制线
        String controllLine = iSystemParmasService.getBatchKey(majorType, proCode);

        SystemParmas systemParmas = iSystemParmasService.getRoleByKey(proCode, controllLine, majorType);
//        Map searMap=new HashMap();
//        searMap.put("userId",map.get("userId").toString());
//        ReportUserInfo reportUserInfo = iReportUserInfoService.getUserInfoByUserId(searMap);
        reportResultView.setControllLine(systemParmas.getConfigValue());
        reportResultView.setUserName(map.get("userName").toString());
        reportResultView.setMajorType(reportResult.getMajorType());
        reportResultView.setCreateTime(reportResult.getCreateTime());
        reportResultView.setPrecedence(reportResult.getPrecedence());
        reportResultView.setComplete(reportResult.isComplete());
        reportResultView.setReasonable(reportResult.isReasonable());
        reportResultView.setScore(reportResult.getScore());
        reportResultView.setReportResultJson(reportResult.getReportResultJson());
        reportResultView.setExtendProper(reportResult.getExtendProper());
        return reportResultView;
    }


    /**
     * 评估结果输出----获取用户所选院校及专业反序列化后List集
     * @return
     */
    private  List<SelfReportResultView> getUnSerializableReports(String reportResultJson) {

        List<SelfReportResultView> selfReportResultViews = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            selfReportResultViews = mapper.readValue(reportResultJson, new TypeReference<List<SelfReportResultView>>(){});
            if (selfReportResultViews == null) {
                return null;
            }
            return selfReportResultViews;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 评估结果输出----获取院校评估报告~
     * @return
     */
    private List<ReportUniversityView> getReportUniversityView(Integer score,List<SelfReportResultView> selfReportResultViews) {
        List<ReportUniversityView> reportUniversityViews = new ArrayList<>();

        for (SelfReportResultView reportResultViews : selfReportResultViews) {

            ReportUniversityView reportUniversityView = new ReportUniversityView();
            reportUniversityView.setSequence(reportResultViews.getSequence());

            //此处后期优化 -- 其实院校不需要List 保存对象即可  因为每个志愿梯度 只能保存一个院校
            SelfReportUniversityView selfReportUniversityView = reportResultViews.getSelfReportUniversityViewList();
            if(!StringUtils.isBlank(selfReportUniversityView.getName())) {


//            for (SelfReportUniversityView selfReportUniversityView : selfReportUniversityViews) {
                reportUniversityView.setProperty(selfReportUniversityView.getProperty());
                reportUniversityView.setUniversityName(selfReportUniversityView.getName());
                reportUniversityView.setEnrollRate(selfReportUniversityView.getEnrollRate());
                reportUniversityView.setEnrollingNumber(selfReportUniversityView.getEnrollingNumber());
                reportUniversityView.setAverageScoreAvg(selfReportUniversityView.getAverageScore());
                reportUniversityView.setProportion(selfReportUniversityView.isProportion());
                reportUniversityView.setRange(selfReportUniversityView.isRange());
                reportUniversityView.setRankTrend(selfReportUniversityView.getRankTrend());
                reportUniversityView.setSeq(selfReportUniversityView.getSequence());

                Map universityMap = new HashMap();
                universityMap.put("universityId", selfReportUniversityView.getId());
                Integer lowestScoreAvg = iUniversityMajorEnrollingService.lowestScoreAvg(universityMap);
                reportUniversityView.setLowestScoreAvg(lowestScoreAvg);
//            }

                Map map = new HashMap();
                map.put("score", score);
                Integer ranking = iReportResultDao.selectRanking(map);

                reportUniversityView.setRanking(ranking);
                reportUniversityViews.add(reportUniversityView);
            }
        }
        return reportUniversityViews;
    }

}
