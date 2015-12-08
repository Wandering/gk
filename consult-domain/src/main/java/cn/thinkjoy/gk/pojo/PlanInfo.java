package cn.thinkjoy.gk.pojo;

/**
 * Created by wpliu on 15/9/25.
 */
public class PlanInfo {
    private long majoredId;
    private String majoredName;
    private String batch;
    private String majoredType;
    private String subject;
    private Integer planNumber;
    private String schoolLength;
    private String feeStandard;

    public String getMajoredType() {
        return majoredType;
    }

    public void setMajoredType(String majoredType) {
        this.majoredType = majoredType;
    }

    public long getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(long majoredId) {
        this.majoredId = majoredId;
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

    public Integer getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    public String getSchoolLength() {
        return schoolLength;
    }

    public void setSchoolLength(String schoolLength) {
        this.schoolLength = schoolLength;
    }

    public String getFeeStandard() {
        return feeStandard;
    }

    public void setFeeStandard(String feeStandard) {
        this.feeStandard = feeStandard;
    }
}
