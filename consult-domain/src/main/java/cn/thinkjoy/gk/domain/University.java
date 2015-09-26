/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  University.java 2015-09-26 11:19:41 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class University extends CreateBaseDomain{
    private String code;
    private String name;
    private String subjection;
    private String educationLevel;
    private String property;
    private String type;
    private String url;
    private String address;
    private String contactPhone;
    private String entranceIntro;
    private String universityIntro;
    private Long provinceId;
    private String provinceName;
    private Long cityId;
    private String cityName;
    private Long countyId;
    private String countyName;
    private String batchType;

	public University(){
	}
    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return this.code;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setSubjection(String value) {
        this.subjection = value;
    }

    public String getSubjection() {
        return this.subjection;
    }
    public void setEducationLevel(String value) {
        this.educationLevel = value;
    }

    public String getEducationLevel() {
        return this.educationLevel;
    }
    public void setProperty(String value) {
        this.property = value;
    }

    public String getProperty() {
        return this.property;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }
    public void setUrl(String value) {
        this.url = value;
    }

    public String getUrl() {
        return this.url;
    }
    public void setAddress(String value) {
        this.address = value;
    }

    public String getAddress() {
        return this.address;
    }
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }
    public void setEntranceIntro(String value) {
        this.entranceIntro = value;
    }

    public String getEntranceIntro() {
        return this.entranceIntro;
    }
    public void setUniversityIntro(String value) {
        this.universityIntro = value;
    }

    public String getUniversityIntro() {
        return this.universityIntro;
    }
    public void setProvinceId(Long value) {
        this.provinceId = value;
    }

    public Long getProvinceId() {
        return this.provinceId;
    }
    public void setProvinceName(String value) {
        this.provinceName = value;
    }

    public String getProvinceName() {
        return this.provinceName;
    }
    public void setCityId(Long value) {
        this.cityId = value;
    }

    public Long getCityId() {
        return this.cityId;
    }
    public void setCityName(String value) {
        this.cityName = value;
    }

    public String getCityName() {
        return this.cityName;
    }
    public void setCountyId(Long value) {
        this.countyId = value;
    }

    public Long getCountyId() {
        return this.countyId;
    }
    public void setCountyName(String value) {
        this.countyName = value;
    }

    public String getCountyName() {
        return this.countyName;
    }
    public void setBatchType(String value) {
        this.batchType = value;
    }

    public String getBatchType() {
        return this.batchType;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CreateDate",getCreateDate())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("Code",getCode())
			.append("Name",getName())
			.append("Subjection",getSubjection())
			.append("EducationLevel",getEducationLevel())
			.append("Property",getProperty())
			.append("Type",getType())
			.append("Url",getUrl())
			.append("Address",getAddress())
			.append("ContactPhone",getContactPhone())
			.append("EntranceIntro",getEntranceIntro())
			.append("UniversityIntro",getUniversityIntro())
			.append("ProvinceId",getProvinceId())
			.append("ProvinceName",getProvinceName())
			.append("CityId",getCityId())
			.append("CityName",getCityName())
			.append("CountyId",getCountyId())
			.append("CountyName",getCountyName())
			.append("BatchType",getBatchType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof University == false) return false;
		if(this == obj) return true;
		University other = (University)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

