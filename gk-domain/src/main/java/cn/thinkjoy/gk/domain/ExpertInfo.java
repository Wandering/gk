/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertInfo.java 2016-10-19 15:14:27 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExpertInfo extends BaseDomain {
    private String expertName;
    private String expertPhotoUrl;
    private String expertIntro;
    private String expertPhone;
    private Integer serverCases;
    private Long areaId;
    private String expertDomain;
    private String expertProfile;
    private String bestExpert;
    private String isChecked;
    private Long createDate;

	public ExpertInfo(){
	}
    public void setExpertName(String value) {
        this.expertName = value;
    }

    public String getExpertName() {
        return this.expertName;
    }
    public void setExpertPhotoUrl(String value) {
        this.expertPhotoUrl = value;
    }

    public String getExpertPhotoUrl() {
        return this.expertPhotoUrl;
    }
    public void setExpertIntro(String value) {
        this.expertIntro = value;
    }

    public String getExpertIntro() {
        return this.expertIntro;
    }
    public void setExpertPhone(String value) {
        this.expertPhone = value;
    }

    public String getExpertPhone() {
        return this.expertPhone;
    }
    public void setServerCases(Integer value) {
        this.serverCases = value;
    }

    public Integer getServerCases() {
        return this.serverCases;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public void setExpertDomain(String value) {
        this.expertDomain = value;
    }

    public String getExpertDomain() {
        return this.expertDomain;
    }
    public void setExpertProfile(String value) {
        this.expertProfile = value;
    }

    public String getExpertProfile() {
        return this.expertProfile;
    }
    public void setBestExpert(String value) {
        this.bestExpert = value;
    }

    public String getBestExpert() {
        return this.bestExpert;
    }
    public void setIsChecked(String value) {
        this.isChecked = value;
    }

    public String getIsChecked() {
        return this.isChecked;
    }
    public void setCreateDate(Long value) {
        this.createDate = value;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExpertName",getExpertName())
			.append("ExpertPhotoUrl",getExpertPhotoUrl())
			.append("ExpertIntro",getExpertIntro())
			.append("ExpertPhone",getExpertPhone())
			.append("ServerCases",getServerCases())
			.append("AreaId",getAreaId())
			.append("ExpertDomain",getExpertDomain())
			.append("ExpertProfile",getExpertProfile())
			.append("BestExpert",getBestExpert())
			.append("IsChecked",getIsChecked())
			.append("CreateDate",getCreateDate())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertInfo == false) return false;
		if(this == obj) return true;
		ExpertInfo other = (ExpertInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

