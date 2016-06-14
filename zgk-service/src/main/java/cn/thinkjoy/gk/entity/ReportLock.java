package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.io.Serializable;

/**
 * Created by douzy on 16/6/12.
 */
public class ReportLock  extends CreateBaseDomain<Integer> implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer score;
    private Integer majorType;
    private Integer precedence;
    private String provinceCode;
    private String batch;
    /**
     * 扩展字段
     */
    private String extendProper;

    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMajorType() {
        return majorType;
    }

    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getExtendProper() {
        return extendProper;
    }

    public void setExtendProper(String extendProper) {
        this.extendProper = extendProper;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
