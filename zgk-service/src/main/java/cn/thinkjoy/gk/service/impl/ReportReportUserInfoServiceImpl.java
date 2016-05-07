package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IReportUserInfoDao;
import cn.thinkjoy.gk.entity.ReportUserInfo;
import cn.thinkjoy.gk.service.IReportUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by douzy on 16/5/4.
 */
@Service
public class ReportReportUserInfoServiceImpl implements IReportUserInfoService {
    @Resource
    IReportUserInfoDao iReportUserInfoDao;

    @Override
    public ReportUserInfo getUserInfoByUserId(Map map) {
        return iReportUserInfoDao.getUserInfoByUserId(map);
    }
}
