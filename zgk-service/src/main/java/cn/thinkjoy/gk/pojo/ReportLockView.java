package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/6/12.
 */
public class ReportLockView implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 是否已经填报
     */
    private boolean isExistReport;
    /**
     * 省份
     */
    private String provinceCode;
    /**
     * 批次
     */
    private String batch;
    /**
     * 位次
     */
    private Integer precedence;

    /**
     * 科类
     */
    private Integer majorType;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 扩展字段
     */
    private String extendProper;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isExistReport() {
        return isExistReport;
    }

    public void setExistReport(boolean isExistReport) {
        this.isExistReport = isExistReport;
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

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public Integer getMajorType() {
        return majorType;
    }

    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getExtendProper() {
        return extendProper;
    }

    public void setExtendProper(String extendProper) {
        this.extendProper = extendProper;
    }
}
