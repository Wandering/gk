/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  Count.java 2015-12-25 16:17:39 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Count extends BaseDomain<Long>{
    /** 统计的项目 */
    private Long projectId;
    /** 统计总数 */
    private Integer number;
    /** 1为院校 */
    private String type;

	public Count(){
	}
    public void setProjectId(Long value) {
        this.projectId = value;
    }

    public Long getProjectId() {
        return this.projectId;
    }
    public void setNumber(Integer value) {
        this.number = value;
    }

    public Integer getNumber() {
        return this.number;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProjectId",getProjectId())
			.append("Number",getNumber())
			.append("Type",getType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Count == false) return false;
		if(this == obj) return true;
		Count other = (Count)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

