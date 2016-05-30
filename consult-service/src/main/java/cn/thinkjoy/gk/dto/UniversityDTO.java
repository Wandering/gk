package cn.thinkjoy.gk.dto;


import cn.thinkjoy.gk.domain.University;

/**
 * Created by admin on 2015/12/18.
 */
public class UniversityDTO extends University {
    /** 学校地址 */
    private String address;
    /** 联系电话 */
    private String contactPhone;
    /** 院校简介 */
    private String universityIntro;
    /** 报考指南 */
    private String entranceIntro;
    /** 特色专业 */
    private String featureMajor;
    /** 类型名称 */
    private String typeName;
    /** 层次名称 */
    private String levelName;

    /** 层次名称 */
    private String province;
    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getUniversityIntro() {
        return universityIntro;
    }

    public void setUniversityIntro(String universityIntro) {
        this.universityIntro = universityIntro;
    }

    public String getEntranceIntro() {
        return entranceIntro;
    }

    public void setEntranceIntro(String entranceIntro) {
        this.entranceIntro = entranceIntro;
    }

    public String getFeatureMajor() {
        return featureMajor;
    }

    public void setFeatureMajor(String featureMajor) {
        this.featureMajor = featureMajor;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
