package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/23.
 */
public class VideoCoursePojo implements Serializable{
    private Long courseId;//ID
    private Long classifyId;//分类ID
    private String classifyName;//分类名称
    private String teacherName;//教师名字
    private String subjectName;//所属科目
    private String title;//题目
    private String content;//内容
    private String frontCover;//封面1
    private String subcontent;//简介
    private Integer hit;//点击量
    private String years;//年份
    private String courseSort;//课程排名
    private String frontcover1;//封面二

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public String getSubcontent() {
        return subcontent;
    }

    public void setSubcontent(String subcontent) {
        this.subcontent = subcontent;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCourseSort() {
        return courseSort;
    }

    public void setCourseSort(String courseSort) {
        this.courseSort = courseSort;
    }

    public String getFrontcover1() {
        return frontcover1;
    }

    public void setFrontcover1(String frontcover1) {
        this.frontcover1 = frontcover1;
    }
}
