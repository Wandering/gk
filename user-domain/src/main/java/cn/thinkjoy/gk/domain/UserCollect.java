/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  UserCollect.java 2015-11-02 14:42:04 $
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
    private Long userId;
    /** 收藏院校ID */
    private Long universityId;
	/** 收藏创建时间 */
	private Long createDate;

	public UserCollect(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setUniversityId(Long value) {
        this.universityId = value;
    }

    public Long getUniversityId() {
        return this.universityId;
    }

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CreateDate",getCreateDate())
			.append("UserId",getUserId())
			.append("UniversityId",getUniversityId())
			.toString();
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

