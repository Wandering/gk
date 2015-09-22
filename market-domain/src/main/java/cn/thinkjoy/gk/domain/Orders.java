/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  Orders.java 2015-09-07 10:14:40 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.math.BigDecimal;

public class Orders extends BaseDomain{
    private Integer status;
    private Long createDate;
    private Long lastModDate;
    private Long invalidDate;
    private String orderNo;
    private BigDecimal amount;
    private String detail;
    private Long userId;
    private Integer payStatus;
    private String description;
    private String channel;
    private String phoneNum;
    private String moduleKey;

	public Orders(){
	}
    public void setInvalidDate(Long value) {
        this.invalidDate = value;
    }

    public Long getInvalidDate() {
        return this.invalidDate;
    }
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    public String getOrderNo() {
        return this.orderNo;
    }
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
    public void setDetail(String value) {
        this.detail = value;
    }

    public String getDetail() {
        return this.detail;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setPayStatus(Integer value) {
        this.payStatus = value;
    }

    public Integer getPayStatus() {
        return this.payStatus;
    }
    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }
    public void setChannel(String value) {
        this.channel = value;
    }

    public String getChannel() {
        return this.channel;
    }
    public void setPhoneNum(String value) {
        this.phoneNum = value;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setModuleKey(String value) {
        this.moduleKey = value;
    }

    public String getModuleKey() {
        return this.moduleKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Long lastModDate) {
        this.lastModDate = lastModDate;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Status",getStatus())
			.append("CreateDate",getCreateDate())
			.append("LastModDate",getLastModDate())
			.append("InvalidDate",getInvalidDate())
			.append("OrderNo",getOrderNo())
			.append("Amount",getAmount())
			.append("Detail",getDetail())
			.append("UserId",getUserId())
			.append("PayStatus",getPayStatus())
			.append("Description",getDescription())
			.append("Channel",getChannel())
			.append("PhoneNum",getPhoneNum())
			.append("ModuleKey",getModuleKey())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Orders == false) return false;
		if(this == obj) return true;
		Orders other = (Orders)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

