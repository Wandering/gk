package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/19.
 */
public class MajorPojo implements Serializable {

    private String universityId;

    private String universityName;

    private String majorCode;

    private String majorName;

    private String batch;

    private String batchName;

    private String planNumber;

    private String selectSubject;

    private String universityRank;

    //是否收藏
    private Integer isFavorite;
    //是收藏对应id
    private Integer cid;

    public String getUniversityRank() {
        return universityRank;
    }

    public void setUniversityRank(String universityRank) {
        this.universityRank = universityRank;
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

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getSelectSubject() {
        return selectSubject;
    }

    public void setSelectSubject(String selectSubject) {
        this.selectSubject = selectSubject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
