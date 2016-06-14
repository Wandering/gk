/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  Card.java 2015-10-21 11:14:10 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Card extends CreateBaseDomain<Long>{
	/** 有效结束日期 */
	private Long endDate;
	/** 信息标题 */
	private String cardNumber;
	/** 信息标题 */
	private String password;
	/**  激活用户ID*/
	private Long userId;
	/** 区域Id */
	private Long areaId;
	/**
	 * 卡号类型，1为正式，2为演示（贾静静用），3为测试（杨甜用）,4为体验（薛延松用）
	 */
	private String cardType;
	/** 激活日期 */
	private Long activeDate;

	private String productType;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Long activeDate) {
		this.activeDate = activeDate;
	}

	public Card(){
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setEndDate(Long value) {
		this.endDate = value;
	}

	public Long getEndDate() {
		return this.endDate;
	}
	public void setCardNumber(String value) {
		this.cardNumber = value;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}
	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}
	public void setAreaId(Long value) {
		this.areaId = value;
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("Id",getId())
				.append("Status", getStatus())
				.append("Creator",getCreator())
				.append("CreateDate",getCreateDate())
				.append("LastModifier",getLastModifier())
				.append("LastModDate",getLastModDate())
				.append("EndDate",getEndDate())
				.append("CardNumber",getCardNumber())
				.append("Password",getPassword())
				.append("UserId",getUserId())
				.append("AreaId",getAreaId())
				.append("CardType",getCardType())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof Card == false) return false;
		if(this == obj) return true;
		Card other = (Card)obj;
		return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
	}
}

