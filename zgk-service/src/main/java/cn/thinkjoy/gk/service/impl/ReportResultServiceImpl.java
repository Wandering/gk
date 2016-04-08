package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IReportResultDao;
import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.IReportResultService;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 评估结果输出
     * @param map
     * @return
     * @throws IOException
     */
    @Override
    public ReportInfoView getReportInfoView(Map map) throws IOException {

        Integer score= Integer.valueOf(map.get("score").toString());

        ReportInfoView reportInfoView = new ReportInfoView();
        ReportResultView reportResultView = getReportResultView(map);
        reportInfoView.setReportResultView(reportResultView);
        List<SelfReportResultView> selfReportResultViews = getUnSerializableReports(reportResultView.getReportResultJson());
        List<ReportUniversityView> reportUniversityViews = getReportUniversityView(score,selfReportResultViews);
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
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("preceden",precedence);
        List<Integer> preList = selectPrecedence(map);
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
    /**
     * 评估结果输出----获取报告展示 -- 用户信息部分
     * @return
     */
    private ReportResultView getReportResultView(Map map) throws IOException {
        ReportResultView reportResultView = new ReportResultView();

        Integer majorType = Integer.valueOf(map.get("majorType").toString());
        String proCode=map.get("province").toString();
        ReportResult reportResult = selectModelOne(map);

        reportResultView.setUserId(reportResult.getUserId());

        //获取批次控制线
        String controllLine = iSystemParmasService.getBatchKey(reportResult.getMajorType(), reportResult.getProvinceCode());

        SystemParmas systemParmas = iSystemParmasService.getRoleByKey(proCode,controllLine,majorType);


        reportResultView.setControllLine(systemParmas.getConfigValue());
        reportResultView.setUserName(map.get("userName").toString());
        reportResultView.setMajorType(reportResult.getMajorType());
        reportResultView.setCreateTime(reportResult.getCreateTime());
        reportResultView.setPrecedence(reportResult.getPrecedence());
        reportResultView.setComplete(reportResult.isComplete());
        reportResultView.setReasonable(reportResult.isReasonable());
        reportResultView.setScore(reportResult.getScore());
        reportResultView.setReportResultJson(reportResult.getReportResultJson());
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
