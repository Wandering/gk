/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: before
 * $Id:  VideoCourse.java 2015-09-23 10:30:50 $
 */



package cn.thinkjoy.gk.before.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class VideoCourse extends CreateBaseDomain{
    private Long classifyId;
    private Long teacherId;
    private Long subjectId;
    private Integer managerId;
    private String teacher;
    private String title;
    private String content;
    private String frontCover;
    private String subcontent;
    private Integer hit;
    private String years;
    private String beikaochongciType;
    private String courseSort;
    private String frontcover1;
    private Integer isAccept;

	public VideoCourse(){
	}
    public void setClassifyId(Long value) {
        this.classifyId = value;
    }

    public Long getClassifyId() {
        return this.classifyId;
    }
    public void setTeacherId(Long value) {
        this.teacherId = value;
    }

    public Long getTeacherId() {
        return this.teacherId;
    }
    public void setSubjectId(Long value) {
        this.subjectId = value;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }
    public void setManagerId(Integer value) {
        this.managerId = value;
    }

    public Integer getManagerId() {
        return this.managerId;
    }
    public void setTeacher(String value) {
        this.teacher = value;
    }

    public String getTeacher() {
        return this.teacher;
    }
    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }
    public void setContent(String value) {
        this.content = value;
    }

    public String getContent() {
        return this.content;
    }
    public void setFrontCover(String value) {
        this.frontCover = value;
    }

    public String getFrontCover() {
        return this.frontCover;
    }
    public void setSubcontent(String value) {
        this.subcontent = value;
    }

    public String getSubcontent() {
        return this.subcontent;
    }
    public void setHit(Integer value) {
        this.hit = value;
    }

    public Integer getHit() {
        return this.hit;
    }
    public void setYears(String value) {
        this.years = value;
    }

    public String getYears() {
        return this.years;
    }
    public void setBeikaochongciType(String value) {
        this.beikaochongciType = value;
    }

    public String getBeikaochongciType() {
        return this.beikaochongciType;
    }
    public void setCourseSort(String value) {
        this.courseSort = value;
    }

    public String getCourseSort() {
        return this.courseSort;
    }
    public void setFrontcover1(String value) {
        this.frontcover1 = value;
    }

    public String getFrontcover1() {
        return this.frontcover1;
    }
    public void setIsAccept(Integer value) {
        this.isAccept = value;
    }

    public Integer getIsAccept() {
        return this.isAccept;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ClassifyId",getClassifyId())
			.append("TeacherId",getTeacherId())
			.append("SubjectId",getSubjectId())
			.append("ManagerId",getManagerId())
			.append("Teacher",getTeacher())
			.append("Title",getTitle())
			.append("Content",getContent())
			.append("FrontCover",getFrontCover())
			.append("Subcontent",getSubcontent())
			.append("Hit",getHit())
			.append("Years",getYears())
			.append("BeikaochongciType",getBeikaochongciType())
			.append("CourseSort",getCourseSort())
			.append("Frontcover1",getFrontcover1())
			.append("IsAccept",getIsAccept())
			.append("CreateDate",getCreateDate())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof VideoCourse == false) return false;
		if(this == obj) return true;
		VideoCourse other = (VideoCourse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

