package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/21.
 */
public class SelUniversityPojo implements Serializable {

    private String universityId;

    private String universityName;

    private String photoUrl;

    private String property;

    private Object propertys;

    private String educationLevel;

    private String rank;

    private String openMajorNumber;

    private List eduLevelMajorNumber;

    private Map<String,Object> sexPercent;

    private String fiveSalary;

    private String educationLevelName;

    public Object getPropertys() {
        return propertys;
    }

    public void setPropertys(Object propertys) {
        this.propertys = propertys;
    }

    public String getEducationLevelName() {
        return educationLevelName;
    }

    public void setEducationLevelName(String educationLevelName) {
        this.educationLevelName = educationLevelName;
    }

    public List getEduLevelMajorNumber() {
        return eduLevelMajorNumber;
    }

    public void setEduLevelMajorNumber(List eduLevelMajorNumber) {
        this.eduLevelMajorNumber = eduLevelMajorNumber;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getOpenMajorNumber() {
        return openMajorNumber;
    }

    public void setOpenMajorNumber(String openMajorNumber) {
        this.openMajorNumber = openMajorNumber;
    }

    public Map<String, Object> getSexPercent() {
        return sexPercent;
    }

    public void setSexPercent(Map<String, Object> sexPercent) {
        this.sexPercent = sexPercent;
    }

    public String getFiveSalary() {
        return fiveSalary;
    }

    public void setFiveSalary(String fiveSalary) {
        this.fiveSalary = fiveSalary;
    }
}
