package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IReportResultDao;
import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.IReportResultService;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
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
        List<Integer> preList = selectPrecedence(map);
        final Integer size = preList.size();
        Integer[] preArr = (Integer[]) preList.toArray(new Integer[size]);
        Integer result = ReportUtil.binarysearchKey(preArr, precedence);
        return result;
    }
    /**
     * 评估结果输出----获取报告展示 -- 用户信息部分
     * @return
     */
    private ReportResultView getReportResultView(Map map) throws IOException {
        ReportResultView reportResultView = new ReportResultView();

        ReportResult reportResult = selectModelOne(map);

        reportResultView.setUserId(reportResult.getUserId());

        //获取批次控制线
        String controllLine = iSystemParmasService.getBatchKey(reportResult.getBatch(), reportResult.getProvinceCode());

        SystemParmas systemParmas = iSystemParmasService.getRoleByKey(controllLine);

        reportResultView.setControllLine(systemParmas.getConfigValue());
        reportResultView.setUserName("测试");
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
            List<SelfReportUniversityView> selfReportUniversityViews = reportResultViews.getSelfReportUniversityViewList();

            for (SelfReportUniversityView selfReportUniversityView : selfReportUniversityViews) {
                reportUniversityView.setProperty(selfReportUniversityView.getProperty());
                reportUniversityView.setUniversityName(selfReportUniversityView.getName());
                reportUniversityView.setEnrollRate(selfReportUniversityView.getEnrollRate());
                reportUniversityView.setEnrollingNumber(selfReportUniversityView.getEnrollingNumber());
                reportUniversityView.setAverageScoreAvg(selfReportUniversityView.getAverageScore());
                reportUniversityView.setProportion(selfReportUniversityView.isProportion());
                reportUniversityView.setRange(selfReportUniversityView.isRange());
                reportUniversityView.setRankTrend(selfReportUniversityView.getRankTrend());

                Map map=new HashMap();
                map.put("universityId",selfReportUniversityView.getId());
                Integer lowestScoreAvg=iUniversityMajorEnrollingService.lowestScoreAvg(map);
                reportUniversityView.setLowestScoreAvg(lowestScoreAvg);
            }

            Map map=new HashMap();
            map.put("score",score);
            Integer  ranking =iReportResultDao.selectRanking(map);

            reportUniversityView.setRanking(ranking);
            reportUniversityViews.add(reportUniversityView);
        }
        return reportUniversityViews;
    }

}
