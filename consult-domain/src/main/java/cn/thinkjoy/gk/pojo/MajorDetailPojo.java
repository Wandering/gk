package cn.thinkjoy.gk.pojo;

/**
 * Created by clei on 15/10/9.
 */
public class MajorDetailPojo {


    private Long id;

    private String majorCode;

    private String name;

    private Integer planNum;

    private String batch;

    private String subject;

    private String schoolLength;

    private String feeStandard;

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }
}
