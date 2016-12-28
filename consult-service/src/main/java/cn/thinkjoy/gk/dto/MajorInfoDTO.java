package cn.thinkjoy.gk.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/5.
 */
public class MajorInfoDTO implements Serializable {
    private String id;
    private String majorName;
    private String majorCode;
    private String degreeOffered;
    private String schoolingDuration;
    private String offerCourses;
    private String majorIntroduce;
    private String employmentRate;
    private String salary;
    private String salaryRank;
    private String fmRatio;

    public String getSalaryRank() {
        return salaryRank;
    }

    public void setSalaryRank(String salaryRank) {
        this.salaryRank = salaryRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getDegreeOffered() {
        return degreeOffered;
    }

    public void setDegreeOffered(String degreeOffered) {
        this.degreeOffered = degreeOffered;
    }

    public String getSchoolingDuration() {
        return schoolingDuration;
    }

    public void setSchoolingDuration(String schoolingDuration) {
        this.schoolingDuration = schoolingDuration;
    }

    public String getOfferCourses() {
        return offerCourses;
    }

    public void setOfferCourses(String offerCourses) {
        this.offerCourses = offerCourses;
    }

    public String getMajorIntroduce() {
        return majorIntroduce;
    }

    public void setMajorIntroduce(String majorIntroduce) {
        this.majorIntroduce = majorIntroduce;
    }

    public String getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(String employmentRate) {
        this.employmentRate = employmentRate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getFmRatio() {
        return fmRatio;
    }

    public void setFmRatio(String fmRatio) {
        this.fmRatio = fmRatio;
    }
}
