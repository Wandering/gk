package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.pojo.ReportInfoView;

import java.io.IOException;
import java.util.Map;

/**
 * Created by douzy on 16/3/16.
 */
public interface IReportResultService {
    /**
     * 保存志愿报告
     * @param reportResult
     * @return
     */
    Integer insertSelective(ReportResult reportResult);

    /**
     * 获取志愿报告详细
     * @param map
     * @return
     */
    ReportInfoView getReportInfoView(Map map) throws IOException;

    /**
     * 获取当前登录学生最近一次填报的志愿报告
     * @param map
     * @return
     */
    ReportResult  selectModelOne(Map map);
}