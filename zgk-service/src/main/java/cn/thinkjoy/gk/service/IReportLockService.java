package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.ReportLock;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/6/12.
 */
public interface IReportLockService {
    public Integer insertSelective(ReportLock reportLock);
    public List<ReportLock> selectReportLock(Map map);
}
