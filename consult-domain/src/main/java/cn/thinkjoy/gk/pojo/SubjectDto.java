package cn.thinkjoy.gk.pojo;

/**
 * Created by wpliu on 15/9/25.
 */
public class SubjectDto {
    private String code;
    /** 专业ID */
    private int majoredId;
    private String name;
    private Integer planNumber;
    private String schoolLength;
    private String foreginLanguage;
    private String feeStandard;
    private Integer universityId;
    private String universityName;

    public int getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(int majoredId) {
        this.majoredId = majoredId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getForeginLanguage() {
        return foreginLanguage;
    }

    public void setForeginLanguage(String foreginLanguage) {
        this.foreginLanguage = foreginLanguage;
    }

    public String getFeeStandard() {
        return feeStandard;
    }

    public void setFeeStandard(String feeStandard) {
        this.feeStandard = feeStandard;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
