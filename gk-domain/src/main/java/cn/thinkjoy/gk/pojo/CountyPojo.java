/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  County.java 2015-09-21 16:58:04 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CountyPojo extends BaseDomain{
	private Integer status;
    private Long cityId;
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "CountyPojo{" +
				"status=" + status +
				", cityId=" + cityId +
				", name='" + name + '\'' +
				'}';
	}
}

