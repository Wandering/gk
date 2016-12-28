package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/10/8.
 */
public class SpecialMajorDto implements Serializable {

    /**
     * 学校ID
     */
    private long universityId;

    /**
     * 学校名称
     */
    private String universityName;

    /**
     * 科类
     */
    private int majorType;

    /**
     * 特殊批次类别: 如，本科提前批(包括本科提前批A、B)
     */
    private String majorCatagory;

    /**
     * 计划招生人数
     */
    private int planEnrollingNumber;

    /**
     * 实际招生人数
     */
    private int realEnrollingNumber;

    /**
     * 录取最高分
     */
    private String highestScore;

    /**
     * 录取最低分
     */
    private String lowestScore;

    /**
     * 录取平均分
     */
    private String averageScore;

    /**
     * 录取批次
     */
    private String batch;

    /**
     * 年份
     */
    private String year;

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public int getMajorType() {
        return majorType;
    }

    public void setMajorType(int majorType) {
        this.majorType = majorType;
    }

    public String getMajorCatagory() {
        return majorCatagory;
    }

    public void setMajorCatagory(String majorCatagory) {
        this.majorCatagory = majorCatagory;
    }

    public int getPlanEnrollingNumber() {
        return planEnrollingNumber;
    }

    public void setPlanEnrollingNumber(int planEnrollingNumber) {
        this.planEnrollingNumber = planEnrollingNumber;
    }

    public int getRealEnrollingNumber() {
        return realEnrollingNumber;
    }

    public void setRealEnrollingNumber(int realEnrollingNumber) {
        this.realEnrollingNumber = realEnrollingNumber;
    }

    public String getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(String highestScore) {
        this.highestScore = highestScore;
    }

    public String getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(String lowestScore) {
        this.lowestScore = lowestScore;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
