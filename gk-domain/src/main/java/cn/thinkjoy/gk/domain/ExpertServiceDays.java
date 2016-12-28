/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertServiceDays.java 2016-11-05 15:40:33 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ExpertServiceDays extends BaseDomain{
    private Integer expertId;
    private Date serviceDay;
    private String isAvailable;

	public ExpertServiceDays(){
	}
    public void setExpertId(Integer value) {
        this.expertId = value;
    }

    public Integer getExpertId() {
        return this.expertId;
    }

    public void setServiceDay(Date value) {
        this.serviceDay = value;
    }

    public Date getServiceDay() {
        return this.serviceDay;
    }
    public void setIsAvailable(String value) {
        this.isAvailable = value;
    }

    public String getIsAvailable() {
        return this.isAvailable;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExpertId",getExpertId())
			.append("ServiceDay",getServiceDay())
			.append("IsAvailable",getIsAvailable())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertServiceDays == false) return false;
		if(this == obj) return true;
		ExpertServiceDays other = (ExpertServiceDays)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

