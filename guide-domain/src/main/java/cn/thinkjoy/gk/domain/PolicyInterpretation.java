/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  PolicyInterpretation.java 2015-10-15 10:01:46 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class PolicyInterpretation extends CreateBaseDomain{
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

    public PolicyInterpretation(){
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id",getId())
                .append("ProvinceId", getProvinceId())
                .append("AdmissionBatchId",getAdmissionBatchId())
                .append("CategoryName",getCategoryName())
                .append("Content",getContent())
                .append("Status",getStatus())
                .append("Creator",getCreator())
                .append("CreateDate",getCreateDate())
                .append("LastModifier",getLastModifier())
                .append("LastModDate",getLastModDate())
                .append("AreaId",getAreaId())
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

