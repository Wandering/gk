/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  Appointment.java 2015-09-23 10:34:36 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AppointmentPojo extends BaseDomain {
    private String title;
    private Long startDate;
    private Long endDate;
    private String content;
    private String name;
    private String mobile;
    private String qq;
    private Long userId;
    private Long createDate;

	public AppointmentPojo(){
	}
    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }
    public void setStartDate(Long value) {
        this.startDate = value;
    }

    public Long getStartDate() {
        return this.startDate;
    }
    public void setEndDate(Long value) {
        this.endDate = value;
    }

    public Long getEndDate() {
        return this.endDate;
    }
    public void setContent(String value) {
        this.content = value;
    }

    public String getContent() {
        return this.content;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setMobile(String value) {
        this.mobile = value;
    }

    public String getMobile() {
        return this.mobile;
    }
    public void setQq(String value) {
        this.qq = value;
    }

    public String getQq() {
        return this.qq;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "AppointmentPojo{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", qq='" + qq + '\'' +
                ", userId=" + userId +
                ", createDate=" + createDate +
                '}';
    }

    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AppointmentPojo == false) return false;
		if(this == obj) return true;
		AppointmentPojo other = (AppointmentPojo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

