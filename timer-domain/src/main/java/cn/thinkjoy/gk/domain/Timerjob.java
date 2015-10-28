/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  Timerjob.java 2015-10-28 11:39:07 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Timerjob extends BaseDomain<Long>{
    /**  */
    private Long createTime;
    /**  */
    private Long updateTime;
    /** 名称 */
    private String name;
    /** 分组 */
    private String group;
    /** 表达式 */
    private String cronExpression;
    /** 描述 */
    private String desc;
    /** 是否同步 */
    private Integer async;
    /** 类名 */
    private String className;
    /** 执行时间 */
    private Long executeTime;
    /** 执行次数 */
    private Long executeNum;
    /** 错误消息 */
    private String errorMessage;

	public Timerjob(){
	}
    public void setCreateTime(Long value) {
        this.createTime = value;
    }

    public Long getCreateTime() {
        return this.createTime;
    }
    public void setUpdateTime(Long value) {
        this.updateTime = value;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setGroup(String value) {
        this.group = value;
    }

    public String getGroup() {
        return this.group;
    }
    public void setCronExpression(String value) {
        this.cronExpression = value;
    }

    public String getCronExpression() {
        return this.cronExpression;
    }
    public void setDesc(String value) {
        this.desc = value;
    }

    public String getDesc() {
        return this.desc;
    }
    public void setAsync(Integer value) {
        this.async = value;
    }

    public Integer getAsync() {
        return this.async;
    }
    public void setClassName(String value) {
        this.className = value;
    }

    public String getClassName() {
        return this.className;
    }
    public void setExecuteTime(Long value) {
        this.executeTime = value;
    }

    public Long getExecuteTime() {
        return this.executeTime;
    }
    public void setExecuteNum(Long value) {
        this.executeNum = value;
    }

    public Long getExecuteNum() {
        return this.executeNum;
    }
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("Name",getName())
			.append("Group",getGroup())
			.append("CronExpression",getCronExpression())
			.append("Desc",getDesc())
			.append("Async",getAsync())
			.append("ClassName",getClassName())
			.append("ExecuteTime",getExecuteTime())
			.append("ExecuteNum",getExecuteNum())
			.append("ErrorMessage",getErrorMessage())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Timerjob == false) return false;
		if(this == obj) return true;
		Timerjob other = (Timerjob)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

