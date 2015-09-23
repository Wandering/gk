/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  Question.java 2015-07-13 09:45:17 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Question extends BaseDomain {
    private Integer status;
    private Long createDate;
    private Long lastModDate;
    private String question;
    private Integer isOpen;
    private Long userId;
    private Long expertId;
    private Integer isAnswer;
    private String tags;
    private Integer freeStatus;
    private String disableExpertId;
    private Integer disableStatus;

	public Question(){
	}
    public void setQuestion(String value) {
        this.question = value;
    }

    public String getQuestion() {
        return this.question;
    }
    public void setIsOpen(Integer value) {
        this.isOpen = value;
    }

    public Integer getIsOpen() {
        return this.isOpen;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setExpertId(Long value) {
        this.expertId = value;
    }

    public Long getExpertId() {
        return this.expertId;
    }
    public void setIsAnswer(Integer value) {
        this.isAnswer = value;
    }

    public Integer getIsAnswer() {
        return this.isAnswer;
    }
    public void setTags(String value) {
        this.tags = value;
    }

    public String getTags() {
        return this.tags;
    }
    public void setFreeStatus(Integer value) {
        this.freeStatus = value;
    }

    public Integer getFreeStatus() {
        return this.freeStatus;
    }
    public void setDisableExpertId(String value) {
        this.disableExpertId = value;
    }

    public String getDisableExpertId() {
        return this.disableExpertId;
    }
    public void setDisableStatus(Integer value) {
        this.disableStatus = value;
    }

    public Integer getDisableStatus() {
        return this.disableStatus;
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
			.append("Id", getId())
			.append("Status",getStatus())
			.append("CreateDate", getCreateDate())
			.append("LastModDate", getLastModDate())
			.append("Question",getQuestion())
			.append("IsOpen",getIsOpen())
			.append("UserId",getUserId())
			.append("ExpertId",getExpertId())
			.append("IsAnswer",getIsAnswer())
			.append("Tags",getTags())
			.append("FreeStatus",getFreeStatus())
			.append("DisableExpertId",getDisableExpertId())
			.append("DisableStatus",getDisableStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Question == false) return false;
		if(this == obj) return true;
		Question other = (Question)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

