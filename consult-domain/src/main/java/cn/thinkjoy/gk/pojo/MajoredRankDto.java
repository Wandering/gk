/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: timer
 * $Id:  MajoredRank.java 2015-11-10 15:57:32 $
 */



package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.gk.domain.MajoredRank;

public class MajoredRankDto extends MajoredRank{

    private String universityName;

    private String majorName;

    private Integer enrollNumber;

    private String type;

    private String feeStandard;

    private String subject;

    private String foreignLanguage;

    private Integer isOpen;

    @Override
    public String getUniversityName() {
        return universityName;
    }

    @Override
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String getMajorName() {
        return majorName;
    }

    @Override
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeeStandard() {
        return feeStandard;
    }

    public void setFeeStandard(String feeStandard) {
        this.feeStandard = feeStandard;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}

