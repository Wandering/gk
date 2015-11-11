/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: timer
 * $Id:  MajoredRank.java 2015-11-10 17:58:49 $
 */



package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class MajoredRank extends BaseDomain{
    private String universityName;
    private String majorName;
    private String mainMajor;
    private Integer salaryRank;
    private Integer employedRank;
    private String majorBatch;

	public MajoredRank(){
	}
    public void setUniversityName(String value) {
        this.universityName = value;
    }

    public String getUniversityName() {
        return this.universityName;
    }
    public void setMajorName(String value) {
        this.majorName = value;
    }

    public String getMajorName() {
        return this.majorName;
    }
    public void setMainMajor(String value) {
        this.mainMajor = value;
    }

    public String getMainMajor() {
        return this.mainMajor;
    }
    public void setSalaryRank(Integer value) {
        this.salaryRank = value;
    }

    public Integer getSalaryRank() {
        return this.salaryRank;
    }
    public void setEmployedRank(Integer value) {
        this.employedRank = value;
    }

    public Integer getEmployedRank() {
        return this.employedRank;
    }
    public void setMajorBatch(String value) {
        this.majorBatch = value;
    }

    public String getMajorBatch() {
        return this.majorBatch;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UniversityName",getUniversityName())
			.append("MajorName",getMajorName())
			.append("MainMajor",getMainMajor())
			.append("SalaryRank",getSalaryRank())
			.append("EmployedRank",getEmployedRank())
			.append("MajorBatch",getMajorBatch())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MajoredRank == false) return false;
		if(this == obj) return true;
		MajoredRank other = (MajoredRank)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

