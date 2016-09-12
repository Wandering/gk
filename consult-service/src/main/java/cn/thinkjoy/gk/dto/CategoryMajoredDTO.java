package cn.thinkjoy.gk.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/1/26.
 */
public class CategoryMajoredDTO implements Serializable {
    private long majoredId;
    private long disciplineCategoriesId;//大门类
    private String disciplineCategoriesName;//大门类
    private long majorCategoryId;//专业门类
    private String majorCategoryName;//专业门类
    private String majoredName;

    public long getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(long majoredId) {
        this.majoredId = majoredId;
    }

    public long getDisciplineCategoriesId() {
        return disciplineCategoriesId;
    }

    public void setDisciplineCategoriesId(long disciplineCategoriesId) {
        this.disciplineCategoriesId = disciplineCategoriesId;
    }

    public String getDisciplineCategoriesName() {
        return disciplineCategoriesName;
    }

    public void setDisciplineCategoriesName(String disciplineCategoriesName) {
        this.disciplineCategoriesName = disciplineCategoriesName;
    }

    public long getMajorCategoryId() {
        return majorCategoryId;
    }

    public void setMajorCategoryId(long majorCategoryId) {
        this.majorCategoryId = majorCategoryId;
    }

    public String getMajorCategoryName() {
        return majorCategoryName;
    }

    public void setMajorCategoryName(String majorCategoryName) {
        this.majorCategoryName = majorCategoryName;
    }

    public String getMajoredName() {
        return majoredName;
    }

    public void setMajoredName(String majoredName) {
        this.majoredName = majoredName;
    }
}
