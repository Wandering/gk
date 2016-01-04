/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  UserCollect.java 2015-12-24 15:27:50 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserCollect extends BaseDomain{
    /** 用户Id */
    private long userId;
    /** 收藏院校ID */
    private long projectId;
	/** 收藏创建时间 */
	private long createDate;
	/** 1为院校收藏（默认），2为课程收藏 */
	private String type;

	public UserCollect(){
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof UserCollect == false) return false;
		if(this == obj) return true;
		UserCollect other = (UserCollect)obj;
		return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
	}
}

