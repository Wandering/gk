/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  Agent.java 2015-09-23 10:34:35 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

public class AgentPojo  extends BaseDomain {
    private String name;
    private String telphone;
    private String address;

	public AgentPojo(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setTelphone(String value) {
        this.telphone = value;
    }

    public String getTelphone() {
        return this.telphone;
    }
    public void setAddress(String value) {
        this.address = value;
    }

    public String getAddress() {
        return this.address;
    }

	@Override
	public String toString() {
		return "AgentPojo{" +
				"name='" + name + '\'' +
				", telphone='" + telphone + '\'' +
				", address='" + address + '\'' +
				'}';
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AgentPojo == false) return false;
		if(this == obj) return true;
		AgentPojo other = (AgentPojo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

