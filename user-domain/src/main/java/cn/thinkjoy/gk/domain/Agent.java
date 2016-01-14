/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  Agent.java 2015-10-16 14:19:05 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Agent extends CreateBaseDomain<Long>{
	/** 明层 */
	private String name;
	/** 电话 */
	private String telphone;
	/** 地址 */
	private String address;
	/** 区域Id */
	private Long areaId;

	public Agent(){
	}
	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}
	public void setTelphone(String value) {
		this.telphone = value;
	}

	public String getTelphone() {
		return this.telphone;
	}
	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
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
				.append("Status", getStatus())
				.append("Creator",getCreator())
				.append("CreateDate",getCreateDate())
				.append("LastModifier",getLastModifier())
				.append("LastModDate",getLastModDate())
				.append("Name",getName())
				.append("Telphone",getTelphone())
				.append("Address",getAddress())
				.append("AreaId",getAreaId())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof Agent == false) return false;
		if(this == obj) return true;
		Agent other = (Agent)obj;
		return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
	}
}

