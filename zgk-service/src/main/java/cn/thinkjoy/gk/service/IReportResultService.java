package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.ReportResult;

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
}
