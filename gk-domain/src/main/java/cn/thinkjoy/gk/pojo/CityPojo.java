/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  City.java 2015-09-21 16:58:03 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

public class CityPojo extends BaseDomain{
	private Integer status;
    private Long provinceId;
    private String name;
	private List<CountyPojo> countyList;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CountyPojo> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<CountyPojo> countyList) {
		this.countyList = countyList;
	}
}

