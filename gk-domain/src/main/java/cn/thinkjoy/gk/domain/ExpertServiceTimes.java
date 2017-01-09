/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertServiceTimes.java 2016-11-05 15:40:34 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ExpertServiceTimes extends BaseDomain{
    private Integer expertDayId;
    private String timeSegment;
    private String isAvailable;

	public ExpertServiceTimes(){
	}
    public void setExpertDayId(Integer value) {
        this.expertDayId = value;
    }

    public Integer getExpertDayId() {
        return this.expertDayId;
    }
    public void setTimeSegment(String value) {
        this.timeSegment = value;
    }

    public String getTimeSegment() {
        return this.timeSegment;
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
			.append("ExpertDayId",getExpertDayId())
			.append("TimeSegment",getTimeSegment())
			.append("IsAvailable",getIsAvailable())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertServiceTimes == false) return false;
		if(this == obj) return true;
		ExpertServiceTimes other = (ExpertServiceTimes)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

