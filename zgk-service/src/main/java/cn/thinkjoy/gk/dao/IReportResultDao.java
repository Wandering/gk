package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.ReportResult;

/**
 * Created by douzy on 16/3/16.
 */
public interface IReportResultDao extends IBaseDAO<ReportResult> {
    Integer insertSelective(ReportResult reportResult);
}
