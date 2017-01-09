package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by yangyongping on 2016/11/18.
 */
public class ExpertCustomerApeskDTO extends BaseDomain<Long> {
    //测评名称
    private String apeskName;
    //测评类型
    private Integer acId;
    //测评id
    private String reportId;
    //测评时间
    private String reportDate;

    private String reportUrl;

    private String uid;

    public String getApeskName() {
        return apeskName;
    }

    public void setApeskName(String apeskName) {
        this.apeskName = apeskName;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
