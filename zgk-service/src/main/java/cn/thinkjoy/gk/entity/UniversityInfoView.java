package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.math.BigDecimal;

/**
 * Created by douzy on 16/3/14.
 */
public class UniversityInfoView extends CreateBaseDomain<Long> {
    private Long id;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 科类
     */
    private String majorType;
    /**
     * 批次
     */
    private String batch;
    /**
     * 院校ID
     */
    private Long universityId;
    /**
     * 院校名称
     */
    private String universityName;
    /**
     * 线差
     */
    private Integer scoreDiff;
    /**
     * 预测线差
     */
    private Integer preScoreDiff;
    /**
     * 录取概率
     */
    private BigDecimal enrollRate;
    /**
     * 分数利用率
     */
    private BigDecimal scoreUseRate;

    /**
     * 梯度
     */
    private Integer sequence;

    private byte isProportion;
    private byte isRange;
    private String rankTrend;
    private String subjection;
    private Integer type;
    private String property;
    private String code;
    private Integer planEnrolling;
    private Integer averageScore;



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Integer getScoreDiff() {
        return scoreDiff;
    }

    public void setScoreDiff(Integer scoreDiff) {
        this.scoreDiff = scoreDiff;
    }

    public Integer getPreScoreDiff() {
        return preScoreDiff;
    }

    public void setPreScoreDiff(Integer preScoreDiff) {
        this.preScoreDiff = preScoreDiff;
    }

    public BigDecimal getEnrollRate() {
        return enrollRate;
    }

    public void setEnrollRate(BigDecimal enrollRate) {
        this.enrollRate = enrollRate;
    }

    public BigDecimal getScoreUseRate() {
        return scoreUseRate;
    }

    public void setScoreUseRate(BigDecimal scoreUseRate) {
        this.scoreUseRate = scoreUseRate;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public byte getIsProportion() {
        return isProportion;
    }

    public void setIsProportion(byte isProportion) {
        this.isProportion = isProportion;
    }

    public byte getIsRange() {
        return isRange;
    }

    public void setIsRange(byte isRange) {
        this.isRange = isRange;
    }

    public String getRankTrend() {
        return rankTrend;
    }

    public void setRankTrend(String rankTrend) {
        this.rankTrend = rankTrend;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPlanEnrolling() {
        return planEnrolling;
    }

    public void setPlanEnrolling(Integer planEnrolling) {
        this.planEnrolling = planEnrolling;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }
}
