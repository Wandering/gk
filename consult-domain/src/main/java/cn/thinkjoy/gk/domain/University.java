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
    private Object property;
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
    /** 院校省码 */
    private String provinceCode;
    /** 位置 */
    private String areaid;
    /** 院校logo链接 */
    private String photoUrl;
    /** 学校排名 */
    private Integer rank;
    /** 选测等级 */
    private String xcRank;

    private int isCollect;

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getXcRank() {
        return xcRank;
    }

    public void setXcRank(String xcRank) {
        this.xcRank = xcRank;
    }

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
    public void setProperty(Object value) {
        this.property = value;
    }

    public Object getProperty() {
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

