/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  ExaminationPaper.java 2015-10-15 11:37:03 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class ExaminationPaper extends CreateBaseDomain<Long>{
    /** 年份 */
    private String years;
    /** 排序 */
    private Integer sort;
    /** 备考冲刺分类ＩＤ */
    private Long mbeikaochongciTypeId;
    /** 备考冲刺分类名称 */
    private String mbeikaochongciType;
    /** 科目ＩＤ */
    private Long subjectId;
    /** 试卷名称 */
    private String paperName;
    /** 信息封面 */
    private String frontCover;
    /** 简介 */
    private String subContent;
    /** 价格 */
    private Double price;
    /** 是否进入 */
    private Integer isAccept;
    /** 站内资源 */
    private String resources;
    /** 资源扩展名 */
    private String resourcesExt;
    /** 资源大小 */
    private String resourcesFilesize;
    /** 下载次数 */
    private Integer downloadsAutomatic;
    /** 下载次数 */
    private Integer downloadsManual;
    /** 区域Id */
    private Long areaId;

    public ExaminationPaper(){
    }
    public void setYears(String value) {
        this.years = value;
    }

    public String getYears() {
        return this.years;
    }
    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }
    public void setMbeikaochongciTypeId(Long value) {
        this.mbeikaochongciTypeId = value;
    }

    public Long getMbeikaochongciTypeId() {
        return this.mbeikaochongciTypeId;
    }
    public void setMbeikaochongciType(String value) {
        this.mbeikaochongciType = value;
    }

    public String getMbeikaochongciType() {
        return this.mbeikaochongciType;
    }
    public void setSubjectId(Long value) {
        this.subjectId = value;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }
    public void setPaperName(String value) {
        this.paperName = value;
    }

    public String getPaperName() {
        return this.paperName;
    }
    public void setFrontCover(String value) {
        this.frontCover = value;
    }

    public String getFrontCover() {
        return this.frontCover;
    }
    public void setSubContent(String value) {
        this.subContent = value;
    }

    public String getSubContent() {
        return this.subContent;
    }
    public void setPrice(Double value) {
        this.price = value;
    }

    public Double getPrice() {
        return this.price;
    }
    public void setIsAccept(Integer value) {
        this.isAccept = value;
    }

    public Integer getIsAccept() {
        return this.isAccept;
    }
    public void setResources(String value) {
        this.resources = value;
    }

    public String getResources() {
        return this.resources;
    }
    public void setResourcesExt(String value) {
        this.resourcesExt = value;
    }

    public String getResourcesExt() {
        return this.resourcesExt;
    }
    public void setResourcesFilesize(String value) {
        this.resourcesFilesize = value;
    }

    public String getResourcesFilesize() {
        return this.resourcesFilesize;
    }
    public void setDownloadsAutomatic(Integer value) {
        this.downloadsAutomatic = value;
    }

    public Integer getDownloadsAutomatic() {
        return this.downloadsAutomatic;
    }
    public void setDownloadsManual(Integer value) {
        this.downloadsManual = value;
    }

    public Integer getDownloadsManual() {
        return this.downloadsManual;
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
                .append("Years", getYears())
                .append("Sort",getSort())
                .append("MbeikaochongciTypeId",getMbeikaochongciTypeId())
                .append("MbeikaochongciType",getMbeikaochongciType())
                .append("SubjectId",getSubjectId())
                .append("PaperName",getPaperName())
                .append("FrontCover",getFrontCover())
                .append("SubContent",getSubContent())
                .append("Price",getPrice())
                .append("IsAccept",getIsAccept())
                .append("Resources",getResources())
                .append("ResourcesExt",getResourcesExt())
                .append("ResourcesFilesize",getResourcesFilesize())
                .append("DownloadsAutomatic",getDownloadsAutomatic())
                .append("DownloadsManual",getDownloadsManual())
                .append("CreateDate",getCreateDate())
                .append("Status",getStatus())
                .append("Creator",getCreator())
                .append("LastModDate",getLastModDate())
                .append("LastModifier",getLastModifier())
                .append("AreaId",getAreaId())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof ExaminationPaper == false) return false;
        if(this == obj) return true;
        ExaminationPaper other = (ExaminationPaper)obj;
        return new EqualsBuilder()
                .append(getId(),other.getId())
                .isEquals();
    }
}

