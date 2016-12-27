/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  ScoreUserRecord.java 2016-12-27 15:23:26 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ScoreUserRecord extends BaseDomain{
    private Long userId;
    private Integer type;
    private Integer universityId;
    private Long areaId;
    private String year;
    private String total;
    private String majorName;
    private Integer majorType;
    private Long scoreRowId;

	public ScoreUserRecord(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
    }
    public void setUniversityId(Integer value) {
        this.universityId = value;
    }

    public Integer getUniversityId() {
        return this.universityId;
    }
    public void setAreaId(Long value) {
        this.areaId = value;
    }

    public Long getAreaId() {
        return this.areaId;
    }
    public void setYear(String value) {
        this.year = value;
    }

    public String getYear() {
        return this.year;
    }
    public void setTotal(String value) {
        this.total = value;
    }

    public String getTotal() {
        return this.total;
    }
    public void setMajorName(String value) {
        this.majorName = value;
    }

    public String getMajorName() {
        return this.majorName;
    }
    public void setMajorType(Integer value) {
        this.majorType = value;
    }

    public Integer getMajorType() {
        return this.majorType;
    }
    public void setScoreRowId(Long value) {
        this.scoreRowId = value;
    }

    public Long getScoreRowId() {
        return this.scoreRowId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Type",getType())
			.append("UniversityId",getUniversityId())
			.append("AreaId",getAreaId())
			.append("Year",getYear())
			.append("Total",getTotal())
			.append("MajorName",getMajorName())
			.append("MajorType",getMajorType())
			.append("ScoreRowId",getScoreRowId())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ScoreUserRecord == false) return false;
		if(this == obj) return true;
		ScoreUserRecord other = (ScoreUserRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

