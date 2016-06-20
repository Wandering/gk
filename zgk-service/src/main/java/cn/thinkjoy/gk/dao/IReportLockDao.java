package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.ReportLock;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/6/12.
 */
public interface IReportLockDao extends IBaseDAO<ReportLock> {

    List<ReportLock> selectReportLock(Map map);

    Integer insertSelective(ReportLock reportLock);
}
