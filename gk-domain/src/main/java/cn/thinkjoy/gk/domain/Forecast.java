/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  Forecast.java 2016-01-22 11:48:22 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Forecast extends CreateBaseDomain<Long>{
    /** 用户Id */
    private Long userId;
    /** 科类(文史理工) */
    private String type;
    /** 科类ID(文史理工) */
    private Integer typeId;
    /** 院校ID */
    private Long universityId;
    /** 院校名称 */
    private String universityName;
    /** 成绩 */
    private Integer achievement;
    /** 最低分 */
    private Integer lowestScore;
    /** 平均分 */
    private String averageScore;

	public Forecast(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }
    public void setTypeId(Integer value) {
        this.typeId = value;
    }

    public Integer getTypeId() {
        return this.typeId;
    }
    public void setUniversityId(Long value) {
        this.universityId = value;
    }

    public Long getUniversityId() {
        return this.universityId;
    }
    public void setUniversityName(String value) {
        this.universityName = value;
    }

    public String getUniversityName() {
        return this.universityName;
    }
    public void setAchievement(Integer value) {
        this.achievement = value;
    }

    public Integer getAchievement() {
        return this.achievement;
    }
    public void setLowestScore(Integer value) {
        this.lowestScore = value;
    }

    public Integer getLowestScore() {
        return this.lowestScore;
    }
    public void setAverageScore(String value) {
        this.averageScore = value;
    }

    public String getAverageScore() {
        return this.averageScore;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Type",getType())
			.append("TypeId",getTypeId())
			.append("UniversityId",getUniversityId())
			.append("UniversityName",getUniversityName())
			.append("Achievement",getAchievement())
			.append("LowestScore",getLowestScore())
			.append("AverageScore",getAverageScore())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModifier",getLastModifier())
			.append("LastModDate",getLastModDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Forecast == false) return false;
		if(this == obj) return true;
		Forecast other = (Forecast)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

