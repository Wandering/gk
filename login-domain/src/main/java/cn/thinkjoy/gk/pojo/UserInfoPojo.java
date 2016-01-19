package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zuohao on 16/1/19.
 */
public class UserInfoPojo extends BaseDomain<Long> {
    private String account;
    private String icon;
    /** 是否填报 */
    private String isReported;
    /** 是否评测 */
    private String isSurvey;
    private String name;
    private String status;
    private String userType;
    private String vipStatus;
    private String subjectType;
    private String sex;
    private String schoolName;
    private String province;
    private String city;
    private String county;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsReported() {
        return isReported;
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported;
    }

    public String getIsSurvey() {
        return isSurvey;
    }

    public void setIsSurvey(String isSurvey) {
        this.isSurvey = isSurvey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
