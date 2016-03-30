package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.ReportResult;
import cn.thinkjoy.gk.pojo.ReportInfoView;

import java.io.IOException;
import java.util.List;
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

    /**
     * 获取位次列表
     * @param map
     * @return
     */
    List<Integer> selectPrecedence(Map map);

    /**
     * 获取位次列表
     * @param map
     * @param precedence
     * @return
     */
    Integer getPrecedence(String tableName,Integer precedence);

    /**
     * 梯度合理性判断
     * @param reportJson
     * @return
     */
     boolean reportIsReasonable(ReportResult reportResult);

    /**
     * 完整性评估
     * @param reportResult
     * @return
     */
    boolean reportIsComplete(ReportResult reportResult);
}
