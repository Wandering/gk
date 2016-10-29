/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  UniversityEnrolling.java 2016-02-01 17:02:35 $
 */





package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UniversityEnrolling extends CreateBaseDomain<Long>{
    /** 专业所属学校ID */
    private Long universityId;
    /** 省份ID */
    private String areaId;
    /** 年份 */
    private Integer year;
    /** 批次，对应字典表MAJOR_BATCH类型 */
    private Integer batch;
    /** 专业类别，1文史，2理工。对应字典表中的UNIVERSITY_MAJOR_TYPE类型 */
    private Integer majorType;
    /** 实际招生人数（查询时为0，返回‘’或者‘－’） */
    private Integer realEnrollingNumber;
    /** 录取最高分（查询时为0，返回‘’或者‘－’） */
    private Integer highestScore;
    /** 录取最低分（查询时为0，返回‘’或者‘－’） */
    private Integer lowestScore;
    /** 录取平均分（查询时为0，返回‘’或者‘－’） */
    private Integer averageScore;
    /** 省控线（无数据，暂不用） */
    private Integer proScore;
    /** 学费,xxxx元/年（无数据，暂不用） */
    private Integer schoolFee;
    /** 学制，1.四年制。对应字典表中的LENGTH_OF_SCHOOLING（无数据，暂不用） */
    private String lengthOfSchooling;
    /** 录取最高位次（无数据，暂不用） */
    private Integer highestPrecedence;
    /** 录取最低位次（无数据，暂不用） */
    private Integer lowestPrecedence;
    /** 录取平均位次（无数据，暂不用） */
    private Integer averagePrecedence;
    /** 计划招生人数（无数据，暂不用） */
    private Integer planEnrollingNumber;
    /**  */
    private Boolean isDelete;
    /** 保留字段 */
    private Boolean flag1;
    /** 保留字段 */
    private Boolean flag2;
    /** 保留字段 */
    private Boolean flag3;
    /** 保留字段 */
    private Boolean flag4;
    /** 保留字段 */
    private Boolean flag5;

	public UniversityEnrolling(){
	}
    public void setUniversityId(Long value) {
        this.universityId = value;
    }

    public Long getUniversityId() {
        return this.universityId;
    }
    public void setAreaId(String value) {
        this.areaId = value;
    }

    public String getAreaId() {
        return this.areaId;
    }
    public void setYear(Integer value) {
        this.year = value;
    }

    public Integer getYear() {
        return this.year;
    }
    public void setBatch(Integer value) {
        this.batch = value;
    }

    public Integer getBatch() {
        return this.batch;
    }
    public void setMajorType(Integer value) {
        this.majorType = value;
    }

    public Integer getMajorType() {
        return this.majorType;
    }
    public void setRealEnrollingNumber(Integer value) {
        this.realEnrollingNumber = value;
    }

    public Integer getRealEnrollingNumber() {
        return this.realEnrollingNumber;
    }
    public void setHighestScore(Integer value) {
        this.highestScore = value;
    }

    public Integer getHighestScore() {
        return this.highestScore;
    }
    public void setLowestScore(Integer value) {
        this.lowestScore = value;
    }

    public Integer getLowestScore() {
        return this.lowestScore;
    }
    public void setAverageScore(Integer value) {
        this.averageScore = value;
    }

    public Integer getAverageScore() {
        return this.averageScore;
    }
    public void setProScore(Integer value) {
        this.proScore = value;
    }

    public Integer getProScore() {
        return this.proScore;
    }
    public void setSchoolFee(Integer value) {
        this.schoolFee = value;
    }

    public Integer getSchoolFee() {
        return this.schoolFee;
    }
    public void setLengthOfSchooling(String value) {
        this.lengthOfSchooling = value;
    }

    public String getLengthOfSchooling() {
        return this.lengthOfSchooling;
    }
    public void setHighestPrecedence(Integer value) {
        this.highestPrecedence = value;
    }

    public Integer getHighestPrecedence() {
        return this.highestPrecedence;
    }
    public void setLowestPrecedence(Integer value) {
        this.lowestPrecedence = value;
    }

    public Integer getLowestPrecedence() {
        return this.lowestPrecedence;
    }
    public void setAveragePrecedence(Integer value) {
        this.averagePrecedence = value;
    }

    public Integer getAveragePrecedence() {
        return this.averagePrecedence;
    }
    public void setPlanEnrollingNumber(Integer value) {
        this.planEnrollingNumber = value;
    }

    public Integer getPlanEnrollingNumber() {
        return this.planEnrollingNumber;
    }
    public void setIsDelete(Boolean value) {
        this.isDelete = value;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }
    public void setFlag1(Boolean value) {
        this.flag1 = value;
    }

    public Boolean getFlag1() {
        return this.flag1;
    }
    public void setFlag2(Boolean value) {
        this.flag2 = value;
    }

    public Boolean getFlag2() {
        return this.flag2;
    }
    public void setFlag3(Boolean value) {
        this.flag3 = value;
    }

    public Boolean getFlag3() {
        return this.flag3;
    }
    public void setFlag4(Boolean value) {
        this.flag4 = value;
    }

    public Boolean getFlag4() {
        return this.flag4;
    }
    public void setFlag5(Boolean value) {
        this.flag5 = value;
    }

    public Boolean getFlag5() {
        return this.flag5;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UniversityId",getUniversityId())
			.append("AreaId",getAreaId())
			.append("Year",getYear())
			.append("Batch",getBatch())
			.append("MajorType",getMajorType())
			.append("RealEnrollingNumber",getRealEnrollingNumber())
			.append("HighestScore",getHighestScore())
			.append("LowestScore",getLowestScore())
			.append("AverageScore",getAverageScore())
			.append("ProScore",getProScore())
			.append("SchoolFee",getSchoolFee())
			.append("LengthOfSchooling",getLengthOfSchooling())
			.append("HighestPrecedence",getHighestPrecedence())
			.append("LowestPrecedence",getLowestPrecedence())
			.append("AveragePrecedence",getAveragePrecedence())
			.append("PlanEnrollingNumber",getPlanEnrollingNumber())
			.append("CreateDate",getCreateDate())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("IsDelete",getIsDelete())
			.append("Flag1",getFlag1())
			.append("Flag2",getFlag2())
			.append("Flag3",getFlag3())
			.append("Flag4",getFlag4())
			.append("Flag5",getFlag5())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof UniversityEnrolling == false) return false;
		if(this == obj) return true;
		UniversityEnrolling other = (UniversityEnrolling)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

