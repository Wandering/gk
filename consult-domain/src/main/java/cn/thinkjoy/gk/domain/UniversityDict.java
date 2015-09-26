/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  UniversityDict.java 2015-09-26 11:19:42 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class UniversityDict extends CreateBaseDomain{
    private String name;
    private String type;
    private Integer dictId;
    private String note;

	public UniversityDict(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }
    public void setDictId(Integer value) {
        this.dictId = value;
    }

    public Integer getDictId() {
        return this.dictId;
    }
    public void setNote(String value) {
        this.note = value;
    }

    public String getNote() {
        return this.note;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CreateDate",getCreateDate())
			.append("Status",getStatus())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("Name",getName())
			.append("Type",getType())
			.append("DictId",getDictId())
			.append("Note",getNote())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UniversityDict == false) return false;
		if(this == obj) return true;
		UniversityDict other = (UniversityDict)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

