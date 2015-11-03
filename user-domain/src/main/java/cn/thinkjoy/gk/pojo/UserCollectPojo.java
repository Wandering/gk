/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  UserCollect.java 2015-11-02 14:42:04 $
 */





package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserCollectPojo{
	/** ID */
	private long id;
    /** 用户Id */
    private long userId;
    /** 收藏院校ID */
    private long universityId;
	/** 收藏创建时间 */
	private long createDate;

	/** 收藏院校名称 */
	private String universityName;
	/** 所在地区 */
	private String provinceName;
	/** 院校类型 */
	private String universityType;
	/** 院校隶属 */
	private String subjection;
	/** 院校特征 */
	private String propertyName;

	public UserCollectPojo(){
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getUniversityType() {
		return universityType;
	}

	public void setUniversityType(String universityType) {
		this.universityType = universityType;
	}

	public String getSubjection() {
		return subjection;
	}

	public void setSubjection(String subjection) {
		this.subjection = subjection;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
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
		if(obj instanceof UserCollectPojo == false) return false;
		if(this == obj) return true;
		UserCollectPojo other = (UserCollectPojo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

