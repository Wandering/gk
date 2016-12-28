package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import cn.thinkjoy.gk.common.ReportUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyp on 16/30/8.
 */
public class UniversityEnrollView extends CreateBaseDomain<Long> implements Comparable<UniversityEnrollView> {
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
     * 预测线差
     */
    private Integer preScoreDiff;
    /**
     * 录取概率
     */
    private BigDecimal enrollRate;
    /**
     * 招生人数
     */
    private Integer planEnrolling;
    /**
     * 最高分
     */
    private Integer highestScore;

    /**
     * 最低分
     */
    private Integer lowestScore;

    /**
     * 平均分
     */
    private Integer averageScore;

    /**
     * 平均分年份
     */
    private Integer averageYear;
    /**
     * 是否被定为目标院校
     */
    private Integer isFavorite;
    /**
     * 是否预测过
     */
    private String batchName;

    /**
     * 院校排名
     */
    private Integer rank;


    /**
     * 是否是当前省份
     */
    private Integer isCurrArea;

    /**
     * 院校排名
     */
    private String xcRank;

    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }

    public Integer getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(Integer lowestScore) {
        this.lowestScore = lowestScore;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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
    public void setEnrollRate(BigDecimal enrollRate,String province) {
        this.enrollRate = enrollRate;
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

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


    public String getXcRank() {
        return xcRank;
    }

    public void setXcRank(String xcRank) {
        this.xcRank = xcRank;
    }

    public Integer getIsCurrArea() {
        return isCurrArea;
    }

    public void setIsCurrArea(Integer isCurrArea) {
        this.isCurrArea = isCurrArea;
    }

    @Override
    public int compareTo(UniversityEnrollView universityInfoView) {
        return this.rank.compareTo(universityInfoView.getRank());
    }
}
