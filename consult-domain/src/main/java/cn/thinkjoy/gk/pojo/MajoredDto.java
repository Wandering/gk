package cn.thinkjoy.gk.pojo;

/**
 * Created by wpliu on 15/9/25.
 */
public class MajoredDto {

    private String name;
    private String educationLevel;
    private String subjectType;
    private String majoredType;
    private String degree;
    private String similarMajor;
    private String mainCourse;
    private String workGuide;

    public String getWorkGuide() {
        return workGuide;
    }

    public void setWorkGuide(String workGuide) {
        this.workGuide = workGuide;
    }

    public String getSimilarMajor() {
        return similarMajor;
    }

    public void setSimilarMajor(String similarMajor) {
        this.similarMajor = similarMajor;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

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