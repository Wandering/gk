package cn.thinkjoy.gk.pojo;

/**
 * Created by wpliu on 15/9/26.
 */
public class EnrollInfo {

    private String subjectName;
    private Integer planNumber;
    private Integer enrollNumber;
    private Integer highestScore;
    private Integer highestRank;
    private Integer lowestScore;
    private Integer lowestRank;
    private Integer averageScore;
    private Integer averageRank;
    private String batch;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }

    public Integer getHighestRank() {
        return highestRank;
    }

    public void setHighestRank(Integer highestRank) {
        this.highestRank = highestRank;
    }

    public Integer getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(Integer lowestScore) {
        this.lowestScore = lowestScore;
    }

    public Integer getLowestRank() {
        return lowestRank;
    }

    public void setLowestRank(Integer lowestRank) {
        this.lowestRank = lowestRank;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getAverageRank() {
        return averageRank;
    }

    public void setAverageRank(Integer averageRank) {
        this.averageRank = averageRank;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
