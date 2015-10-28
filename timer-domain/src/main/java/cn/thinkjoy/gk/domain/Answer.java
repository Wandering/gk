/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  Answer.java 2015-07-13 09:45:16 $
 */



package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class Answer extends BaseDomain{

    private Long id;
    //任务名称
    private String name;
    //任务所属组的名称
    private String group;
    //定时任务运行时间表达式
    private String cronExpression;
    //任务描述
    private String details;
    //同步异步
    private Integer async;
    //执行类名
    private String className;
    //任务状态
    private Integer status;
    //执行时间
    private Date execute_time;
    //执行次数
    private Integer execute_num = 0;
    //错误信息
    private String error_message;

    private Date create_time;

    private Date update_time;

	public Answer(){
	}
    public void setQuestionId(Long value) {
        this.questionId = value;
    }

    public Long getQuestionId() {
        return this.questionId;
    }
    public void setExpertId(Long value) {
        this.expertId = value;
    }

    public Long getExpertId() {
        return this.expertId;
    }
    public void setAnswer(String value) {
        this.answer = value;
    }

    public String getAnswer() {
        return this.answer;
    }
    public void setAnswerHtml(String value) {
        this.answerHtml = value;
    }

    public String getAnswerHtml() {
        return this.answerHtml;
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
			.append("QuestionId",getQuestionId())
			.append("ExpertId",getExpertId())
			.append("Answer",getAnswer())
			.append("AnswerHtml",getAnswerHtml())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Answer == false) return false;
		if(this == obj) return true;
		Answer other = (Answer)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

