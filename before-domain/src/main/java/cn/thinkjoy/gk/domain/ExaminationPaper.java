/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: before
 * $Id:  ExaminationPaper.java 2015-09-23 10:30:48 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class ExaminationPaper extends CreateBaseDomain{
    private String years;
    private Integer sort;
    private Long mbeikaochongciTypeId;
    private String mbeikaochongciType;
    private Long subjectId;
    private String paperName;
    private String frontCover;
    private String subContent;
    private Double price;
    private Integer isAccept;
    private String resources;
    private String resourcesExt;
    private String resourcesFilesize;
    private Integer downloadsAutomatic;
    private Integer downloadsManual;

	public ExaminationPaper(){
	}
    public void setYears(String value) {
        this.years = value;
    }

    public String getYears() {
        return this.years;
    }
    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }
    public void setMbeikaochongciTypeId(Long value) {
        this.mbeikaochongciTypeId = value;
    }

    public Long getMbeikaochongciTypeId() {
        return this.mbeikaochongciTypeId;
    }
    public void setMbeikaochongciType(String value) {
        this.mbeikaochongciType = value;
    }

    public String getMbeikaochongciType() {
        return this.mbeikaochongciType;
    }
    public void setSubjectId(Long value) {
        this.subjectId = value;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }
    public void setPaperName(String value) {
        this.paperName = value;
    }

    public String getPaperName() {
        return this.paperName;
    }
    public void setFrontCover(String value) {
        this.frontCover = value;
    }

    public String getFrontCover() {
        return this.frontCover;
    }
    public void setSubContent(String value) {
        this.subContent = value;
    }

    public String getSubContent() {
        return this.subContent;
    }
    public void setPrice(Double value) {
        this.price = value;
    }

    public Double getPrice() {
        return this.price;
    }
    public void setIsAccept(Integer value) {
        this.isAccept = value;
    }

    public Integer getIsAccept() {
        return this.isAccept;
    }
    public void setResources(String value) {
        this.resources = value;
    }

    public String getResources() {
        return this.resources;
    }
    public void setResourcesExt(String value) {
        this.resourcesExt = value;
    }

    public String getResourcesExt() {
        return this.resourcesExt;
    }
    public void setResourcesFilesize(String value) {
        this.resourcesFilesize = value;
    }

    public String getResourcesFilesize() {
        return this.resourcesFilesize;
    }
    public void setDownloadsAutomatic(Integer value) {
        this.downloadsAutomatic = value;
    }

    public Integer getDownloadsAutomatic() {
        return this.downloadsAutomatic;
    }
    public void setDownloadsManual(Integer value) {
        this.downloadsManual = value;
    }

    public Integer getDownloadsManual() {
        return this.downloadsManual;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Years",getYears())
			.append("Sort",getSort())
			.append("MbeikaochongciTypeId",getMbeikaochongciTypeId())
			.append("MbeikaochongciType",getMbeikaochongciType())
			.append("SubjectId",getSubjectId())
			.append("PaperName",getPaperName())
			.append("FrontCover",getFrontCover())
			.append("SubContent",getSubContent())
			.append("Price",getPrice())
			.append("IsAccept",getIsAccept())
			.append("Resources",getResources())
			.append("ResourcesExt",getResourcesExt())
			.append("ResourcesFilesize",getResourcesFilesize())
			.append("DownloadsAutomatic",getDownloadsAutomatic())
			.append("DownloadsManual",getDownloadsManual())
			.append("CreateDate",getCreateDate())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExaminationPaper == false) return false;
		if(this == obj) return true;
		ExaminationPaper other = (ExaminationPaper)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

