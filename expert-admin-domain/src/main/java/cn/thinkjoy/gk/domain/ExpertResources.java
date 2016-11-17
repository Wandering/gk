/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertResources.java 2016-11-17 17:25:48 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ExpertResources extends BaseDomain{
    private String resName;
    private Integer parentId;
    private String resUrl;
    private Date createDate;
    private String iconUrl;

	public ExpertResources(){
	}
    public void setResName(String value) {
        this.resName = value;
    }

    public String getResName() {
        return this.resName;
    }
    public void setParentId(Integer value) {
        this.parentId = value;
    }

    public Integer getParentId() {
        return this.parentId;
    }
    public void setResUrl(String value) {
        this.resUrl = value;
    }

    public String getResUrl() {
        return this.resUrl;
    }

    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    public void setIconUrl(String value) {
        this.iconUrl = value;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ResName",getResName())
			.append("ParentId",getParentId())
			.append("ResUrl",getResUrl())
			.append("CreateDate",getCreateDate())
			.append("IconUrl",getIconUrl())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertResources == false) return false;
		if(this == obj) return true;
		ExpertResources other = (ExpertResources)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

