package cn.thinkjoy.gk.pojo;

/**
 * Created by zuohao on 15/11/11.
 */
public class OpenMajoredPojo {
    private String majoredName;
    private String batch;
    private String subject;
    private String majoredRank;
    private String salaryRank;
    private String employmentRateRank;
    private int isEnrol;

    public int getIsEnrol() {
        return isEnrol;
    }

    public void setIsEnrol(int isEnrol) {
        this.isEnrol = isEnrol;
    }

    public String getMajoredName() {
        return majoredName;
    }

    public void setMajoredName(String majoredName) {
        this.majoredName = majoredName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMajoredRank() {
        return majoredRank;
    }

    public void setMajoredRank(String majoredRank) {
        this.majoredRank = majoredRank;
    }

    public String getSalaryRank() {
        return salaryRank;
    }

    public void setSalaryRank(String salaryRank) {
        this.salaryRank = salaryRank;
    }

    public String getEmploymentRateRank() {
        return employmentRateRank;
    }

    public void setEmploymentRateRank(String employmentRateRank) {
        this.employmentRateRank = employmentRateRank;
    }
}
