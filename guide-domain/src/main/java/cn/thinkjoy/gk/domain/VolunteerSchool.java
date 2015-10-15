/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  VolunteerSchool.java 2015-10-14 17:12:29 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class VolunteerSchool extends CreateBaseDomain{
    /** 分类id */
    private Long categoryId;
    /** 标题 */
    private String title;
    /** 摘要 */
    private String summary;
    /** 富文本内容 */
    private String content;
    /** 排序 */
    private Integer sort;
    /** 点击量 */
    private Integer hits;
    /** 区域Id */
    private Long areaId;

    public VolunteerSchool(){
    }
    public void setCategoryId(Long value) {
        this.categoryId = value;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }
    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }
    public void setSummary(String value) {
        this.summary = value;
    }

    public String getSummary() {
        return this.summary;
    }
    public void setContent(String value) {
        this.content = value;
    }

    public String getContent() {
        return this.content;
    }
    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }
    public void setHits(Integer value) {
        this.hits = value;
    }

    public Integer getHits() {
        return this.hits;
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
                .append("CategoryId", getCategoryId())
                .append("Title",getTitle())
                .append("Summary",getSummary())
                .append("Content",getContent())
                .append("Sort",getSort())
                .append("Status",getStatus())
                .append("Creator",getCreator())
                .append("CreateDate",getCreateDate())
                .append("LastModifier",getLastModifier())
                .append("LastModDate",getLastModDate())
                .append("Hits",getHits())
                .append("AreaId",getAreaId())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof VolunteerSchool == false) return false;
        if(this == obj) return true;
        VolunteerSchool other = (VolunteerSchool)obj;
        return new EqualsBuilder()
                .append(getId(),other.getId())
                .isEquals();
    }
}

