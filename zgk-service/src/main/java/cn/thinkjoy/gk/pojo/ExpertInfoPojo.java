package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/10/20.
 */
public class ExpertInfoPojo  implements Serializable {

    private String expertId;
    private String expertName;
    private String photoUrl;
    private String qualification;
    private String bestExpert;
    private String speciality;
    private String casesNumber;
    private String info;
    private String service;
    private String rate;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getBestExpert() {
        return bestExpert;
    }

    public void setBestExpert(String bestExpert) {
        this.bestExpert = bestExpert;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCasesNumber() {
        return casesNumber;
    }

    public void setCasesNumber(String casesNumber) {
        this.casesNumber = casesNumber;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
