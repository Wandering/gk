/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  Province.java 2015-09-26 09:20:33 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Province extends BaseDomain {
    private String name;
	private Integer status;

	public Province(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Province == false) return false;
		if(this == obj) return true;
		Province other = (Province)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

