package cn.thinkjoy.gk.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/2/2.
 */
public class UniversityMajorEnrollingPlanDTO implements Serializable{
    private Long majoredId;
    private String majoredName;
    private int year;
    /** 批次 */
    private String batchName;
    /** 科类 */
    private String majorType;
    /**
     *  招生性质
     */
    private String enrollType;
    /** 招生人数 */
    private int planEnrolling;
    /** 学年，学制 */
    private String lengthOfSchooling;
    /** 续费 */
    private int schoolFee;

    public Long getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(Long majoredId) {
        this.majoredId = majoredId;
    }

    public String getMajoredName() {
        return majoredName;
    }

    public void setMajoredName(String majoredName) {
        this.majoredName = majoredName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getEnrollType() {
        return enrollType;
    }

    public void setEnrollType(String enrollType) {
        this.enrollType = enrollType;
    }

    public int getPlanEnrolling() {
        return planEnrolling;
    }

    public void setPlanEnrolling(int planEnrolling) {
        this.planEnrolling = planEnrolling;
    }

    public String getLengthOfSchooling() {
        return lengthOfSchooling;
    }

    public void setLengthOfSchooling(String lengthOfSchooling) {
        this.lengthOfSchooling = lengthOfSchooling;
    }

    public int getSchoolFee() {
        return schoolFee;
    }

    public void setSchoolFee(int schoolFee) {
        this.schoolFee = schoolFee;
    }
}
