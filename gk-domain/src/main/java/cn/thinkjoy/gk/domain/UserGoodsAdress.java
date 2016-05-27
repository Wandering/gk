/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk
 * $Id:  UserGoodsAdress.java 2016-05-24 11:14:24 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserGoodsAdress extends BaseDomain<Long>{
    /**  */
    private Long userId;
    /**  */
    private String receivingAddress;
    /**  */
    private String contactPhone;
    /**  */
    private String contactName;
    /**  */
    private Long updateDate;

    private Long createDate;

    private String status;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private String postCode;

	public UserGoodsAdress(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setReceivingAddress(String value) {
        this.receivingAddress = value;
    }

    public String getReceivingAddress() {
        return this.receivingAddress;
    }
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }
    public void setContactName(String value) {
        this.contactName = value;
    }

    public String getContactName() {
        return this.contactName;
    }

    public Long getUpdateDate() {
        return this.updateDate;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("ReceivingAddress",getReceivingAddress())
			.append("ContactPhone",getContactPhone())
			.append("ContactName",getContactName())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserGoodsAdress == false) return false;
		if(this == obj) return true;
		UserGoodsAdress other = (UserGoodsAdress)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

