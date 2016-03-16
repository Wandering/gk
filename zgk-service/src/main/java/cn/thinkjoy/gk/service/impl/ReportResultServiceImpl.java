package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IReportResultDao;
import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.service.IReportResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by douzy on 16/3/16.
 */
@Service
public class ReportResultServiceImpl implements IReportResultService {

    @Resource
    IReportResultDao iReportResultDao;

    /**
     * 保存志愿报告
     * @param reportResult
     * @return
     */
    @Override
    public Integer insertSelective(ReportResult reportResult) {
        return iReportResultDao.insertSelective(reportResult);
    }
}
