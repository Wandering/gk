package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zuohao on 16/9/21.
 */
public class EduLevelNumberPojo implements Serializable {

    private String eduLevel;

    private String eduLevelName;

    private String number;

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getEduLevelName() {
        return eduLevelName;
    }

    public void setEduLevelName(String eduLevelName) {
        this.eduLevelName = eduLevelName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
