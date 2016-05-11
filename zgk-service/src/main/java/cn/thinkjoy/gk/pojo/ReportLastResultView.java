package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/5/11.
 */
public class ReportLastResultView implements Serializable {
    private boolean isReport;
    private Long createTime;

    public boolean isReport() {
        return isReport;
    }

    public void setReport(boolean isReport) {
        this.isReport = isReport;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
