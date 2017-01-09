package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zuohao on 16/9/22.
 */
public class SelMajorPojo implements Serializable {

    private String majorId;

    private String majorName;

    private String jobRank;

    private List sexPercent;

    private String fiveSalary;

    private String fmRatio;

    public String getFmRatio() {
        return fmRatio;
    }

    public void setFmRatio(String fmRatio) {
        this.fmRatio = fmRatio;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getJobRank() {
        return jobRank;
    }

    public void setJobRank(String jobRank) {
        this.jobRank = jobRank;
    }

    public List getSexPercent() {
        return sexPercent;
    }

    public void setSexPercent(List sexPercent) {
        this.sexPercent = sexPercent;
    }

    public String getFiveSalary() {
        return fiveSalary;
    }

    public void setFiveSalary(String fiveSalary) {
        this.fiveSalary = fiveSalary;
    }
}
