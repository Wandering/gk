package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/19.
 */
public class NumberPojo implements Serializable {

    private int schoolNumber;

    private int majorNumber;

    public int getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public int getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(int majorNumber) {
        this.majorNumber = majorNumber;
    }
}
