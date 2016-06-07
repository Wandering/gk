package cn.thinkjoy.gk.dto;

import cn.thinkjoy.common.domain.CreateBaseDomain;

/**
 * Created by zuohao on 15/12/30.
 */
public class UniversityMajorDTO extends CreateBaseDomain {
    /** 学校ID */
    private Long universityId;
    /** 学校名称 */
    private String universityName;
    /** 专业名称 */
    private String majorName;
    /** 学历层次 */
    private Integer educationLevel;
    /** 薪资排名 */
    private Integer salaryRank;
    /** 就业排名 */
    private Integer jobRank;
    /** 特色重点专业(暂时不用) */
    private String majorFeature;
    /** 获得学位(无数据暂时不用) */
    private String gainDegree;
    /** 专业Id(名称不匹配暂时不用) */
    private Long majorId;
    /** 专业排名(无数据暂时不用) */
    private Integer majorRank;
    /** 专业类型(无数据暂时不用) */
    private Integer majorType;
    /** 专业科类，1文史，2理工。对应字典表中的MAJOR_SUBJECT类型(无数据暂时不用) */
    private Integer majorSubject;
    /** 是否逻辑删除 */
    private Boolean isDelete;
    private String majorTypeName;
    private String majorSubjectName;
    private String educationLevelName;
    private String gainDegreeName;

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

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Integer getSalaryRank() {
        return salaryRank;
    }

    public void setSalaryRank(Integer salaryRank) {
        this.salaryRank = salaryRank;
    }

    public Integer getJobRank() {
        return jobRank;
    }

    public void setJobRank(Integer jobRank) {
        this.jobRank = jobRank;
    }

    public String getMajorFeature() {
        return majorFeature;
    }

    public void setMajorFeature(String majorFeature) {
        this.majorFeature = majorFeature;
    }

    public String getGainDegree() {
        return gainDegree;
    }

    public void setGainDegree(String gainDegree) {
        this.gainDegree = gainDegree;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Integer getMajorRank() {
        return majorRank;
    }

    public void setMajorRank(Integer majorRank) {
        this.majorRank = majorRank;
    }

    public Integer getMajorType() {
        return majorType;
    }

    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    public Integer getMajorSubject() {
        return majorSubject;
    }

    public void setMajorSubject(Integer majorSubject) {
        this.majorSubject = majorSubject;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getMajorTypeName() {
        return majorTypeName;
    }

    public void setMajorTypeName(String majorTypeName) {
        this.majorTypeName = majorTypeName;
    }

    public String getMajorSubjectName() {
        return majorSubjectName;
    }

    public void setMajorSubjectName(String majorSubjectName) {
        this.majorSubjectName = majorSubjectName;
    }

    public String getEducationLevelName() {
        return educationLevelName;
    }

    public void setEducationLevelName(String educationLevelName) {
        this.educationLevelName = educationLevelName;
    }

    public String getGainDegreeName() {
        return gainDegreeName;
    }

    public void setGainDegreeName(String gainDegreeName) {
        this.gainDegreeName = gainDegreeName;
    }
}
