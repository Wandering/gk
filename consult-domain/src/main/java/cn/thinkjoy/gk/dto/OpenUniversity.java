package cn.thinkjoy.gk.dto;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class OpenUniversity {
    private String universityType;
    private List<UniversityDto> universityLs;

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public List<UniversityDto> getUniversityLs() {
        return universityLs;
    }

    public void setUniversityLs(List<UniversityDto> universityLs) {
        this.universityLs = universityLs;
    }
}
