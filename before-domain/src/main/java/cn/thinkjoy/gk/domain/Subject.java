/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  Subject.java 2015-10-15 15:19:12 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Subject extends CreateBaseDomain<Long>{
	/** 科目名称 */
	private String subjectName;
	/** 区域Id */
	private Long areaId;

	public Subject(){
	}
	public void setSubjectName(String value) {
		this.subjectName = value;
	}

	public String getSubjectName() {
		return this.subjectName;
	}
	public void setAreaId(Long value) {
		this.areaId = value;
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("Id",getId())
				.append("SubjectName", getSubjectName())
				.append("Status",getStatus())
				.append("Creator",getCreator())
				.append("CreateDate",getCreateDate())
				.append("LastModDate",getLastModDate())
				.append("LastModifier",getLastModifier())
				.append("AreaId",getAreaId())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof Subject == false) return false;
		if(this == obj) return true;
		Subject other = (Subject)obj;
		return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
	}
}

