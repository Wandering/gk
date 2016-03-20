package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by douzy on 16/3/17.
 */
public class SelfReportUniversityView implements Serializable {
    /**
     * 院校ID
     */
    private Integer id;
    /**
     * 院校名称
     */
    private String name;
    /**
     * 院校特征
     */
    private String property;
    /**
     * 院校隶属
     */
    private String subjection;
    /**
     * 院校类别
     */
    private Integer type;

    /**
     * 院校名称
     */
    private String typeName;
    /**
     * 院校录取率
     */
    private BigDecimal enrollRate;
    /**
     * 院校招生人数
     */
    private Integer enrollingNumber;
    /**
     * 院校录取平均分
     */
    private Integer averageScore;

    /**
     * 是否投档1.2
     */
    private boolean isProportion;

    /**
     * 时候极差
     */
    private boolean isRange;
    /**
     * 热度
     */
    private String rankTrend;

    /**
     * 0: 不服从  1:全部服从  2:部分服从
     */
    private Integer isComplied;

    /**
     * 专业集
     */
    private List<SelfReportMajorView> selfReportMajorViewList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getSubjection() {
        return subjection;
    }

    public void setSubjection(String subjection) {
        this.subjection = subjection;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getEnrollRate() {
        return enrollRate;
    }

    public void setEnrollRate(BigDecimal enrollRate) {
        this.enrollRate = enrollRate;
    }

    public Integer getEnrollingNumber() {
        return enrollingNumber;
    }

    public void setEnrollingNumber(Integer enrollingNumber) {
        this.enrollingNumber = enrollingNumber;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public boolean isProportion() {
        return isProportion;
    }

    public void setProportion(boolean isProportion) {
        this.isProportion = isProportion;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean isRange) {
        this.isRange = isRange;
    }

    public String getRankTrend() {
        return rankTrend;
    }

    public void setRankTrend(String rankTrend) {
        this.rankTrend = rankTrend;
    }

    public Integer getIsComplied() {
        return isComplied;
    }

    public void setIsComplied(Integer isComplied) {
        this.isComplied = isComplied;
    }

    public List<SelfReportMajorView> getSelfReportMajorViewList() {
        return selfReportMajorViewList;
    }

    public void setSelfReportMajorViewList(List<SelfReportMajorView> selfReportMajorViewList) {
        this.selfReportMajorViewList = selfReportMajorViewList;
    }
}
