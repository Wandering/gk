package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/15.
 */
public class SpecialtyView implements Serializable {

    /**
     * 院校ID
     */
    private Long universityId;
    /**
     * 院校名称
     */
    private String universityName;
    /**
     * 专业ID
     */
    private Long  specialtyId;
    /**
     * 专业名称
     */
    private String specialtyName;

    /**
     * 招生计划数
     */
    private Integer planEnrolling;

    /**
     * 招生性质
     */
    private String  enrollType;

    /**
     * 科类
     */
    private String majorType;

    /**
     * 科类
     */
    private Integer year;

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

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Integer getPlanEnrolling() {
        return planEnrolling;
    }

    public void setPlanEnrolling(Integer planEnrolling) {
        this.planEnrolling = planEnrolling;
    }

    public String getEnrollType() {
        return enrollType;
    }

    public void setEnrollType(String enrollType) {
        this.enrollType = enrollType;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
