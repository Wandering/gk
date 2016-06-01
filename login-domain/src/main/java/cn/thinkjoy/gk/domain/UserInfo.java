/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gk
 * $Id:  UserInfo.java 2015-09-28 18:43:26 $
 */





package cn.thinkjoy.gk.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserInfo extends BaseDomain<Long>{
    /** 预留 */
    private String token;
    /** 用户名 */
    private String name;
    /** 头像 */
    private String icon;
    /** 省份Id */
    private String provinceId;
    /** 城市Id */
    private String cityId;
    /** 区域Id */
    private String countyId;
    /** 学校名称 */
    private String schoolName;
    /** 年级。1：高中一年级；2：高中二年级；3：高中三年级 */
    private int grade;
    /** 生日 */
    private Long birthdayDate;
    /** 性别，0女，1男 */
    private Integer sex;
    /** 科类，0文科，1理科 */
    private Integer subjectType;
    /** 邮箱 */
    private String mail;
    /** QQ号 */
    private String qq;

    /**成绩预测start**/
    /** 文理 */
    private String type;
    /** 院校 */
    private String universityName;
    /** 成绩 */
    private String achievement;
    /** 成绩 */
    private Integer typeId;
    /** 是否预测*/
    private boolean isForecaset;
    /**成绩预测end**/

    private Long accountId;
    private String qrCodeUrl;
    private Long sharerId;
    private String sharerType;
    private String agentLevel;
    private String vipType;

    public UserInfo(){
    }
    public void setToken(String value) {
        this.token = value;
    }

    public String getToken() {
        return this.token;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setIcon(String value) {
        this.icon = value;
    }

    public String getIcon() {
        return this.icon;
    }
    public void setProvinceId(String value) {
        this.provinceId = value;
    }

    public String getProvinceId() {
        return this.provinceId;
    }
    public void setCityId(String value) {
        this.cityId = value;
    }

    public String getCityId() {
        return this.cityId;
    }
    public void setCountyId(String value) {
        this.countyId = value;
    }

    public String getCountyId() {
        return this.countyId;
    }
    public void setSchoolName(String value) {
        this.schoolName = value;
    }

    public String getSchoolName() {
        return this.schoolName;
    }
    public void setBirthdayDate(Long value) {
        this.birthdayDate = value;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getBirthdayDate() {
        return this.birthdayDate;
    }
    public void setSex(Integer value) {
        this.sex = value;
    }

    public Integer getSex() {
        return this.sex;
    }
    public void setSubjectType(Integer value) {
        this.subjectType = value;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }
    public void setMail(String value) {
        this.mail = value;
    }

    public String getMail() {
        return this.mail;
    }
    public void setQq(String value) {
        this.qq = value;
    }

    public String getQq() {
        return this.qq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public boolean isForecaset() {
        return isForecaset;
    }

    public void setIsForecaset(boolean isForecaset) {
        this.isForecaset = isForecaset;
    }

    public void setForecaset(boolean forecaset) {
        isForecaset = forecaset;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Long getSharerId() {
        return sharerId;
    }

    public void setSharerId(Long sharerId) {
        this.sharerId = sharerId;
    }

    public String getSharerType() {
        return sharerType;
    }

    public void setSharerType(String sharerType) {
        this.sharerType = sharerType;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id",getId())
                .append("Token", getToken())
                .append("Name",getName())
                .append("Icon",getIcon())
                .append("ProvinceId",getProvinceId())
                .append("CityId",getCityId())
                .append("CountyId",getCountyId())
                .append("SchoolName",getSchoolName())
                .append("BirthdayDate",getBirthdayDate())
                .append("Sex",getSex())
                .append("SubjectType",getSubjectType())
                .append("Mail",getMail())
                .append("Qq",getQq())
                .append("Type",getType())
                .append("UniversityName",getUniversityName())
                .append("Achievement",getAchievement())
                .append("typeId",getTypeId())
                .append("isForecaset",isForecaset())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof UserInfo == false) return false;
        if(this == obj) return true;
        UserInfo other = (UserInfo)obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

