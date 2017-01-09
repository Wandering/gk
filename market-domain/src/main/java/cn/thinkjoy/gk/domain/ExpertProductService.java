/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertProductService.java 2016-11-03 11:33:42 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ExpertProductService extends BaseDomain{
    private Integer productId;
    private Integer areaId;
    private String serviceId;
    private String serviceType;
    private String serviceTimes;
    private String serviceFrequency;
    private String serviceIntroduce;
    private String serviceGrade;
    private String serviceIcon;
    private String serviceCount;

    public String getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(String serviceCount) {
        this.serviceCount = serviceCount;
    }

    public ExpertProductService(){
	}
    public void setProductId(Integer value) {
        this.productId = value;
    }

    public Integer getProductId() {
        return this.productId;
    }
    public void setAreaId(Integer value) {
        this.areaId = value;
    }

    public Integer getAreaId() {
        return this.areaId;
    }
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    public String getServiceId() {
        return this.serviceId;
    }
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    public String getServiceType() {
        return this.serviceType;
    }
    public void setServiceTimes(String value) {
        this.serviceTimes = value;
    }

    public String getServiceTimes() {
        return this.serviceTimes;
    }
    public void setServiceFrequency(String value) {
        this.serviceFrequency = value;
    }

    public String getServiceFrequency() {
        return this.serviceFrequency;
    }
    public void setServiceIntroduce(String value) {
        this.serviceIntroduce = value;
    }

    public String getServiceIntroduce() {
        return this.serviceIntroduce;
    }
    public void setServiceGrade(String value) {
        this.serviceGrade = value;
    }

    public String getServiceGrade() {
        return this.serviceGrade;
    }
    public void setServiceIcon(String value) {
        this.serviceIcon = value;
    }

    public String getServiceIcon() {
        return this.serviceIcon;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductId",getProductId())
			.append("AreaId",getAreaId())
			.append("ServiceId",getServiceId())
			.append("ServiceType",getServiceType())
			.append("ServiceTimes",getServiceTimes())
			.append("ServiceFrequency",getServiceFrequency())
			.append("ServiceIntroduce",getServiceIntroduce())
			.append("ServiceGrade",getServiceGrade())
			.append("ServiceIcon",getServiceIcon())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertProductService == false) return false;
		if(this == obj) return true;
		ExpertProductService other = (ExpertProductService)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

