package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

/**
 * Created by douzy on 16/3/16.
 */
public class ReportResult extends CreateBaseDomain<Long> {
    private Long id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 科类
     */
    private Integer majorType;
    /**
     * 位次
     */
    private Integer precedence;
    /**
     * 是否完整
     */
    private byte isComplete;
    /**
     * 是否合理
     */
    private byte isReasonable;
    /**
     * 报告结果
     */
    private String reportResultJson;

    /**
     * 省份
     */
    private String provinceCode;

    /**
     * 批次
     */
    private String batch;
    /**
     * 评测日期
     */
    private Long createTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMajorType() {
        return majorType;
    }

    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public byte isComplete() {
        return isComplete;
    }

    public void setComplete(byte isComplete) {
        this.isComplete = isComplete;
    }

    public byte isReasonable() {
        return isReasonable;
    }

    public void setReasonable(byte isReasonable) {
        this.isReasonable = isReasonable;
    }

    public String getReportResultJson() {
        return reportResultJson;
    }

    public void setReportResultJson(String reportResultJson) {
        this.reportResultJson = reportResultJson;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
