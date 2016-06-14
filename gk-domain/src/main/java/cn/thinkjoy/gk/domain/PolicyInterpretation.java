/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  PolicyInterpretation.java 2016-01-12 18:41:57 $
 */





package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PolicyInterpretation extends CreateBaseDomain<Long>{
    /** 标题 */
    private String title;
    /** 省份id */
    private Long provinceId;
    /** 录取批次id */
    private Long admissionBatchId;
    /** 分类名称 */
    private String categoryName;
    /** 富文本内容 */
    private String content;
    /** 区域Id */
    private Long areaId;
    /** 内容在云盘中的ID */
    private String htmlId;
    /** 内容摘要 */
    private String subContent;

	public PolicyInterpretation(){
	}
    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }
    public void setProvinceId(Long value) {
        this.provinceId = value;
    }

    public Long getProvinceId() {
        return this.provinceId;
    }
    public void setAdmissionBatchId(Long value) {
        this.admissionBatchId = value;
    }

    public Long getAdmissionBatchId() {
        return this.admissionBatchId;
    }
    public void setCategoryName(String value) {
        this.categoryName = value;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
    public void setContent(String value) {
        this.content = value;
    }

    public String getContent() {
        return this.content;
    }
    public void setAreaId(Long value) {
        this.areaId = value;
    }

    public Long getAreaId() {
        return this.areaId;
    }
    public void setHtmlId(String value) {
        this.htmlId = value;
    }

    public String getHtmlId() {
        return this.htmlId;
    }
    public void setSubContent(String value) {
        this.subContent = value;
    }

    public String getSubContent() {
        return this.subContent;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Title",getTitle())
			.append("ProvinceId",getProvinceId())
			.append("AdmissionBatchId",getAdmissionBatchId())
			.append("CategoryName",getCategoryName())
			.append("Content",getContent())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModifier",getLastModifier())
			.append("LastModDate",getLastModDate())
			.append("AreaId",getAreaId())
			.append("HtmlId",getHtmlId())
			.append("SubContent",getSubContent())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PolicyInterpretation == false) return false;
		if(this == obj) return true;
		PolicyInterpretation other = (PolicyInterpretation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

