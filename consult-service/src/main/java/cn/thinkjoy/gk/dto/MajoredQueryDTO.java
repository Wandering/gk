package cn.thinkjoy.gk.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/1/27.
 */
public class MajoredQueryDTO implements Serializable {
    private String disciplineCategoriesName;
    private String majorCategoryName;
    private String majoredName;
    private long majoredId;
    public String getDisciplineCategoriesName() {
        return disciplineCategoriesName;
    }

    public void setDisciplineCategoriesName(String disciplineCategoriesName) {
        this.disciplineCategoriesName = disciplineCategoriesName;
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

    public long getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(long majoredId) {
        this.majoredId = majoredId;
    }
}
