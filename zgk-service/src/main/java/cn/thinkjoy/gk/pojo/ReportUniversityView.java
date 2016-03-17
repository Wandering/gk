package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by douzy on 16/3/17.
 */
public class ReportUniversityView implements Serializable {
    /**
     * 梯度
     */
    public Integer sequence;
    /**
     * 院校名称
     */
    private String universityName;
    /**
     * 最低分均值
     */
    private Integer  lowestScoreAvg;
    /**
     *平均分均值
     */
    private Integer averageScoreAvg;
    /**
     * 招生数
     */
    private Integer enrollingNumber;

    /**
     * 是否投档1.2
     */
    private boolean isProportion;
    /**
     * 是否极差
     */
    private boolean isRange;
    /**
     * 院校热度
     */
    private String rankTrend;
    /**
     * 院校特征
     */
    private String property;

    /**
     * 录取率
     */
    private BigDecimal enrollRate;
    /**
     * 利用率
     */
    private BigDecimal scoreUseRate;

    /**
     * 智高考排名
     */
    private Integer ranking;




    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Integer getLowestScoreAvg() {
        return lowestScoreAvg;
    }

    public void setLowestScoreAvg(Integer lowestScoreAvg) {
        this.lowestScoreAvg = lowestScoreAvg;
    }

    public Integer getAverageScoreAvg() {
        return averageScoreAvg;
    }

    public void setAverageScoreAvg(Integer averageScoreAvg) {
        this.averageScoreAvg = averageScoreAvg;
    }

    public Integer getEnrollingNumber() {
        return enrollingNumber;
    }

    public void setEnrollingNumber(Integer enrollingNumber) {
        this.enrollingNumber = enrollingNumber;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
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

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

}
