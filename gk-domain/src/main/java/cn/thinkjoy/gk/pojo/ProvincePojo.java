/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  Province.java 2015-09-21 16:58:04 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

public class ProvincePojo extends BaseDomain{

	private Integer status;
    private String name;
	private List<CityPojo> cityList;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityPojo> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityPojo> cityList) {
		this.cityList = cityList;
	}
}

