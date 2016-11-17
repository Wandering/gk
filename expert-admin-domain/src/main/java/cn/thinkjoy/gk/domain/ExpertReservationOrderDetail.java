/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  ExpertReservationOrderDetail.java 2016-11-02 15:31:19 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExpertReservationOrderDetail extends BaseDomain{
    private String cardId;
    private String serviceItem;
    private String userId;
    private String expertId;
    private String channel;
    private String channelNum;
    private String contactPerson;
    private String contactPhone;
    private String expectTime;
    private String endTime;
    private String questionDesc;
    private String agreeMark;
    private Integer score;
    private String comments;
    private String createDate;
    private Integer serviceCount;
    private Integer status;

	public ExpertReservationOrderDetail(){
	}
    public void setCardId(String value) {
        this.cardId = value;
    }

    public String getCardId() {
        return this.cardId;
    }
    public void setServiceItem(String value) {
        this.serviceItem = value;
    }

    public String getServiceItem() {
        return this.serviceItem;
    }
    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUserId() {
        return this.userId;
    }
    public void setExpertId(String value) {
        this.expertId = value;
    }

    public String getExpertId() {
        return this.expertId;
    }
    public void setChannel(String value) {
        this.channel = value;
    }

    public String getChannel() {
        return this.channel;
    }
    public void setChannelNum(String value) {
        this.channelNum = value;
    }

    public String getChannelNum() {
        return this.channelNum;
    }
    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }
    public void setExpectTime(String value) {
        this.expectTime = value;
    }

    public String getExpectTime() {
        return this.expectTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setQuestionDesc(String value) {
        this.questionDesc = value;
    }

    public String getQuestionDesc() {
        return this.questionDesc;
    }
    public void setAgreeMark(String value) {
        this.agreeMark = value;
    }

    public String getAgreeMark() {
        return this.agreeMark;
    }
    public void setScore(Integer value) {
        this.score = value;
    }

    public Integer getScore() {
        return this.score;
    }
    public void setComments(String value) {
        this.comments = value;
    }

    public String getComments() {
        return this.comments;
    }
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    public String getCreateDate() {
        return this.createDate;
    }
    public void setServiceCount(Integer value) {
        this.serviceCount = value;
    }

    public Integer getServiceCount() {
        return this.serviceCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CardId",getCardId())
			.append("ServiceItem",getServiceItem())
			.append("UserId",getUserId())
			.append("ExpertId",getExpertId())
			.append("Channel",getChannel())
			.append("ChannelNum",getChannelNum())
			.append("ContactPerson",getContactPerson())
			.append("ContactPhone",getContactPhone())
			.append("ExpectTime",getExpectTime())
			.append("EndTime",getEndTime())
			.append("QuestionDesc",getQuestionDesc())
			.append("AgreeMark",getAgreeMark())
			.append("Status",getStatus())
			.append("Score",getScore())
			.append("Comments",getComments())
			.append("CreateDate",getCreateDate())
			.append("ServiceCount",getServiceCount())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ExpertReservationOrderDetail == false) return false;
		if(this == obj) return true;
		ExpertReservationOrderDetail other = (ExpertReservationOrderDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

