package cn.thinkjoy.gk.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/25.
 */
public class OpenUniversity {
    private String universityType;
    private List<Map<String,Object>> universityLs;

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public List<Map<String, Object>> getUniversityLs() {
        return universityLs;
    }

    public void setUniversityLs(List<Map<String, Object>> universityLs) {
        this.universityLs = universityLs;
    }
}
