/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  Card.java 2015-09-23 10:34:36 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class Card extends CreateBaseDomain{
    private Long endDate;
    private String cardNumber;
    private String password;
    private Long userId;

	public Card(){
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModifier",getLastModifier())
			.append("LastModDate",getLastModDate())
			.append("EndDate",getEndDate())
			.append("CardNumber",getCardNumber())
			.append("Password",getPassword())
			.append("UserId",getUserId())
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

