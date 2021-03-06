/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccount.java 2015-09-21 16:58:04 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserAccount extends BaseDomain<Long>{

	private Integer status;
	private Long createDate;
	private Long lastModDate;
    private String account;
    private String password;
    private Integer userType;
	/** 区域Id */
	private Long areaId;
	private boolean canTargetSchool;
	/** 省份Id */
	private String provinceId;
	/** 城市Id */
	private String cityId;
	/** 区域Id */
	private String countyId;
	/** 年级。1：高中一年级；2：高中二年级；3：高中三年级 */
	private int grade;

	private int isRegisterXueTang;

	private long userId;

	private String nickName;

	private String avatar;

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public int getIsRegisterXueTang() {
		return isRegisterXueTang;
	}

	public void setIsRegisterXueTang(int isRegisterXueTang) {
		this.isRegisterXueTang = isRegisterXueTang;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public boolean isCanTargetSchool() {
		return canTargetSchool;
	}

	public void setCanTargetSchool(boolean canTargetSchool) {
		this.canTargetSchool = canTargetSchool;
	}

	public UserAccount(){
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public void setAccount(String value) {
        this.account = value;
    }

    public String getAccount() {
        return this.account;
    }
    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }
    public void setUserType(Integer value) {
        this.userType = value;
    }

    public Integer getUserType() {
        return this.userType;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Long lastModDate) {
		this.lastModDate = lastModDate;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Status",getStatus())
			.append("CreateDate",getCreateDate())
			.append("Account",getAccount())
			.append("LastModDate",getLastModDate())
			.append("Password",getPassword())
			.append("UserType",getUserType())
			.append("CanTargetSchool",isCanTargetSchool())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserAccount == false) return false;
		if(this == obj) return true;
		UserAccount other = (UserAccount)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

