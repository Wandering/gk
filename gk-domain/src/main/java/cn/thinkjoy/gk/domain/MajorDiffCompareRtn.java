package cn.thinkjoy.gk.domain;

import java.util.List;

/**
 * Created by yangyongping on 16/9/19.
 */
public class MajorDiffCompareRtn {
    private String  universityName;
    private Long  universityId;
    private String  areaId;
    private String  areaName;
    //院校类型(工,农..)
    private String  universityType;
    //专业信息
    private List<UniversityMajorInfo> universityMajorInfos1;

    //专业信息
    private List<UniversityMajorInfo> universityMajorInfos2;

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public List<UniversityMajorInfo> getUniversityMajorInfos1() {
        return universityMajorInfos1;
    }

    public void setUniversityMajorInfos1(List<UniversityMajorInfo> universityMajorInfos1) {
        this.universityMajorInfos1 = universityMajorInfos1;
    }

    public List<UniversityMajorInfo> getUniversityMajorInfos2() {
        return universityMajorInfos2;
    }

    public void setUniversityMajorInfos2(List<UniversityMajorInfo> universityMajorInfos2) {
        this.universityMajorInfos2 = universityMajorInfos2;
    }
}
