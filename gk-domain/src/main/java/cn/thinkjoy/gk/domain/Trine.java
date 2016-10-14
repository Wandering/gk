/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  City.java 2015-09-26 09:20:33 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trine extends BaseDomain {
	private String universityName;
    private Long universityId;
    private String majorCode;
    private String majorName;
    private String majorType;
    private String batchName;
    private String property;
    private String lengthOfSchooling;
    private String universityIntro;
    private String planEnrollingNumber;
    private String areaId;
    private String year;
    private String extInfo;

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorType() {
		return majorType;
	}

	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getLengthOfSchooling() {
		return lengthOfSchooling;
	}

	public void setLengthOfSchooling(String lengthOfSchooling) {
		this.lengthOfSchooling = lengthOfSchooling;
	}

	public String getUniversityIntro() {
		return universityIntro;
	}

	public void setUniversityIntro(String universityIntro) {
		this.universityIntro = universityIntro;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getPlanEnrollingNumber() {
		return planEnrollingNumber;
	}

	public void setPlanEnrollingNumber(String planEnrollingNumber) {
		this.planEnrollingNumber = planEnrollingNumber;
	}
}

