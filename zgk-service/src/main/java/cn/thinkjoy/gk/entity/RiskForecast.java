package cn.thinkjoy.gk.entity;

import java.io.Serializable;

/**
 * Created by douzy on 16/5/11.
 */
public class RiskForecast implements Serializable {
    private Long id;
    /**
     * 院校ID
     */
    private Long universityId;
    /**
     * 院校名称
     */
    private String universityName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 计划招生数
     */
    private Integer planEnrolling;

    /**
     * 创建时间
     */
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
