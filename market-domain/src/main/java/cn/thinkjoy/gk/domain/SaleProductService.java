/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  SaleProductService.java 2016-12-20 15:31:13 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SaleProductService extends BaseDomain<Integer>{
    private Integer productType;
    private Integer parentId;
    private String serviceIntro;
    private Long areaId;
    private Integer sort;

	public SaleProductService(){
	}
    public void setProductType(Integer value) {
        this.productType = value;
    }

    public Integer getProductType() {
        return this.productType;
    }
    public void setParentId(Integer value) {
        this.parentId = value;
    }

    public Integer getParentId() {
        return this.parentId;
    }
    public void setServiceIntro(String value) {
        this.serviceIntro = value;
    }

    public String getServiceIntro() {
        return this.serviceIntro;
    }
    public void setAreaId(Long value) {
        this.areaId = value;
    }

    public Long getAreaId() {
        return this.areaId;
    }
    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductType",getProductType())
			.append("ParentId",getParentId())
			.append("ServiceIntro",getServiceIntro())
			.append("AreaId",getAreaId())
			.append("Sort",getSort())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof SaleProductService == false) return false;
		if(this == obj) return true;
		SaleProductService other = (SaleProductService)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

