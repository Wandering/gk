package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.math.BigDecimal;

/**
 * Created by douzy on 16/3/14.
 */
public class UniversityInfoView extends CreateBaseDomain<Long> implements Comparable<UniversityInfoView> {
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
     * 位次
     */
    private Integer precedence;
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
    /**
     * 是否1.2
     */
    private byte isProportion;
    /**
     * 是否极差
     */
    private byte isRange;
    /**
     * 院校热度
     */
    private String rankTrend;
    /**
     * 院校隶属
     */
    private String subjection;
    /**
     * 所在地
     */
    private String areaName;
    private Integer type;
    private String typeName;
    /**
     * 院校特征
     */
    private String property;
    private Integer areaId;
    private String code;
    /**
     * 招生人数
     */
    private Integer planEnrolling;
    /**
     * 平均分
     */
    private Integer averageScore;
    /**
     * 平均分年份
     */
    private Integer averageYear;




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

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public Integer getAverageYear() {
        return averageYear;
    }

    public void setAverageYear(Integer averageYear) {
        this.averageYear = averageYear;
    }

    @Override
    public int compareTo(UniversityInfoView universityInfoView) {
        return this.sequence.compareTo(universityInfoView.getSequence());
    }
}
