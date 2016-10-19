package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/19.
 */
public class MajorTop3Pojo implements Serializable {
    private String majorCode;
    private String schoolCode;
    private String selectSubject;

    public String getMajorCode() {
        return majorCode;
    }
    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
    public String getSchoolCode() {
        return schoolCode;
    }
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    public String getSelectSubject() {
        return selectSubject;
    }
    public void setSelectSubject(String selectSubject) {
        this.selectSubject = selectSubject;
    }
}
