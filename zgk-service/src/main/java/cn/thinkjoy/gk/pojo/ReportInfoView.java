package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by douzy on 16/3/17.
 */
public class ReportInfoView implements Serializable {
    /**
     * 院校基础信息
     */
    public List<ReportUniversityView> reportUniversityViewList;

    /**
     * 报告结果
     */
    public ReportResultView reportResultView;

    public List<ReportUniversityView> getReportUniversityViewList() {
        return reportUniversityViewList;
    }

    public void setReportUniversityViewList(List<ReportUniversityView> reportUniversityViewList) {
        this.reportUniversityViewList = reportUniversityViewList;
    }

    public ReportResultView getReportResultView() {
        return reportResultView;
    }

    public void setReportResultView(ReportResultView reportResultView) {
        this.reportResultView = reportResultView;
    }
}
