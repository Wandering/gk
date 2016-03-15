package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

/**
 * 专业信息
 * Created by douzy on 16/3/15.
 */
public class UniversityMajorEnrollingPlan extends CreateBaseDomain<Long> {
    private Long id;
    /**
     * 学校名称
     */
    private String universityName;
    /**
     * 学校ID
     */
    private Long universityId;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 省份ID
     */
    private Long areaId;
    /**
     * 批次
     */
    private String batch;
    /**
     * 科类，1文科，2理科
     */
    private String majorType;
    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 计划招生数
     */
    private Integer planEnrolling;
    /**
     * 学年
     */
    private Integer lengthOfSchooling;
    /**
     * 学费
     */
    private Integer schoolFee;
    /**
     * 专业ID（专业名称不匹配，暂不用）
     */
    private Long majorId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getPlanEnrolling() {
        return planEnrolling;
    }

    public void setPlanEnrolling(Integer planEnrolling) {
        this.planEnrolling = planEnrolling;
    }

    public Integer getLengthOfSchooling() {
        return lengthOfSchooling;
    }

    public void setLengthOfSchooling(Integer lengthOfSchooling) {
        this.lengthOfSchooling = lengthOfSchooling;
    }

    public Integer getSchoolFee() {
        return schoolFee;
    }

    public void setSchoolFee(Integer schoolFee) {
        this.schoolFee = schoolFee;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }
}
