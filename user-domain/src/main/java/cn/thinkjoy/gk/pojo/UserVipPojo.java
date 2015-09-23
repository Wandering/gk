/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UserVip.java 2015-09-23 10:34:37 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserVipPojo extends BaseDomain {
    private Long endDate;

	public UserVipPojo(){
	}
    public void setEndDate(Long value) {
        this.endDate = value;
    }

    public Long getEndDate() {
        return this.endDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())

			.append("EndDate",getEndDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserVipPojo == false) return false;
		if(this == obj) return true;
		UserVipPojo other = (UserVipPojo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

