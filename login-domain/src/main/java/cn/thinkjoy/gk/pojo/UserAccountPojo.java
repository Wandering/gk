package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by clei on 15/9/21.
 */
public class UserAccountPojo extends BaseDomain {
    private Integer status;
    private String name;
    private String icon;
    private String account;
    private String password;
    private Integer userType;
    private Integer areaId;
    private Integer vipStatus;
    private Integer isReported;
    private Integer isSurvey;

    public UserAccountPojo(){
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUserType(Integer value) {
        this.userType = value;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getIsReported() {
        return isReported;
    }

    public void setIsReported(Integer isReported) {
        this.isReported = isReported;
    }

    public Integer getIsSurvey() {
        return isSurvey;
    }

    public void setIsSurvey(Integer isSurvey) {
        this.isSurvey = isSurvey;
    }
}
