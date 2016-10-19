package cn.thinkjoy.gk.domain;

/**
 * Created by yangyongping on 16/9/19.
 */
public class UniversityMajorInfo {
    private Integer  bacth;
    private String  bacthName;
    private String  majorName;
    private String  majorCode;

    public Integer getBacth() {
        return bacth;
    }

    public void setBacth(Integer bacth) {
        this.bacth = bacth;
    }

    public String getBacthName() {
        return bacthName;
    }

    public void setBacthName(String bacthName) {
        this.bacthName = bacthName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
}
