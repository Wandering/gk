package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * 志愿报告结果  扩展
 * Created by douzy on 16/3/17.
 */
public class ReportResultView implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 省控线
     */
    private String controllLine;
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
     *扩展字段
     */
    private String extendProper;

    /**
     * 评测日期
     */
    private Long createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getControllLine() {
        return controllLine;
    }

    public void setControllLine(String controllLine) {
        this.controllLine = controllLine;
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

    public String getExtendProper() {
        return extendProper;
    }

    public void setExtendProper(String extendProper) {
        this.extendProper = extendProper;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
