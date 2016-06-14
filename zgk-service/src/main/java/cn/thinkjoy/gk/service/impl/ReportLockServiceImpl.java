package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IReportLockDao;
import cn.thinkjoy.gk.entity.ReportLock;
import cn.thinkjoy.gk.service.IReportLockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/6/12.
 */
@Service
public class ReportLockServiceImpl implements IReportLockService {

    @Resource
    IReportLockDao iReportLockDao;

    @Override
    public List<ReportLock> selectReportLock(Map map) {
        return iReportLockDao.selectReportLock(map);
    }

    @Override
    public Integer insertSelective(ReportLock reportLock){
        return iReportLockDao.insertSelective(reportLock);
    }
}
