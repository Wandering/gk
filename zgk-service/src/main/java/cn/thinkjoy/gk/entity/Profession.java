

package cn.thinkjoy.gk.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Profession extends CreateBaseDomain<Long>{
    /** 目录路径 */
    private String professionType;
    private String professionSubType;
    /** 职业名称 */
    private String professionName;
    /** 职业名称 */
    private String professionShort;
    /** 职业简介 */
    private String professionDescription;
    /** 热门度 */
    private Integer hotDegree;
    /** 热门度 */
    private Integer salaryRank;
    /** 是否删除，0为不删除，1为删除。 */
    private Boolean idDelete;

	public Profession(){
	}

    public String getProfessionType() {
        return professionType;
    }

    public void setProfessionType(String professionType) {
        this.professionType = professionType;
    }

    public String getProfessionSubType() {
        return professionSubType;
    }

    public void setProfessionSubType(String professionSubType) {
        this.professionSubType = professionSubType;
    }

    public void setProfessionName(String value) {
        this.professionName = value;
    }

    public String getProfessionName() {
        return this.professionName;
    }
    public void setProfessionDescription(String value) {
        this.professionDescription = value;
    }

    public String getProfessionDescription() {
        return this.professionDescription;
    }
    public void setHotDegree(Integer value) {
        this.hotDegree = value;
    }

    public Integer getHotDegree() {
        return this.hotDegree;
    }

    public Integer getSalaryRank() {
        return salaryRank;
    }

    public void setSalaryRank(Integer salaryRank) {
        this.salaryRank = salaryRank;
    }

    public void setIdDelete(Boolean value) {
        this.idDelete = value;
    }

    public Boolean getIdDelete() {
        return this.idDelete;
    }

    public String getProfessionShort() {
        return professionShort;
    }

    public void setProfessionShort(String professionShort) {
        this.professionShort = professionShort;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProfessionType", getProfessionType())
			.append("ProfessionSubType",getProfessionSubType())
			.append("ProfessionName",getProfessionName())
			.append("ProfessionDescription",getProfessionDescription())
			.append("HotDegree",getHotDegree())
			.append("SalaryRank",getSalaryRank())
			.append("CreateDate",getCreateDate())
			.append("Creator",getCreator())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("IdDelete",getIdDelete())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Profession == false) return false;
		if(this == obj) return true;
		Profession other = (Profession)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

