/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  GkinformationGkhot.java 2015-10-28 09:56:08 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class GkinformationGkhot extends CreateBaseDomain{
    /** 热点标题 */
    private String hotInformation;
    /** 热点摘要 */
    private String informationSubContent;
    /** 热点信息 */
    private String informationContent;
    /** 点击数 */
    private Long hotCount;
    /** 区域Id */
    private Long areaId;

    public GkinformationGkhot(){
    }
    public void setHotInformation(String value) {
        this.hotInformation = value;
    }

    public String getHotInformation() {
        return this.hotInformation;
    }
    public void setInformationSubContent(String value) {
        this.informationSubContent = value;
    }

    public String getInformationSubContent() {
        return this.informationSubContent;
    }
    public void setInformationContent(String value) {
        this.informationContent = value;
    }

    public String getInformationContent() {
        return this.informationContent;
    }
    public void setHotCount(Long value) {
        this.hotCount = value;
    }

    public Long getHotCount() {
        return this.hotCount;
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
                .append("Status",getStatus())
                .append("Creator", getCreator())
                .append("CreateDate", getCreateDate())
                .append("LastModifier", getLastModifier())
                .append("LastModDate", getLastModDate())
                .append("HotInformation", getHotInformation())
                .append("InformationSubContent", getInformationSubContent())
                .append("InformationContent", getInformationContent())
                .append("HotCount", getHotCount())
                .append("AreaId", getAreaId())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof GkinformationGkhot == false) return false;
        if(this == obj) return true;
        GkinformationGkhot other = (GkinformationGkhot)obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

