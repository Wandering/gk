/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  UserCollect.java 2015-11-02 14:42:04 $
 */





package cn.thinkjoy.gk.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UserCollectPojo{
	/** ID */
	private long id;
    /** 用户Id */
    private long userId;
    /** 收藏院校ID */
    private long universityId;
	/** 收藏创建时间 */
	private long createDate;

	/** 学校logo */
	private String universityLogo;
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
	/** 收藏人数 */
	private int number;

	public UserCollectPojo(){
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(long universityId) {
		this.universityId = universityId;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getUniversityLogo() {
		return universityLogo;
	}

	public void setUniversityLogo(String universityLogo) {
		this.universityLogo = universityLogo;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

