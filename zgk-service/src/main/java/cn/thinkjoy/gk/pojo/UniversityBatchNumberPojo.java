package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/19.
 */
public class UniversityBatchNumberPojo implements Serializable {

    private String batchName;

    private int universityNumber;

    private String percent;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getUniversityNumber() {
        return universityNumber;
    }

    public void setUniversityNumber(int universityNumber) {
        this.universityNumber = universityNumber;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
