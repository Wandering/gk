package cn.thinkjoy.gk.dto;

/**
 * Created by wpliu on 15/9/25.
 */
public class MajoredDto {

    private String name;
    private String educationLevel;
    private String subjectType;
    private String majoredType;
    private String degree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getMajoredType() {
        return majoredType;
    }

    public void setMajoredType(String majoredType) {
        this.majoredType = majoredType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
