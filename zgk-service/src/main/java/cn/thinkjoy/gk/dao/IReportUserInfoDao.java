package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.ReportUserInfo;

import java.util.Map;

/**
 * Created by douzy on 16/5/4.
 */
public interface IReportUserInfoDao extends IBaseDAO<ReportUserInfo> {
     ReportUserInfo getUserInfoByUserId(Map map);
}
